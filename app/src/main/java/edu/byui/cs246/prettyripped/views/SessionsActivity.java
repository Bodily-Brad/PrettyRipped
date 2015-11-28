package edu.byui.cs246.prettyripped.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import edu.byui.cs246.prettyripped.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.SessionTextView;
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

        // Get data from app data singleton
        PrettyRippedData data = PrettyRippedData.getInstance();

        // Set up expandable list
        listView = (ExpandableListView) findViewById(R.id.sessionList);

        // Create adapter
        listAdapter = new SessionsExpandableListAdapter(SessionsActivity.this, data.sessions);

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

        SessionTextView sessionTextView = (SessionTextView)view;
        List<IExercise> exercises = sessionTextView.exercises;

        // Start a SessionActivity
        // TODO: Pass a session to the session activity
        Log.i(TAG, "openSessionActivity()");
        Intent intent = new Intent(SessionsActivity.this, SessionActivity.class);

        // Horrible conversion
        ArrayList<Exercise> newList = new ArrayList<>();
        for (IExercise iex : exercises) {
            newList.add(new Exercise(iex.getName(), iex.getGroup(), iex.getSets()));
        }
        intent.putExtra(SessionActivity.SESSION_KEY, newList);

        SessionsActivity.this.startActivity(intent);
    }
}
