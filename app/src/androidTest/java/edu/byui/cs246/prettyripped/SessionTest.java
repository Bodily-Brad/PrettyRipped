package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.IExercise;
import edu.byui.cs246.prettyripped.models.ISession;
import edu.byui.cs246.prettyripped.models.Session;

/**
 * Created by Brandon on 11/4/2015.
 */
public class SessionTest extends InstrumentationTestCase {

    public void testAddExercise() {
        ISession testSession = new Session();
        List<IExercise> exerciseList = new ArrayList<>();

        testSession.addExercise(new Exercise());
        exerciseList = testSession.getExercises();

        assertEquals(1, exerciseList.size());
        assertEquals(new Exercise(), exerciseList.get(0));
    }

    public void testRemoveExercise() {
        ISession testSession = new Session();
        List<IExercise> exerciseList = new ArrayList<>();
        List<IExercise> testExerciseList = new ArrayList<>();

        // populate the session with some numbered exercises
        for (int i = 1; i <= 3; ++i) {
            IExercise tempExercise = new Exercise();
            tempExercise.setName(Integer.toString(i));
            tempExercise.setGroup(Integer.toString(i));

            testSession.addExercise(tempExercise);
            testExerciseList.add(tempExercise);
        }

        // remove those exercises and check to make sure each was removed properly
        for (IExercise tempExercise : testExerciseList) {
            testSession.removeExercise(tempExercise);

            exerciseList = testSession.getExercises();
            assertFalse(exerciseList.contains(tempExercise));

        }
        testSession.addExercise(new Exercise());

        assertNotNull(exerciseList.size() == 0);
    }






}
