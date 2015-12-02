package edu.byui.cs246.prettyripped.models;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an individual exercise activity, including a name, groupDescription, and a collection of Sets
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class Exercise extends SugarRecord<Exercise> implements Serializable {
    // CONSTANTS & SETTINGS
    private final static String TAG = "Exercise";

    // PUBLIC VARIABLES
    /**
     * The name of the Exercise
     */
    public String name;

    /**
     * A string "group" descriptor that categorizes this Exercise
     */
    public String groupDescription;

    /**
     * The Session this Exercise belongs to
     */
    public Session session;

    /**
     * A collection of Sets that comprise this Exercise's exerciseSets
     */
    @Ignore
    public List<ExerciseSet> exerciseSets = new ArrayList<>();

    // CONSTRUCTORS

    /**
     * Creates a new instance of Exercise with the default parameters
     */
    public  Exercise() {
        this("Exercise", "No Group", new ArrayList<ExerciseSet>());
    }

    /**
     * Creates a new instance of Exercise with the specified parameters
     *
     * @param name the name of this Exercise
     * @param groupDescription a groupDescription descriptor for this Exercise
     * @param exerciseSets a collection of Sets for this Exercise
     */
    public Exercise(String name, String groupDescription, List<ExerciseSet> exerciseSets) {
        Log.i(TAG, "Exercise(name, groupDescription, exerciseSets)");
        this.name = name;
        this.groupDescription = groupDescription;

        // Add each set (this ensures the set is 'hooked up'
        for (ExerciseSet set : exerciseSets) {
            addSet(set);
        }
    }

    /**
     * Gets the groupDescription of this Exercise
     *
     * @return the groupDescription of this Exercise
     */
    public String getGroupDescription() {
        return groupDescription;
    }

    /**
     * Sets the groupDescription of this Exercise
     *
     * @param groupDescription the groupDescription to assign to this exercise
     */
    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    /**
     * Gets the name of this Exercise
     *
     * @return the name of this Exercise
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of this Exercise
     *
     * @param name the name to assign to this exercise
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the Sets of this Exercise
     *
     * @return a List containing the Sets of this Exercise
     */
    @Ignore
    public List<ExerciseSet> getExerciseSets() {
        return exerciseSets;
    }

    /**
     * Adds a ExerciseSet to this Exercise
     *
     * @param exerciseSet ExerciseSet to add to this Exercise
     */
    public void addSet(ExerciseSet exerciseSet) {
        // Hook up
        exerciseSet.exercise = this;
        // Add to our list
        this.exerciseSets.add(exerciseSet);
    }

    /**
     * Removes a ExerciseSet from this Exercise
     *
     * @param exerciseSet ExerciseSet to remove from this Exercise
     */
    public void removeSet(ExerciseSet exerciseSet) {
        this.exerciseSets.remove(exerciseSet);
    }
}
