package edu.byui.cs246.prettyripped.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byui.cs246.prettyripped.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.RippedTextView;
import edu.byui.cs246.prettyripped.controls.SessionsExpandableListAdapter;
import edu.byui.cs246.prettyripped.models.Session;

/**
 * Displays a list of selectable sessions
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class SessionsActivity extends AppCompatActivity {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionsActivity";

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;

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


        // Get data from app data singleton
        PrettyRippedData data = PrettyRippedData.getInstance();

        // ExerciseSet up expandable list
        listView = (ExpandableListView) findViewById(R.id.sessionList);

        // Create adapter
        List<Session> session = data.sessions;
        listAdapter = new SessionsExpandableListAdapter(SessionsActivity.this, data.sessions);

        // Attach adapter to list
        listView.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get data handler
                PrettyRippedData data = PrettyRippedData.getInstance();

                // get a new session
                Session session = data.createSession();

                Calendar cal = GregorianCalendar.getInstance();
                Date time = cal.getTime();

                Snackbar.make(view, "Created a new session. And the time is " + time.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // TODO: Session list / adapter needs updated to reflect new session has been added
            }
        });
    }

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
}
