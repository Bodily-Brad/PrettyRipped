package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import edu.byui.cs246.prettyripped.models.ExerciseSet;

/**
 * Tests our ExerciseSet class
 *
 * @author Brandon
 * @since 2015-11-04
 */
public class ExerciseSetTest extends InstrumentationTestCase {
    /**
     * Tests setReps() and getReps()
     */
    public void testReps() {
        ExerciseSet testExerciseSet = new ExerciseSet();
        int num = 14239;

        testExerciseSet.setReps(num);
        assertEquals(num, testExerciseSet.getReps());
    }

    /**
     * Tests setWeight() and getWeight() with a float
     */
    public void testWeight() {
        ExerciseSet testExerciseSet = new ExerciseSet();
        float num = (float) 14.53;

        testExerciseSet.setWeight(num);
        assertEquals(num, testExerciseSet.getWeight());
    }
}
