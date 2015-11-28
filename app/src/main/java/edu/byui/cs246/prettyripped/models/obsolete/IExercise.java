package edu.byui.cs246.prettyripped.models.obsolete;

import java.util.List;

import edu.byui.cs246.prettyripped.models.ExerciseSet;

/**
 * Represents the interface for an Exercise
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public interface IExercise {
    // GETTERS & SETTERS
    /**
     * Gets the group of this Exercise
     *
     * @return the group
     */
    public String getGroup();

    /**
     * Sets the group of this Exercise
     *
     * @param group the group to assign to this exercise
     */
    public void setGroup(String group);

    /**
     * Gets the name of this Exercise
     *
     * @return the name of this exercise
     */
    public String getName();

    /**
     * Sets the name of this Exercise
     *
     * @param name the name to assign to this exercise
     */
    public void setName(String name);

    /**
     * Gets the Sets of this Exercise
     *
     * @return a List containing the Sets of this Exercise
     */
    public List<ExerciseSet> getSets();

    // METHODS

    /**
     * Adds a ExerciseSet to this Exercise
     *
     * @param exerciseSet ExerciseSet to add to this Exercise
     */
    public void addSet(ExerciseSet exerciseSet);

    /**
     * Removes a ExerciseSet from this Exercise
     *
     * @param exerciseSet ExerciseSet to remove from this Exercise
     */
    public void removeSet(ExerciseSet exerciseSet);

}
