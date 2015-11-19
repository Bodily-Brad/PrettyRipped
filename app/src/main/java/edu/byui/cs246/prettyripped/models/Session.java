package edu.byui.cs246.prettyripped.models;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by bradb on 11/3/2015.
 * TODO: function for comparing two sets? Not sure what the best way is in Java
 */
public class Session extends SugarRecord<Session> implements ISession {
    // LOCAL VARIABLES
    private List<IExercise> exercises = new ArrayList<>();
    private Date time;

    /**
     * Adds an Exercise to this Session
     * @param exercise the Exercise to add to this Session
     */
    @Override
    public void addExercise(IExercise exercise) {
        exercises.add(exercise);
    }

    /**
     * Removes an Exercise from this Session
     * @param exercise the Exercise to remove from this Session
     */
    @Override
    public void removeExercise(IExercise exercise) {
        // TODO: presumably some error-checking here
        // I don't think Lists 'handle' removing invalid items on their own
        // (i.e., I don't think it causes any problems to remove an item that doesn't exist in the
        // list)
        exercises.remove(exercise);
    }

    /**
     * Gets a list of the Exercises contained within this Session
     * @return a list of the Exercises contained within this Session
     */
    @Override
    public List<IExercise> getExercises() {
        return exercises;
    }

    /**
     *
     * @return
     */
    @Override
    public Date getTime() {
        return time;
    }

    /**
     * Sets the time of this Session
     * @param time the time to assign to this Session
     */
    @Override
    public void setTime(Date time) {
        this.time = time;
    }
}
