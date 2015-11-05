package edu.byui.cs246.prettyripped.models;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bradb on 11/3/2015.
 * TODO: function for comparing two sets? Not sure what the best way is in Java
 */
public class Session implements ISession {
    List<IExercise> exercises = new ArrayList<>();


    @Override
    public void addExercise(IExercise exercise) {
        exercises.add(exercise);

    }

    @Override
    public void removeExercise(IExercise exercise) {
        // TODO: presumably some error-checking here
        exercises.remove(exercise);

    }

    @Override
    public List<IExercise> getExercises() {
        return exercises;
    }

    @Override
    public Date getTime() {
        return null;
    }

    @Override
    public void setTime(Date date) {

    }
}
