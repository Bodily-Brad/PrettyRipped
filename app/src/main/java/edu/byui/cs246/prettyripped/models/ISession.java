package edu.byui.cs246.prettyripped.models;

import java.sql.Date;
import java.util.List;

/**
 * Created by bradb on 11/3/2015.
 */
public interface ISession {
    public void addExercise(IExercise exercise);
    public void removeExercise(IExercise exercise);
    public void getExercises(List<IExercise> exercises);
    public Date getTime();
    public void setTime(Date date);

}
