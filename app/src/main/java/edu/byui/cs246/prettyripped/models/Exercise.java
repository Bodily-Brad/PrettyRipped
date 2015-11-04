package edu.byui.cs246.prettyripped.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brad Bodily on 11/3/2015.
 */

/**
 * Represents an individual exercise activity, including a name, group, and a collection of Sets
 */
public class Exercise implements IExercise{
    // LOCAL VARIABLES
    private String name;
    private String group;
    private List<ISet> sets = new ArrayList<>();

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
