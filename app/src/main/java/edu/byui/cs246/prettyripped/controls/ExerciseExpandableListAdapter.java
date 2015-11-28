package edu.byui.cs246.prettyripped.controls;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.ExerciseSet;

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
    private List<Exercise> exercises = new ArrayList<>();

    /**
     * Creates a new instance of ExerciseExpandableListAdapter
     *
     * @param context Context
     * @param exercises list of exercises to list
     */
    public ExerciseExpandableListAdapter(Context context, List<Exercise> exercises) {
        Log.d(TAG, "ExerciseExpandableListAdapter(context, exercises)");
        this.context = context;
        this.exercises = exercises;
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
        return true;
    }

    /**
     * Gets a view for a groupDescription element (Exercise)
     *
     * @param groupPosition groupDescription position within the exercises list
     * @param isExpanded
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the groupDescription element
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Exercise exercise = exercises.get(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exercise_view, null);
        }

        CheckedTextView label = (CheckedTextView) convertView.findViewById(R.id.labelExerciseName);
        label.setText("(" + exercise.getId() + ")" + exercise.getName());

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

        // Get the child for the specified groupDescription/child position
        ExerciseSet exerciseSet = (ExerciseSet) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.set_view, null);
        }

        // Get controls
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        TextView labelSetNumber = (TextView) convertView.findViewById(R.id.labelSetNumber);
        EditText editWeight = (EditText) convertView.findViewById(R.id.editWeight);
        EditText editReps = (EditText) convertView.findViewById(R.id.editReps);

        // ExerciseSet controls to match underlying ExerciseSet
        checkBox.setChecked(exerciseSet.getCompleted());
        labelSetNumber.setText( context.getString(R.string.label_set_prefix) + " " + Integer.toString(childPosition + 1) );
        editWeight.setText( Float.toString(exerciseSet.getWeight()) );
        editReps.setText( Float.toString( exerciseSet.getReps() ) );

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
        return false;
    }
}