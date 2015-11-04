package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.IExercise;

/**
 * Created by bradb on 11/3/2015.
 */
public class ExerciseTest extends InstrumentationTestCase {
    // Local Variables
    IExercise exercise;
    public ExerciseTest() {
        exercise = new Exercise();
    }
    public void getGroupTest() {
        exercise.setGroup("test");
        String expected = "test";
        String actual = exercise.getGroup();
        assertEquals(expected, actual);
    }
}
