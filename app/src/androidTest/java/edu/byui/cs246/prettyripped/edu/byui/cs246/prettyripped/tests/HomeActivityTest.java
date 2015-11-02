package edu.byui.cs246.prettyripped.edu.byui.cs246.prettyripped.tests;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import edu.byui.cs246.prettyripped.HomeActivity;
import edu.byui.cs246.prettyripped.R;

/**
 * Created by bradb on 11/2/2015.
 */
public class HomeActivityTest extends ActivityInstrumentationTestCase2<HomeActivity> {
    // LOCAL VARIABLES
    private HomeActivity homeActivity;
    private TextView textViewMain;

    public  HomeActivityTest() {
        super(HomeActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        homeActivity = getActivity();
        textViewMain = (TextView) homeActivity.findViewById(R.id.textViewMain);
    }

    public void testPreconditions() {
        assertNotNull("homeActivity is null", homeActivity);
        assertNotNull("textViewMain is null", textViewMain);
    }

    public void testHomeActivityTest_textViewMainText() {
        final String expected =
                homeActivity.getString(R.string.helloMessage);
        final String actual = textViewMain.getText().toString();
        assertEquals(expected, actual);
    }
}
