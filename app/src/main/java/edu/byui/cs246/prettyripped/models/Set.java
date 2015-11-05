package edu.byui.cs246.prettyripped.models;

/**
 * Created by bradb on 11/3/2015.
 */
public class Set implements ISet {
    // CONSTANTS & SETTINGS
    public static final int MIN_REPS = 0;
    public static final int MAX_REPS = Integer.MAX_VALUE;

    public static final float MIN_WEIGHT = 0.0f;
    public static final float MAX_WEIGHT = Float.MAX_VALUE;

    private static final String ERROR_MESSAGE_INVALID_REP_ARGUMENT = "Invalid rep argument";
    private static final String ERROR_MESSAGE_INVALID_WEIGHT_ARGUMENT = "Invalid weight argument";
    // LOCAL VARIABLES
    private int reps = 0;
    private float weight = 0.0f;

    /**
     * Gets the number of reps in this Set
     * @return the number of reps in the Set
     */
    @Override
    public int getReps() {
        return reps;
    }

    /**
     * Sets the number of reps in this Set
     * @param reps the number of reps
     * @throws IllegalArgumentException
     */
    @Override
    public void setReps(int reps) {
        // Range checking
        if ( (reps >= MIN_REPS) && (reps <= MAX_REPS)) {
            this.reps = reps;
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_REP_ARGUMENT);
        }
    }

    /**
     * Gets the amount of weight used in this Set
     * @return the amount of weight used in this Set
     */
    @Override
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight used in this Set
     * @param weight the amount of weight used in this Set
     * @throws IllegalArgumentException
     */
    @Override
    public void setWeight(float weight) throws IllegalArgumentException {
        // Range checking
        if ( (weight >= MIN_REPS) && (weight <= MAX_REPS)) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_WEIGHT_ARGUMENT);
        }
    }
}
