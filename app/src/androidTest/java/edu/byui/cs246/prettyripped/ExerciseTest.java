package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;
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
        Exercise exercise = new Exercise();

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
        Set set = new Set();

        exercise.addSet(set);

        // Check that set is added
        List<Set> sets = exercise.getSets();
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
        Exercise exercise = new Exercise();
        List<Set> sets = exercise.getSets();

        // Make sure sets is not null
        assertNotNull(sets);
    }

    /**
     * Tests removeSet()
     */
    public void testRemoveSet() {
        Exercise exercise = new Exercise();
        Set set = new Set();

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
