package edu.byui.cs246.prettyripped.models;

/**
 * Created by Brad Bodily on 11/3/2015.
 */

/**
 * Represents the interface to a Set
 * @author Brad Bodily
 * @since 2015-03-11
 */
public interface ISet {
    /**
     * Gets the completed flag for this Set
     * @return the completed flag for this Set
     */
    public boolean getCompleted();

    /**
     * Sets the completed flag for this Set
     * @param completed a flag indicating whether or not this set was completed
     */
    public void setCompleted(boolean completed);

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
