package edu.byui.cs246.prettyripped.controls;

import android.content.Context;
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
import edu.byui.cs246.prettyripped.models.IExercise;
import edu.byui.cs246.prettyripped.models.ISet;

/**
 * Created by bradb on 11/13/2015.
 */
public class ExerciseExpandableListAdapter extends BaseExpandableListAdapter {

    // LOCAL VARIABLES
    private Context context;
    private List<IExercise> exercises = new ArrayList<>();

    /**
     * Creates a new instance of ExerciseExpandableListAdapter
     * @param context Context
     * @param exercises list of exercises to list
     */
    public ExerciseExpandableListAdapter(Context context, List<IExercise> exercises) {
        this.context = context;
        this.exercises = exercises;
    }

    /**
     * Gets the number of groups (i.e. the number of exercises within our exercise list)
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return exercises.size();
    }

    /**
     * Gets the number of children within a particular group
     * @param groupPosition the position of the group in question
     * @return the number of children within the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return exercises.get(groupPosition).getSets().size();
    }

    /**
     * Gets the Exercise at the specified position
     * @param groupPosition position of the Exercise within the exercises list
     * @return the Exercise at the specified location within the exercises list
     */
    @Override
    public Object getGroup(int groupPosition) {
        return exercises.get(groupPosition);
    }

    /**
     * Gets a Set from the child list
     * @param groupPosition position of the Exercise within the exercise list to get the set
     *                      from
     * @param childPosition position of the Set within the Exercise's set list to get the set
     *                      from
     * @return the Set at the specified position within the specified Exercise
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ISet> sets = exercises.get(groupPosition).getSets();
        return sets.get(childPosition);
    }

    /**
     * Returns the id for a particular groupPosition
     * @param groupPosition groupPosition in question
     * @return the id of the groupPosition
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns the id for a particular childPosition
     * @param groupPosition groupPosition of the childPosition in question
     * @param childPosition childPosition in question
     * @return the id of the childPosition
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Gets a view for a group element (Exercise)
     * @param groupPosition group position within the exercises list
     * @param isExpanded
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the group element
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        IExercise exercise = exercises.get(groupPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.exercise_view, null);
        }

        CheckedTextView label = (CheckedTextView) convertView.findViewById(R.id.labelExerciseName);
        label.setText(exercise.getName());

        return convertView;
    }

    /**
     * Gets a view for a child element (Set)
     * @param groupPosition group position within the exercises list
     * @param childPosition child position within the specific exercise
     * @param isLastChild boolean indicator as to whether this is the last child in the list
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the child element
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        // Get the child for the specified group/child position
        ISet set = (ISet) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.set_view, null);
        }

        // Get controls
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkBoxCompleted);
        TextView labelSetNumber = (TextView) convertView.findViewById(R.id.labelSetNumber);
        EditText editWeight = (EditText) convertView.findViewById(R.id.editWeight);
        EditText editReps = (EditText) convertView.findViewById(R.id.editReps);

        // Set controls to match underlying Set
        checkBox.setChecked(set.getCompleted());
        labelSetNumber.setText( context.getString(R.string.label_set_prefix) + " " + Integer.toString(childPosition + 1) );
        editWeight.setText( Float.toString(set.getWeight()) );
        editReps.setText( Float.toString( set.getReps() ) );

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}