package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.IExercise;

/**
 * Created by bradb on 11/3/2015.
 */
public class ExerciseTest extends InstrumentationTestCase {

    public void testSetGroupTest() {
        IExercise exercise = new Exercise();
        exercise.setGroup("test");
        String expected = "test";
        String actual = exercise.getGroup();
        assertEquals(expected, actual);
    }
}
