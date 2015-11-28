package edu.byui.cs246.prettyripped.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Represents a weight lifting "set"
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class Set extends SugarRecord<Set> implements ISet, Serializable {
    // CONSTANTS & SETTINGS
    private final static String TAG = "Set";

    /**
     * The minimum number that constitutes a valid number of reps for a Set
     */
    public static final int MIN_REPS = 0;
    /**
     * The maximum number that constitutes a valid number of reps for a Set
     */
    public static final int MAX_REPS = Integer.MAX_VALUE;

    /**
     * The minimum number that constitutes a valid weight value for a Set
     */
    public static final float MIN_WEIGHT = 0.0f;

    /**
     * The maximum number that constitutes a valid weight value for a Set
     */
    public static final float MAX_WEIGHT = Float.MAX_VALUE;

    private static final String ERROR_MESSAGE_INVALID_REP_ARGUMENT = "Invalid rep argument";
    private static final String ERROR_MESSAGE_INVALID_WEIGHT_ARGUMENT = "Invalid weight argument";

    // LOCAL VARIABLES
    /**
     * A boolean flag representing whether this Set was completed or not
     */
    public boolean completed = false;
    /**
     * The number of reps that this Set is comprised of
     */
    public int reps = 0;
    /**
     * The value of the weight that this Set is comprised of
     */
    public float weight = 0.0f;

    // CONSTRUCTORS

    /**
     * Creates a new instance of Set with the default parameters
     */
    public Set() {
        this(0, 0.0f, false);
        Log.i(TAG, "exiting Set()");
    }

    /**
     * Creates a new instance of Set with the specified parameters
     *
     * @param reps the number of reps this Set is comprised of
     * @param weight the weight value this Set is comprised of
     * @param completed a flag indicating whether this Set is completed or not
     */
    public Set(int reps, float weight, boolean completed) {
        Log.i(TAG, "Set(reps, weight, completed)");
        this.reps = reps;
        this.weight = weight;
        this.completed = completed;
        Log.i(TAG, "exiting Set(reps, weight, completed)");
    }

    /**
     * Creates a new instance of Set with the specified parameters and flag as not completed
     *
     * @param reps the number of reps this Set is comprised of
     * @param weight the weight value this Set is comprised of
     */
    public Set(int reps, float weight) {
        this(reps, weight, false);
        Log.i(TAG, "exiting Set(reps, weight)");
    }

    /**
     * Gets the completed flag for this Set
     *
     * @return the completed flag for this Set
     */
    @Override
    public boolean getCompleted() {
        return completed;
    }

    /**
     * Sets the completed flag for this Set
     *
     * @param completed a flag indicating whether or not this set was completed
     */
    @Override
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * Gets the number of reps in this Set
     *
     * @return the number of reps in the Set
     */
    @Override
    public int getReps() {
        return reps;
    }

    /**
     * Sets the number of reps in this Set
     *
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
     *
     * @return the amount of weight used in this Set
     */
    @Override
    public float getWeight() {
        return weight;
    }

    /**
     * Sets the weight used in this Set
     *
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
