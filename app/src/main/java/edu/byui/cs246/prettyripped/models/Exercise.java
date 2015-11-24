package edu.byui.cs246.prettyripped.models;

import android.util.Log;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an individual exercise activity, including a name, group, and a collection of Sets
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class Exercise extends SugarRecord<Exercise> implements IExercise{
    // CONSTANTS & SETTINGS
    private final static String TAG = "Exercise";

    // LOCAL VARIABLES
    /**
     * The name of the Exercise
     */
    public String name;

    /**
     * A string "group" descriptor that categorizes this Exercise
     */
    public String group;

    /**
     * A collection of Sets that comprise this Exercise's sets
     */
    public List<ISet> sets = new ArrayList<>();

    // CONSTRUCTORS

    /**
     * Creates a new instance of Exercise with the default parameters
     */
    public  Exercise() {
        this("Exercise", "No Group", new ArrayList<ISet>());
        Log.i(TAG, "exiting Exercise()");
    }

    /**
     * Creates a new instance of Exercise with the specified parameters
     * @param name the name of this Exercise
     * @param group a group descriptor for this Exercise
     * @param sets a collection of Sets for this Exercise
     */
    public Exercise(String name, String group, List<ISet> sets) {
        Log.i(TAG, "Exercise(name, group, sets)");
        this.name = name;
        this.group = group;
        this.sets = sets;
        Log.i(TAG, "exiting Exercise(name, group, sets)");
    }

    /**
     * Gets the group of this Exercise
     * @return the group of this Exercise
     */
    @Override
    public String getGroup() {
        return group;
    }

    /**
     * Sets the group of this Exercise
     * @param group the group to assign to this exercise
     */
    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Gets the name of this Exercise
     * @return the name of this Exercise
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Exercise
     * @param name the name to assign to this exercise
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Sets of this Exercise
     * @return a List containing the Sets of this Exercise
     */
    @Override
    public List<ISet> getSets() {
        return sets;
    }

    /**
     * Adds a Set to this Exercise
     * @param set Set to add to this Exercise
     */
    @Override
    public void addSet(ISet set) {
        this.sets.add(set);
    }

    /**
     * Removes a Set from this Exercise
     * @param set Set to remove from this Exercise
     */
    @Override
    public void removeSet(ISet set) {
        this.sets.remove(set);
    }
}
