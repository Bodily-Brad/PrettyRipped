package edu.byui.cs246.prettyripped.models.obsolete;

/**
 * Created by Brad Bodily on 11/3/2015.
 */

/**
 * Represents the interface to a ExerciseSet
 *
 * @author Brad Bodily
 * @since 2015-03-11
 */
public interface ISet {
    /**
     * Gets the completed flag for this ExerciseSet
     *
     * @return the completed flag for this ExerciseSet
     */
    public boolean getCompleted();

    /**
     * Sets the completed flag for this ExerciseSet
     *
     * @param completed a flag indicating whether or not this set was completed
     */
    public void setCompleted(boolean completed);

    /**
     * Gets the number of reps in this ExerciseSet
     *
     * @return the number of reps in the ExerciseSet
     */
    public int getReps();

    /**
     * Sets the number of reps in this ExerciseSet
     *
     * @param reps the number of reps
     */
    public void setReps(int reps);

    /**
     * Gets the amount of weight used in this ExerciseSet
     *
     * @return the amount of weight used in this ExerciseSet
     */
    public float getWeight();

    /**
     * Sets the weight used in this ExerciseSet
     *
     * @param weight the amount of weight used in this ExerciseSet
     */
    public void setWeight(float weight);
}
