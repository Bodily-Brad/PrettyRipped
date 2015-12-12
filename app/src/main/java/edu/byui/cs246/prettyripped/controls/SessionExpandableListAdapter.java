package edu.byui.cs246.prettyripped.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
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
    private final static String TAG = "ExerciseListAdapter";

    // LOCAL VARIABLES
    private Context context;
    private Session session;
    private long sessionID;
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
        Log.d(TAG, "SessionExpandableListAdapter(context, exercises)");

        data = PrettyRippedData.getInstance();

        this.context = context;
        this.sessionActivity = (SessionActivity) context;
        this.session = session;
        //this.sessionID = session.getId();

        this.exercises = session.getExercises();
    }

    /**
     * Gets the number of groups (i.e. the number of exercises within our exercise list)
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {

        // Let us know if our groupDescription is empty for some reason
        if (exercises.size() < 1) {
            Log.e(TAG, "getGroupCount() : exercises.size() < 1");
        }
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
        Log.d(TAG, "getChildrenCount(int)");
        return exercises.get(groupPosition).getExerciseSets().size();
    }

    /**
     * Gets the Exercise at the specified position
     *
     * @param groupPosition position of the Exercise within the exercises list
     * @return the Exercise at the specified location within the exercises list
     */
    @Override
    public Object getGroup(int groupPosition) {
        Log.d(TAG, "getGroup(int)");
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
        Log.d(TAG, "getChild(int, int)");

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
        Log.d(TAG, "getGroupId(int)");
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
        Log.d(TAG, "getChildId(int, int)");
        return childPosition;
    }

    /**
     * Gets a flag indicator of whether the IDs are stable or not
     *
     * @return true if the IDs are stable; otherwise, false.
     */
    @Override
    public boolean hasStableIds() {
        Log.d(TAG, "hasStableIds()");
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
        Log.d(TAG, "getGroupView(int, boolean, View, ViewGroup)");

        Exercise exercise = exercises.get(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.session_list_group, null);
        }

        // Get controls from view
        // Add ExerciseSet button
        RippedImageView addButton = (RippedImageView) convertView.findViewById(R.id.buttonAddSet);
        addButton.rippedObject = exercise;

        // Delete Exercise button
        RippedImageView delButton = (RippedImageView) convertView.findViewById(R.id.buttonDeleteExercise);
        delButton.rippedObject = exercise;

        // Exercise name
        RippedTextView label = (RippedTextView) convertView.findViewById(R.id.labelExerciseName);
        label.rippedObject = exercise;

        // Add Set button
        addButton.setOnClickListener(new View.OnClickListener() {
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

        // Delete Exercise Button
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();

                // Get a handle to me that we can cast to a RippedView
                RippedImageView me = (RippedImageView) v;

                // Get Exercise from ourselves (as a ripped view)
                final Exercise ex = (Exercise) me.rippedObject;

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
        return getExerciseSetView(groupPosition, childPosition, isLastChild, convertView, parent);
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
        Log.d(TAG, "isChildSelectable(int, int)");
        return false;
    }

    private View getExerciseSetView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Log.d(TAG, "getExerciseSetView(int, int, boolean, View, ViewGroup)");

        // Get the child for the specified groupDescription/child position
        ExerciseSet exerciseSet = (ExerciseSet) getChild(groupPosition, childPosition);

        // If the view is null (i.e., it's not being reused), inflate a new one
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.session_list_child, null);
        }

        // Get controls
        // Set # label
        TextView labelSetNumber = (TextView) convertView.findViewById(R.id.labelSetNumber);

        // Check Box
        RippedCheckBox checkBox = (RippedCheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        checkBox.rippedID = exerciseSet.getId();
        checkBox.rippedObject = exerciseSet;

        // Weight edit
        RippedEditText editWeight = (RippedEditText) convertView.findViewById(R.id.editWeight);
        editWeight.rippedID = exerciseSet.getId();
        editWeight.rippedObject = exerciseSet;

        // Reps edit
        RippedEditText editReps = (RippedEditText) convertView.findViewById(R.id.editReps);
        editReps.rippedID = exerciseSet.getId();
        editReps.rippedObject = exerciseSet;

        // Delete Icon
        RippedImageView delButton = (RippedImageView) convertView.findViewById(R.id.buttonDeleteExerciseSet);
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

                } else {
                    Log.e(TAG, "ExerciseSet from store doesn't match ID");
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
                } else {
                    Log.e(TAG, "ExerciseSet from store doesn't match ID");
                }
            }
        });


        return convertView;
    }
}