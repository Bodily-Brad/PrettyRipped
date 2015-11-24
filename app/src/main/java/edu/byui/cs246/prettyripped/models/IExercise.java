package edu.byui.cs246.prettyripped.models;

import java.util.List;

/**
 * Represents the interface for an Exercise
 * @author Brad Bodily
 * @since 2015-11-03
 */
public interface IExercise {
    // GETTERS & SETTERS
    /**
     * Gets the group of this Exercise
     * @return the group
     */
    public String getGroup();

    /**
     * Sets the group of this Exercise
     * @param group the group to assign to this exercise
     */
    public void setGroup(String group);

    /**
     * Gets the name of this Exercise
     * @return the name of this exercise
     */
    public String getName();

    /**
     * Sets the name of this Exercise
     * @param name the name to assign to this exercise
     */
    public void setName(String name);

    /**
     * Gets the Sets of this Exercise
     * @return a List containing the Sets of this Exercise
     */
    public List<ISet> getSets();

    // METHODS

    /**
     * Adds a Set to this Exercise
     * @param set Set to add to this Exercise
     */
    public void addSet(ISet set);

    /**
     * Removes a Set from this Exercise
     * @param set Set to remove from this Exercise
     */
    public void removeSet(ISet set);

}
