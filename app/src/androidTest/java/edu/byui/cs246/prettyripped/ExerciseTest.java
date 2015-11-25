package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.IExercise;
import edu.byui.cs246.prettyripped.models.ISet;
import edu.byui.cs246.prettyripped.models.Set;

/**
 * Tests our Exercise class
 *
 * @author Brad
 * @since 2015-11-03
 */
public class ExerciseTest extends InstrumentationTestCase {

    /**
     * Tests setGroup()
     */
    public void testSetGroup() {
        IExercise exercise = new Exercise();

        String expected = "testGroup";
        exercise.setGroup(expected);
        String actual = exercise.getGroup();

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    /**
     * Tests setName()
     */
    public void testSetName() {
        IExercise exercise = new Exercise();

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
        IExercise exercise = new Exercise();
        ISet set = new Set();

        exercise.addSet(set);

        // Check that set is added
        List<ISet> sets = exercise.getSets();
        boolean containsSet = sets.contains(set);

        // Make sure sets is not null
        assertNotNull(sets);
        // Make sure sets contains our added set
        assertEquals(true, containsSet);
    }

    /**
     * Tests getSets()
     */
    public void testGetSets() {
        IExercise exercise = new Exercise();
        List<ISet> sets = exercise.getSets();

        // Make sure sets is not null
        assertNotNull(sets);
    }

    /**
     * Tests removeSet()
     */
    public void testRemoveSet() {
        IExercise exercise = new Exercise();
        ISet set = new Set();

        // ensure set isn't already present
        boolean flag = exercise.getSets().contains(set);
        assertEquals(false, flag);

        // Add set
        exercise.addSet(set);
        // Remove set
        exercise.removeSet(set);

        // Make sure the set is (still) not there
        flag = exercise.getSets().contains(set);
        assertEquals(false, flag);
    }
}
