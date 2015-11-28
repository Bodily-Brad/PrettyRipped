package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.Session;

/**
 * Tests our Session class
 *
 * @author Brandon
 * @since 2015-11-04
 */
public class SessionTest extends InstrumentationTestCase {

    /**
     * Tests addExercise()
     */
    public void testAddExercise() {
        Session testSession = new Session();
        List<Exercise> exerciseList = new ArrayList<>();

        testSession.addExercise(new Exercise());
        exerciseList = testSession.getExercises();

        assertEquals(1, exerciseList.size());
        assertEquals(new Exercise(), exerciseList.get(0));
    }

    /**
     * Tests removeExercise()
     */
    public void testRemoveExercise() {
        Session testSession = new Session();
        List<Exercise> exerciseList = new ArrayList<>();
        List<Exercise> testExerciseList = new ArrayList<>();

        // populate the session with some numbered exercises
        for (int i = 1; i <= 3; ++i) {
            Exercise tempExercise = new Exercise();
            tempExercise.setName(Integer.toString(i));
            tempExercise.setGroup(Integer.toString(i));

            testSession.addExercise(tempExercise);
            testExerciseList.add(tempExercise);
        }

        // remove those exercises and check to make sure each was removed properly
        for (Exercise tempExercise : testExerciseList) {
            testSession.removeExercise(tempExercise);

            exerciseList = testSession.getExercises();
            assertFalse(exerciseList.contains(tempExercise));

        }
        testSession.addExercise(new Exercise());

        assertNotNull(exerciseList.size() == 0);
    }
}
