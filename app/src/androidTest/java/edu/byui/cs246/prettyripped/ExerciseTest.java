package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.IExercise;

/**
 * Created by bradb on 11/3/2015.
 */
public class ExerciseTest extends InstrumentationTestCase {

    public void testSetGroup() {
        IExercise exercise = new Exercise();

        String expected = "testGroup";
        exercise.setGroup(expected);
        String actual = exercise.getGroup();

        assertEquals(expected, actual);
    }

    public void testSetName() {
        IExercise exercise = new Exercise();

        String expected = "testName";
        exercise.setName(expected);
        String actual = exercise.getName();

        assertEquals(expected, actual);
    }
}
