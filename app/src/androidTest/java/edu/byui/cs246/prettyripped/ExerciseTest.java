package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.ExerciseSet;

/**
 * Tests our Exercise class
 *
 * @author Brad
 * @since 2015-11-03
 */
public class ExerciseTest extends InstrumentationTestCase {

    /**
     * Tests setGroupDescription()
     */
    public void testSetGroup() {
        Exercise exercise = new Exercise();

        String expected = "testGroup";
        exercise.setGroupDescription(expected);
        String actual = exercise.getGroupDescription();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    /**
     * Tests setName()
     */
    public void testSetName() {
        Exercise exercise = new Exercise();

        String expected = "testName";
        exercise.setName(expected);
        String actual = exercise.getName();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    /**
     * Tests addSet()
     */
    public void testAddSet() {
        Exercise exercise = new Exercise();
        ExerciseSet exerciseSet = new ExerciseSet();

        exercise.addSet(exerciseSet);

        // Check that exerciseSet is added
        List<ExerciseSet> exerciseSets = exercise.getExerciseSets();
        boolean containsSet = exerciseSets.contains(exerciseSet);

        // Make sure exerciseSets is not null
        assertNotNull(exerciseSets);
        // Make sure exerciseSets contains our added exerciseSet
        assertEquals(true, containsSet);
    }

    /**
     * Tests getExerciseSets()
     */
    public void testGetSets() {
        Exercise exercise = new Exercise();
        List<ExerciseSet> exerciseSets = exercise.getExerciseSets();

        // Make sure exerciseSets is not null
        assertNotNull(exerciseSets);
    }

    /**
     * Tests removeSet()
     */
    public void testRemoveSet() {
        Exercise exercise = new Exercise();
        ExerciseSet exerciseSet = new ExerciseSet();

        // ensure exerciseSet isn't already present
        boolean flag = exercise.getExerciseSets().contains(exerciseSet);
        assertEquals(false, flag);

        // Add exerciseSet
        exercise.addSet(exerciseSet);
        // Remove exerciseSet
        exercise.removeSet(exerciseSet);

        // Make sure the exerciseSet is (still) not there
        flag = exercise.getExerciseSets().contains(exerciseSet);
        assertEquals(false, flag);
    }
}
