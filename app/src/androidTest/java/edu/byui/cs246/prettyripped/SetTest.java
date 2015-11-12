package edu.byui.cs246.prettyripped;

import android.test.InstrumentationTestCase;

import edu.byui.cs246.prettyripped.models.ISet;
import edu.byui.cs246.prettyripped.models.Set;

/**
 * Created by Brandon on 11/4/2015.
 */
public class SetTest extends InstrumentationTestCase {
    public void testReps() {
        ISet testSet = new Set();
        int num = 14239;

        testSet.setReps(num);
        assertEquals(num, testSet.getReps());
    }

    public void testWeight() {
        ISet testSet = new Set();
        float num = (float) 14.53;

        testSet.setWeight(num);
        assertEquals(num, testSet.getWeight());
    }
}