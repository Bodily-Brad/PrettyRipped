package edu.byui.cs246.prettyripped.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Observable;
import java.util.Observer;

import edu.byui.cs246.prettyripped.models.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.controls.SessionsExpandableListAdapter;
import edu.byui.cs246.prettyripped.models.Session;
import edu.byui.cs246.prettyripped.views.RippedTextView;

/**
 * Displays a list of selectable sessions
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class SessionsActivity extends AppCompatActivity implements Observer {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionsActivity";

    // LOCAL VARIABLES
    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private PrettyRippedData data;

    /**
     * Called when the activity is created, exerciseSets up layout and data
     *
     * @param savedInstanceState Instance state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.title_workout_sessions);

        // Set data to app data singleton
        data = PrettyRippedData.getInstance();

        // ExerciseSet up expandable list
        listView = (ExpandableListView) findViewById(R.id.sessionList);

        // Create adapter
        listAdapter = new SessionsExpandableListAdapter(SessionsActivity.this, data.sessions);

        // Attach adapter to list
        listView.setAdapter(listAdapter);

        // Set up as an observer for PrettyRippedData
        data.addObserver(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // get a new session

                Session session = data.createSession();

                Calendar cal = GregorianCalendar.getInstance();
                Date time = cal.getTime();

                refreshSessionList();

                // Open Session view
                startSessionActivity(session.getId());
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        // We're refreshing the list when focus comes back to this activity
        refreshSessionList();
    }

    // METHODS

    /**
     * Start a SessionActivity with a given session
     *
     * @param view Calling view
     */
    public void openSessionActivity(View view) {
        // Cast to SessionTextView so we can retrieve the session ID from it
        RippedTextView rippedTextView = (RippedTextView)view;

        // Start SessionActivity with the specified SessionID
        startSessionActivity(rippedTextView.rippedID);
    }

    /**
     * Starts a SessionActivity for the specified Session (by ID)
     *
     * @param sessionID ID of the Session to display in the SessionActivity
     */
    public void startSessionActivity(long sessionID) {
        // Start a SessionActivity
        Intent intent = new Intent(SessionsActivity.this, SessionActivity.class);

        // Add SessionID to intent, using session ID from the clicked view
        intent.putExtra(SessionActivity.SESSION_ID_KEY, sessionID);

        // Start activity
        SessionsActivity.this.startActivity(intent);
    }

    /**
     * Refreshes the exercise list
     */
    private void refreshSessionList() {

        // Create adapter
        listAdapter = new SessionsExpandableListAdapter(SessionsActivity.this, data.sessions);

        // Attach adapter to list
        listView.setAdapter(listAdapter);

        // Expand all the lists
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
    }

    // OBSERVER METHODS

    /**
     * Is called when the Observable updates
     *
     * @param observable object that is being observed
     * @param data update data
     */
    @Override
    public void update(Observable observable, Object data) {
        if (observable.getClass() == PrettyRippedData.class) {
            refreshSessionList();
        }
    }
}
