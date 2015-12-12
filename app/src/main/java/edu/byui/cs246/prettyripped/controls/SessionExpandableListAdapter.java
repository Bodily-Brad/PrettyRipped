package edu.byui.cs246.prettyripped.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cs246.prettyripped.views.RippedCheckBox;
import edu.byui.cs246.prettyripped.models.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.views.RippedEditText;
import edu.byui.cs246.prettyripped.views.RippedImageView;
import edu.byui.cs246.prettyripped.views.RippedTextView;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.ExerciseSet;
import edu.byui.cs246.prettyripped.models.Session;
import edu.byui.cs246.prettyripped.activities.SessionActivity;

/**
 * Represents an adapter between a List of exercises and an expandable list
 *
 * @author Brad Bodily
 * @since 2015-11-13
 */
public class SessionExpandableListAdapter extends BaseExpandableListAdapter {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionListAdapter";

    // LOCAL VARIABLES
    private Context context;
    private Session session;
    private PrettyRippedData data;
    private SessionActivity sessionActivity;

    private List<Exercise> exercises = new ArrayList<>();

    /**
     * Creates a new instance of SessionExpandableListAdapter
     *
     * @param context Context
     * @param session the Session that drives this list
     */
    public SessionExpandableListAdapter(Context context, Session session) {

        // Get handle to data handler
        data = PrettyRippedData.getInstance();

        this.context = context;
        this.sessionActivity = (SessionActivity) context;
        this.session = session;
        this.exercises = session.getExercises();
    }

    /**
     * Gets the number of groups (i.e. the number of exercises within our exercise list)
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return exercises.size();
    }

    /**
     * Gets the number of children within a particular groupDescription
     *
     * @param groupPosition the position of the groupDescription in question
     * @return the number of children within the specified groupDescription
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        // We add one, because the last spot is filled by the "new exercise set" view
        return exercises.get(groupPosition).getExerciseSets().size()+1;
    }

    /**
     * Gets the Exercise at the specified position
     *
     * @param groupPosition position of the Exercise within the exercises list
     * @return the Exercise at the specified location within the exercises list
     */
    @Override
    public Object getGroup(int groupPosition) {
        return exercises.get(groupPosition);
    }

    /**
     * Gets a ExerciseSet from the child list
     *
     * @param groupPosition position of the groupDescription within the groupDescription list
     * @param childPosition position of the child within the child list
     * @return the ExerciseSet at the specified position within the specified Exercise
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ExerciseSet> exerciseSets = exercises.get(groupPosition).getExerciseSets();
        return exerciseSets.get(childPosition);
    }

    /**
     * Returns the id for a particular groupPosition
     *
     * @param groupPosition groupPosition in question
     * @return the id of the groupPosition
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns the id for a particular childPosition
     *
     * @param groupPosition groupPosition of the childPosition in question
     * @param childPosition childPosition in question
     * @return the id of the childPosition
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Gets a flag indicator of whether the IDs are stable or not
     *
     * @return true if the IDs are stable; otherwise, false.
     */
    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Gets a view for a groupDescription element (Exercise)
     *
     * @param groupPosition groupDescription position within the exercises list
     * @param isExpanded flag describing whether the group is expanded or not
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the groupDescription element
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        // Get the associated Exercise
        Exercise exercise = exercises.get(groupPosition);

