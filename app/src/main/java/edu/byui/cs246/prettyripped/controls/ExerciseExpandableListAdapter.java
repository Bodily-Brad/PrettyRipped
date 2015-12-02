package edu.byui.cs246.prettyripped.controls;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cs246.prettyripped.RippedCheckBox;
import edu.byui.cs246.prettyripped.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.RippedEditText;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.ExerciseSet;
import edu.byui.cs246.prettyripped.models.Session;

/**
 * Represents an adapter between a List of exercises and an expandable list
 *
 * @author Brad Bodily
 * @since 2015-11-13
 */
public class ExerciseExpandableListAdapter extends BaseExpandableListAdapter {
    // CONSTANTS & SETTINGS
    private final static String TAG = "ExerciseListAdapter";

    // LOCAL VARIABLES
    private Context context;
    private Session session;
    private long sessionID;
    private PrettyRippedData data;

    private List<Exercise> exercises = new ArrayList<>();

    /**
     * Creates a new instance of ExerciseExpandableListAdapter
     *
     * @param context Context
     * @param session the Session that drives this list
     */
    public ExerciseExpandableListAdapter(Context context, Session session) {
        Log.d(TAG, "ExerciseExpandableListAdapter(context, exercises)");

        data = PrettyRippedData.getInstance();

        this.context = context;
        this.session = session;
        this.sessionID = session.getId();

        this.exercises = session.getExercises();
    }

    /**
     * Gets the number of groups (i.e. the number of exercises within our exercise list)
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        Session ses = data.getSessionById(sessionID);
        exercises = ses.getExercises();

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
        return true;
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
            convertView = inflater.inflate(R.layout.exercise_view, null);
        }

        CheckedTextView label = (CheckedTextView) convertView.findViewById(R.id.labelExerciseName);
        label.setText(exercise.getName() + " (" + exercise.getId() + ")");

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
        Log.d(TAG, "getChildView(int, int, boolean, View, ViewGroup)");

        // Get the child for the specified groupDescription/child position
        ExerciseSet exerciseSet = (ExerciseSet) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.set_view, null);
        }

        // Get controls
        RippedCheckBox checkBox = (RippedCheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        TextView labelSetNumber = (TextView) convertView.findViewById(R.id.labelSetNumber);
        RippedEditText editWeight = (RippedEditText) convertView.findViewById(R.id.editWeight);
        final RippedEditText editReps = (RippedEditText) convertView.findViewById(R.id.editReps);

        checkBox.rippedID = exerciseSet.getId();
        editReps.rippedID = exerciseSet.getId();
        editWeight.rippedID = exerciseSet.getId();

        // ExerciseSet controls to match underlying ExerciseSet
        checkBox.setChecked(exerciseSet.getCompleted());
        labelSetNumber.setText( context.getString(R.string.label_set_prefix) + " " + Integer.toString(childPosition + 1) );
        editWeight.setText(String.format("%.0f", exerciseSet.getWeight()));
        editReps.setText(Integer.toString(exerciseSet.getReps()));

        // Set up listeners
        // Check box
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                long esID = ((RippedCheckBox) buttonView).rippedID;
                Log.d(TAG, "CHECK CHANGED for " + esID);

                // Get data handler
                PrettyRippedData data = PrettyRippedData.getInstance();
                ExerciseSet es = data.getSetById(esID);

                es.completed = isChecked;
                data.updateExerciseSet(es);
                //es.save();
            }
        });

        // Reps
        editReps.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                RippedEditText me = (RippedEditText) v;

                // Get data handler
                PrettyRippedData data = PrettyRippedData.getInstance();
                long esID = me.rippedID;

                ExerciseSet es = data.getSetById(esID);
                if (es != null) {
                    String repText = me.getText().toString();
                    int newRepValue = 0;
                    if (!repText.equals("")) {
                        newRepValue = Integer.parseInt(repText);
                    }
                    if (newRepValue != es.getReps()) {
                        es.reps = newRepValue;
                        data.updateExerciseSet(es);
                        //es.save();
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
                RippedEditText me = (RippedEditText) v;

                // Get data handler
                PrettyRippedData data = PrettyRippedData.getInstance();
                long esID = me.rippedID;

                ExerciseSet es = data.getSetById(esID);
                if (es != null) {
                    String weightText = me.getText().toString();
                    float newWeightValue = 0.0f;
                    if (!weightText.equals("")) {
                        newWeightValue = Float.parseFloat(weightText);
                    }
                    if (newWeightValue != es.getWeight()) {
                        es.weight = newWeightValue;
                        data.updateExerciseSet(es);
                        //es.save();
                    }
                } else {
                    Log.e(TAG, "ExerciseSet from store doesn't match ID");
                }
            }
        });


        return convertView;
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
}