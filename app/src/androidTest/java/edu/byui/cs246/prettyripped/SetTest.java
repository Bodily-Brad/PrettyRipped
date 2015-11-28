package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import edu.byui.cs246.prettyripped.models.Set;

/**
 * Tests our Set class
 *
 * @author Brandon
 * @since 2015-11-04
 */
public class SetTest extends InstrumentationTestCase {
    /**
     * Tests setReps() and getReps()
     */
    public void testReps() {
        Set testSet = new Set();
        int num = 14239;

        testSet.setReps(num);
        assertEquals(num, testSet.getReps());
    }

    /**
     * Tests setWeight() and getWeight() with a float
     */
    public void testWeight() {
        Set testSet = new Set();
        float num = (float) 14.53;

        testSet.setWeight(num);
        assertEquals(num, testSet.getWeight());
    }
}