        // check for null view, if so, inflate a new one
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.session_list_group, null);
        }

        // Get controls from view

        // Delete Exercise button
        RippedImageView delButton = (RippedImageView) convertView.findViewById(R.id.buttonDeleteExercise);
        delButton.rippedObject = exercise;

        // Exercise name
        RippedTextView label = (RippedTextView) convertView.findViewById(R.id.labelExerciseName);
        label.rippedObject = exercise;

        // Delete Exercise Button
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();

                // Get a handle to me that we can cast to a RippedView
                RippedImageView me = (RippedImageView) v;

                // Get Exercise from ourselves (as a ripped view)
                final Exercise ex = (Exercise) me.rippedObject;

                // If the exercise has any sets, use a dialog
                if (ex.getExerciseSets().size() > 0) {
                    // Show a dialog asking the user to confirm deletion of the exercise
                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.dialog_title_delete_exercise)
                            .setMessage(R.string.prompt_delete_exercise)
                            .setPositiveButton(R.string.button_confirm_delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Delete the Exercise and refresh the ExerciseList
                                    data.deleteExercise(ex);
                                    sessionActivity.refreshExerciseList();
                                }
                            })
                            .setNegativeButton(R.string.button_cancel_delete, null)
                            .show();
                } else {
                    // Otherwise, just delete it
                    data.deleteExercise(ex);
                    sessionActivity.refreshExerciseList();
                }

            }
        });

        // Set the group label to the exercise name
        label.setText(exercise.getName());

        return convertView;
    }

    /**
     * Gets a view for a child element (ExerciseSet)
     *
     * @param groupPosition groupDescription position within the exercises list
     * @param childPosition child position within the specific exercise
     * @param isLastChild boolean indicator as to whether this is the last child in the list
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the child element
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // If this is the last child (the one 'past the end') we'll set the "new set" view;
        // otherwise, create a view and tie it to the 'real' ExerciseSet
        if (childPosition < getChildrenCount(groupPosition)-1) {
            return getExerciseSetView(groupPosition, childPosition, isLastChild, convertView, parent);
        } else {
            return getNewExerciseSetView(groupPosition, childPosition, isLastChild, convertView, parent);
        }
    }

    /**
     * Returns a flag indicating whether or not a specified child is selectable
     *
     * @param groupPosition groupDescription position within the exercises list
     * @param childPosition child position within the specific exercise
     * @return true if the child is selectable; otherwise, false.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    /**
     * Creates a new child view, attached to an established ExerciseSet
     *
     * @param groupPosition group position
     * @param childPosition child position
     * @param isLastChild flag indicating whether this is the last child in the list
     * @param convertView view to use (or re-use)
     * @param parent parent ViewGroup
     * @return a view representing an ExercistSet
     */
    private View getExerciseSetView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // Get the child for the specified groupDescription/child position
        ExerciseSet exerciseSet = (ExerciseSet) getChild(groupPosition, childPosition);

        // If the view is null (i.e., it's not being reused), inflate a new one
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.session_list_child, null);
        }

        // Get controls
        // New set button
        RippedImageView addSetButton = (RippedImageView) convertView.findViewById(R.id.buttonAddExerciseSet);
        addSetButton.setVisibility(View.GONE);

        // Set # label
        TextView labelSetNumber = (TextView) convertView.findViewById(R.id.labelSetNumber);
        labelSetNumber.setVisibility(View.VISIBLE);

        // Check Box
        RippedCheckBox checkBox = (RippedCheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        checkBox.setVisibility(View.VISIBLE);           // Re-use may have hidden this, so show it
        checkBox.rippedID = exerciseSet.getId();
        checkBox.rippedObject = exerciseSet;

        // Reps edit
        RippedEditText editReps = (RippedEditText) convertView.findViewById(R.id.editReps);
        editReps.setVisibility(View.VISIBLE);
        editReps.rippedID = exerciseSet.getId();
        editReps.rippedObject = exerciseSet;

        // Reps Label
        TextView repsLabel = (TextView) convertView.findViewById(R.id.labelReps);
        repsLabel.setVisibility(View.VISIBLE);

        // Weight edit
        RippedEditText editWeight = (RippedEditText) convertView.findViewById(R.id.editWeight);
        editWeight.setVisibility(View.VISIBLE);
        editWeight.rippedID = exerciseSet.getId();
        editWeight.rippedObject = exerciseSet;

        // Weight Label
        TextView weightLabel = (TextView) convertView.findViewById(R.id.labelWeight);
        weightLabel.setVisibility(View.VISIBLE);

        // Delete Icon
        RippedImageView delButton = (RippedImageView) convertView.findViewById(R.id.buttonDeleteExerciseSet);
        delButton.setVisibility(View.VISIBLE);          // Re-use may have hidden this, so show it
        delButton.rippedID = exerciseSet.getId();
        delButton.rippedObject = exerciseSet;

        // Populate our controls with data from the ExerciseSet
        checkBox.setChecked(exerciseSet.getCompleted());
        labelSetNumber.setText(context.getString(R.string.label_set_prefix) + " " + Integer.toString(childPosition + 1));
        editWeight.setText(String.format("%.0f", exerciseSet.getWeight()));
        editReps.setText(Integer.toString(exerciseSet.getReps()));

        // Set up listeners

        // Delete ExerciseSet button
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                // Get a handle to me that we can cast to a RippedView
                RippedImageView me = (RippedImageView) v;

                // Get ExerciseSet from ourselves (as a ripped view)
                final ExerciseSet es = (ExerciseSet) me.rippedObject;

                // Check to see if our set is 'empty'

                if ( (es.getReps() > 0) || (es.getWeight()>0.0f) || (es.getCompleted())) {
                    // If so, we'll use a confirmation dialog
                    new AlertDialog.Builder(context)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.dialog_title_delete_exerciseset)
                            .setMessage(R.string.prompt_delete_exerciseset)
                            .setPositiveButton(R.string.button_confirm_delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    data.deleteExerciseSet(es);
                                    sessionActivity.refreshExerciseList();
                                }
                            })
                            .setNegativeButton(R.string.button_cancel_delete, null)
                            .show();

                } else {
                    // Otherwise, we'll just delete it
                    data.deleteExerciseSet(es);
                    sessionActivity.refreshExerciseList();
                }
            }
        });

        // Check box
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Get a handle to me that we can cast to a RippedView
                RippedCheckBox me = (RippedCheckBox) buttonView;

                // Get ExerciseSet from ourselves (as a ripped view)
                ExerciseSet es = (ExerciseSet) me.rippedObject;

                if (isChecked != es.completed) {
                    es.setCompleted(isChecked);
                    data.updateExerciseSet(es);
                    sessionActivity.refreshExerciseList();
                }

            }
        });

        // Reps
        editReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Get a handle to me that we can cast to a RippedView
                RippedEditText me = (RippedEditText) v;

                // Get ExerciseSet from ourselves (as a ripped view)
                ExerciseSet es = (ExerciseSet) me.rippedObject;

                // Get data handler
                PrettyRippedData data = PrettyRippedData.getInstance();

                if (es != null) {
                    String repText = me.getText().toString();
                    int newRepValue = 0;
                    if (!repText.equals("")) {
                        newRepValue = Integer.parseInt(repText);
                    }
                    if (newRepValue != es.getReps()) {
                        es.reps = newRepValue;
                        data.updateExerciseSet(es);
                    }

                }
            }
        });

        // Weight
        editWeight.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // Get a handle to me that we can cast to a RippedView
                RippedEditText me = (RippedEditText) v;

                // Get ExerciseSet from ourselves (as a ripped view)
                ExerciseSet es = (ExerciseSet) me.rippedObject;

                // Get data handler
                PrettyRippedData data = PrettyRippedData.getInstance();

                if (es != null) {
                    String weightText = me.getText().toString();
                    float newWeightValue = 0.0f;
                    if (!weightText.equals("")) {
                        newWeightValue = Float.parseFloat(weightText);
                    }
                    if (newWeightValue != es.getWeight()) {
                        es.weight = newWeightValue;
                        data.updateExerciseSet(es);
                    }
                }
            }
        });


        return convertView;
    }

    /**
     * Creates a new child view, attached to nothing, and shows/hides the appropriate elements for
     * the 'new' view
     *
     * @param groupPosition group position
     * @param childPosition child position
     * @param isLastChild flag indicating whether this is the last child in the list
     * @param convertView view to use (or re-use)
     * @param parent parent ViewGroup
     * @return a view representing an ExercistSet
     */
    private View getNewExerciseSetView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Get a hook to our exercise
        Exercise exercise = exercises.get(groupPosition);

        // If the view is null (i.e., it's not being reused), inflate a new one
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.session_list_child, null);
        }

        // Get Controls

        // New set button
        RippedImageView addSetButton = (RippedImageView) convertView.findViewById(R.id.buttonAddExerciseSet);
        addSetButton.setVisibility(View.VISIBLE);
        addSetButton.rippedObject = exercise;

        // Check Box
        RippedCheckBox checkBox = (RippedCheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        // Hide the check box for 'new' ExerciseSets
        checkBox.setVisibility(View.INVISIBLE);

        // Set # label
        TextView labelSetNumber = (TextView) convertView.findViewById(R.id.labelSetNumber);
        labelSetNumber.setText("");
        labelSetNumber.setVisibility(View.INVISIBLE);

        // Reps edit
        RippedEditText editReps = (RippedEditText) convertView.findViewById(R.id.editReps);
        editReps.setText("");
        editReps.setVisibility(View.INVISIBLE);

        // Reps Label
        TextView repsLabel = (TextView) convertView.findViewById(R.id.labelReps);
        repsLabel.setVisibility(View.INVISIBLE);

        // Weight edit
        RippedEditText editWeight = (RippedEditText) convertView.findViewById(R.id.editWeight);
        editWeight.setText("");
        editWeight.setVisibility(View.INVISIBLE);

        // Weight Label
        TextView weightLabel = (TextView) convertView.findViewById(R.id.labelWeight);
        weightLabel.setVisibility(View.INVISIBLE);

        // Delete set button
        RippedImageView delButton = (RippedImageView) convertView.findViewById(R.id.buttonDeleteExerciseSet);
        // Hide the delete icon for 'new' ExerciseSets
        delButton.setVisibility(View.INVISIBLE);

        // Add set listener
        addSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get a handle to me that we can cast to a RippedView
                RippedImageView me = (RippedImageView) v;

                // Get Exercise from ourselves (as a ripped view)
                Exercise ex = (Exercise) me.rippedObject;

                // Create a new ExerciseSet and refresh the ExerciseList
                data.createExerciseSet(ex);
                sessionActivity.refreshExerciseList();
            }
        });

        return convertView;
    }
}