package edu.byui.cs246.prettyripped.models.obsolete;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;

/**
 * Represents the interface to a Session
 * <p>A Session represents a collection of exercises; typically performed together. I.e., a
 * Session might represent all of the exercises performed during a single visit to the gym.</p>
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public interface ISession {
    /**
     * Adds an Exercise to this Session
     *
     * @param exercise the Exercise to add to this Session
     */
    public void addExercise(Exercise exercise);

    /**
     * Removes an Exercise from this Session
     *
     * @param exercise the Exercise to remove from this Session
     */
    public void removeExercise(Exercise exercise);

    /**
     * Gets a list of the Exercises contained within this Session
     *
     * @return a list of the Exercises contained within this Session
     */
    List<Exercise> getExercises();

    /**
     * Gets the time of this Session
     *
     * @return the time of this Session
     */
    public Date getTime();

    /**
     * Sets the time of this Session
     *
     * @param time the time to assign to this Session
     */
    public void setTime(Date time);

}