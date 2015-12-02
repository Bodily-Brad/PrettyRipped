package edu.byui.cs246.prettyripped.models;

import android.util.Log;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents an exercise session.
 * <p>A Session represents a collection of exercises; typically performed together. I.e., a
 * Session might represent all of the exercises performed during a single visit to the gym.</p>
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class Session extends SugarRecord<Session> {

    // CONSTANTS & SETTINGS
    private final static String TAG = "Session";

    // LOCAL VARIABLES
    @Ignore
    private List<Exercise> exercises = new ArrayList<>();

    // PUBLIC VARIABLES

    /**
     * The date this session took place
     */
    public Date time;

    // CONSTRUCTORS

    /**
     * Creates a new instance of Session with the default parameters
     */
    public Session() {
        this(new Date(), new ArrayList<Exercise>());
    }

    /**
     * Creates a new instance of Session with the specified parameters
     *
     * @param date the date of this Session
     * @param exercises a collection of Exercises that this Session will be comprised of
     */
    public Session(Date date, List<Exercise> exercises) {
        Log.d(TAG, "Session(Date, List<Exercise>)");
        this.time = date;

        // Add exercises; this ensures each exercise is 'hooked up'
        for (Exercise exercise : exercises) {
            addExercise(exercise);
        }
    }

    // METHODS

    /**
     * Adds an Exercise to this Session
     *
     * @param exercise the Exercise to add to this Session
     */
    public void addExercise(Exercise exercise) {
        Log.d(TAG, "addExercise(Exercise)");
        // Hook up parent
        exercise.session = this;
        // Add to list
        exercises.add(exercise);
    }

    /**
     * Removes an Exercise from this Session
     *
     * @param exercise the Exercise to remove from this Session
     */
    public void removeExercise(Exercise exercise) {
        // TODO: presumably some error-checking here
        // I think Lists 'handle' removing invalid items on their own
        // (i.e., I don't think it causes any problems to remove an item that doesn't exist in the
        // list)
        exercises.remove(exercise);
    }

    /**
     * Gets a list of the Exercises contained within this Session
     *
     * @return a list of the Exercises contained within this Session
     */
    @Ignore
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Gets the time of this Session
     *
     * @return the time of this Session (date it took place)
     */
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time of this Session
     *
     * @param time the time to assign to this Session
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * String description of this object
     *
     * @return a string description of this object
     */
    @Override
    public String toString() {
        java.text.DateFormat format = new java.text.SimpleDateFormat("MMM d, yyyy");
        return format.format(time) + " (" + this.id + ")";
    }
}
