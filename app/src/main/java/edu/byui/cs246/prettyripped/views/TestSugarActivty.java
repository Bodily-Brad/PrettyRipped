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

import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.Session;
import edu.byui.cs246.prettyripped.models.Set;

public class TestSugarActivty extends AppCompatActivity {
    // CONSTANTS & SETTINGS
    private final static String TAG = "TestSugarActivty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sugar_activty);
    }

    public void createDefaultData(View view) {
        Log.i(TAG,"createDefaultData");

        // Create a date
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        Date date;

        cal.set(2015, 10, 31);
        date= cal.getTime();

        // Create a Session
        Session session = new Session();
        session.setTime(date);

        // Create Exercise #1
        Exercise ex1 = new Exercise();
        ex1.setName("Curl");
        ex1.setGroup("Default Group");

        // Create Set #1 (Exercise #1)
        Set set1 = new Set();
        set1.setReps(10);
        set1.setWeight(15);

        // Create Set #2 (Exercise #1)
        Set set2 = new Set();
        set2.setReps(10);
        set2.setWeight(20);

        // Add Sets to Exercise #1
        ex1.addSet(set1);
        ex1.addSet(set2);

        // Add Exercise #1 to Session
        session.addExercise(ex1);

        // Save Set #1
        Log.i(TAG, "attempting to save Set #1");
        set1.save();

        // Save session and see what happens
        Log.i(TAG, "attempting to save session");
        session.save();
    }

    public void loadAllSessions(View view) {
        List<Session> sessions;

        sessions = Session.listAll(Session.class);
        int count = sessions.size();

        // Display Count
        Snackbar.make(view, "Count: " + count, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();

    }
}
