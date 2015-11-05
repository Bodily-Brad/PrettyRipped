package edu.byui.cs246.prettyripped.models;

/**
 * Created by Brad Bodily on 11/3/2015.
 */

/**
 * Represents the interface to a Set
 */
public interface ISet {
    /**
     * Gets the number of reps in this Set
     * @return the number of reps in the Set
     */
    public int getReps();

    /**
     * Sets the number of reps in this Set
     * @param reps the number of reps
     */
    public void setReps(int reps);

    /**
     * Gets the amount of weight used in this Set
     * @return the amount of weight used in this Set
     */
    public float getWeight();

    /**
     * Sets the weight used in this Set
     * @param weight the amount of weight used in this Set
     */
    public void setWeight(float weight);
}
