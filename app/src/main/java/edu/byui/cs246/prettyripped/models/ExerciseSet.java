package edu.byui.cs246.prettyripped.models;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;

/**
 * Represents a weight lifting "set"
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class ExerciseSet extends SugarRecord<ExerciseSet> implements Serializable {
    // CONSTANTS & SETTINGS
    /**
     * Debugging tag
     */
    private final static String TAG = "ExerciseSet";

    /**
     * Error message if an invalid rep argument is passed
     */
    private static final String ERROR_MESSAGE_INVALID_REP_ARGUMENT = "Invalid rep argument";

    /**
     * Error message if an invalid weight argument is passed
     */
    private static final String ERROR_MESSAGE_INVALID_WEIGHT_ARGUMENT = "Invalid weight argument";

    /**
     * The minimum number that constitutes a valid number of reps for a ExerciseSet
     */
    public static final int MIN_REPS = 0;
    /**
     * The maximum number that constitutes a valid number of reps for a ExerciseSet
     */
    public static final int MAX_REPS = Integer.MAX_VALUE;

    /**
     * The minimum number that constitutes a valid weight value for a ExerciseSet
     */
    public static final float MIN_WEIGHT = 0.0f;

    /**
     * The maximum number that constitutes a valid weight value for a ExerciseSet
     */
    public static final float MAX_WEIGHT = Float.MAX_VALUE;


    // PUBLIC MEMBERS
    /**
     * A boolean flag representing whether this ExerciseSet was completed or not
     */
    public boolean completed = false;

    /**
     * The Exercise this ExerciseSet belongs to
     */
    public Exercise exercise;

    /**
     * The number of reps that this ExerciseSet is comprised of
     */
    public int reps = 0;

    /**
     * The value of the weight that this ExerciseSet is comprised of
     */
    public float weight = 0.0f;


    // CONSTRUCTORS

    /**
     * Creates a new instance of ExerciseSet with the default parameters
     */
    public ExerciseSet() {
        this(0, 0.0f, false);
        Log.i(TAG, "exiting ExerciseSet()");
    }

    /**
     * Creates a new instance of ExerciseSet with the specified parameters
     *
     * @param reps the number of reps this ExerciseSet is comprised of
     * @param weight the weight value this ExerciseSet is comprised of
     * @param completed a flag indicating whether this ExerciseSet is completed or not
     */
    public ExerciseSet(int reps, float weight, boolean completed) {
        Log.i(TAG, "ExerciseSet(reps, weight, completed)");
        this.reps = reps;
        this.weight = weight;
        this.completed = completed;
        Log.i(TAG, "exiting ExerciseSet(reps, weight, completed)");
    }

    /**
     * Creates a new instance of ExerciseSet with the specified parameters
     *
     * @param exercise the Exercise this ExerciseSet belongs to
     * @param reps the number of reps this ExerciseSet is comprised of
     * @param weight the weight value this ExerciseSet is comprised of
     * @param completed a flag indicating whether this ExerciseSet is completed or not
     */
    public ExerciseSet(Exercise exercise, int reps, float weight, boolean completed) {
        Log.d(TAG, "ExerciseSet(exericse, reps, weight, completed)");

        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
        this.completed = completed;
    }

    /**
     * Creates a new instance of ExerciseSet with the specified parameters and flag as not completed
     *
     * @param reps the number of reps this ExerciseSet is comprised of
     * @param weight the weight value this ExerciseSet is comprised of
     */
    public ExerciseSet(int reps, float weight) {
        this(reps, weight, false);
        Log.i(TAG, "exiting ExerciseSet(reps, weight)");
    }

    /**
     * Gets the completed flag for this ExerciseSet
     *
     * @return the completed flag for this ExerciseSet
     */
    public boolean getCompleted() {
        return completed;
    }

    /**
     * Sets the completed flag for this ExerciseSet
     *
     * @param completed a flag indicating whether or not this set was completed
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the number of reps in this ExerciseSet
     *
     * @return the number of reps in the ExerciseSet
     */
    public int getReps() {
        return reps;
    }

    /**
     * Sets the number of reps in this ExerciseSet
     *
     * @param reps the number of reps
     * @throws IllegalArgumentException
     */
    public void setReps(int reps) {
        // Range checking
        if ( (reps >= MIN_REPS) && (reps <= MAX_REPS)) {
            this.reps = reps;
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_REP_ARGUMENT);
        }
    }

    /**
     * Gets the amount of weight used in this ExerciseSet
     *
     * @return the amount of weight used in this ExerciseSet
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight used in this ExerciseSet
     *
     * @param weight the amount of weight used in this ExerciseSet
     * @throws IllegalArgumentException
     */
    public void setWeight(float weight) throws IllegalArgumentException {
        // Range checking
        if ( (weight >= MIN_REPS) && (weight <= MAX_REPS)) {
            this.weight = weight;
        } else {
            throw new IllegalArgumentException(ERROR_MESSAGE_INVALID_WEIGHT_ARGUMENT);
        }
    }
}
