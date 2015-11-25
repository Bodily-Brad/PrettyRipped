package edu.byui.cs246.prettyripped.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.controls.SessionsExpandableListAdapter;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.IExercise;
import edu.byui.cs246.prettyripped.models.ISession;
import edu.byui.cs246.prettyripped.models.ISet;
import edu.byui.cs246.prettyripped.models.Session;
import edu.byui.cs246.prettyripped.models.Set;

/**
 * Displays a list of selectable sessions
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class SessionsActivity extends AppCompatActivity {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionsActivity";

    // LOCAL VARIABLES
    private List<ISession> sessions;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;

    /**
     * Called when the activity is created, sets up layout and data
     *
     * @param savedInstanceState Instance state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Make up some data tha
        createDefaultData();

        // Set up expandable list
        listView = (ExpandableListView) findViewById(R.id.sessionList);

        // Create adapter
        listAdapter = new SessionsExpandableListAdapter(SessionsActivity.this, sessions);

        // Attach adapter to list
        listView.setAdapter(listAdapter);

        Log.i(TAG, "onCreate() Finished");
    }

    /**
     * Start a SessionActivity with a given session
     *
     * @param view The view to run under
     */
    public void openSessionActivity(View view) {
        // Start a SessionActivity
        // TODO: Pass a session to the session activity
        Log.i(TAG, "openSessionActivity()");
        Intent intent = new Intent(SessionsActivity.this, SessionActivity.class);
        SessionsActivity.this.startActivity(intent);
    }

    private void createDefaultData() {
        // Calendar
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        Date date;



        // Just make some dummy data
        sessions = new ArrayList<ISession>();
        sessions.add(buildTestSession(2015,10,30,12,10));
        sessions.add(buildTestSession(2015,10,30,12,10));
        sessions.add(buildTestSession(2015,10,30,12,10));
        sessions.add(buildTestSession(2015,10,30,12,45));
    }

    private ISession buildTestSession(int year, int month, int day, int hour, int min) {
        Log.i(TAG, "buildTestSession(" + year + ", " + month + ", " + day + ", "
                                       + hour + ", " + min + ")");
        // Calendar
        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        Date date;

        cal.set(year, month, day, hour, min,0);
        date = cal.getTime();

        ISession session = new Session();
        session.setTime(date);

        // Make some exercises
        IExercise ex = new Exercise();
        ISet set;
        ex.setName("Curl");

        // Set of 10lb x 5
        set = new Set();
        set.setCompleted(true);
        set.setReps(5);
        set.setWeight(10);
        ex.addSet(set);

        // Set of 12 lbs x 6
        set = new Set();
        set.setReps(6);
        set.setWeight(12);
        ex.addSet(set);

        session.addExercise(ex);

        return session;
    }
}
