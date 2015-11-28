package edu.byui.cs246.prettyripped.views;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import edu.byui.cs246.prettyripped.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.ExerciseSet;
import edu.byui.cs246.prettyripped.models.Session;

/**
 * An activity for testing SugarORM integration
 *
 * @author Brad Bodily
 * @since 2015-11-18
 */
public class TestSugarActivty extends AppCompatActivity {
    // CONSTANTS & SETTINGS
    private final static String TAG = "TestSugarActivty";

    /**
     * Is called on creation
     * @param savedInstanceState instace state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sugar_activty);
    }

    /**
     * Creates the default PrettyRippedData
     *
     * @param view Calling view
     */
    public void createDefaultRippedData(View view) {
        Log.d(TAG, "createDefaultRippedData(view)");
        // Get instance of PrettyRippedData, which will create the data
        PrettyRippedData data = PrettyRippedData.getInstance();
    }

    /**
     * Attempts to save the current PrettyRippedData
     *
     * @param view Calling view
     */
    public void saveRippedData(View view) {
        Log.d(TAG, "saveRippedData(view)");
        PrettyRippedData data = PrettyRippedData.getInstance();

        data.saveData();
    }

    /**
     * Attempts to load all saved Sessions from the store
     *
     * @param view Calling view
     */
    public void loadAllSessions(View view) {
        List<Session> sessions;

        sessions = Session.listAll(Session.class);
        int count = sessions.size();
        Log.d(TAG, "sessions.size(): " + sessions.size());

        // Display Count
        Snackbar.make(view, "Count: " + count, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }

    /**
     * Testing function to create and save a single ExerciseSet
     *
     * @param view Calling view
     */
    public void createAndSaveSingleSet(View view) {
        ExerciseSet exerciseSet = new ExerciseSet(10, 15.05f, false);
        exerciseSet.save();

    }
}
