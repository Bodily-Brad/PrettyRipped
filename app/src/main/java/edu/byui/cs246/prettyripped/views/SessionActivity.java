package edu.byui.cs246.prettyripped.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byui.cs246.prettyripped.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.controls.ExerciseExpandableListAdapter;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.Session;

/**
 * Displays a session
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class SessionActivity extends AppCompatActivity {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionActivity";

    /**
     * Extra key for passed Session ID
     */
    public final static String SESSION_ID_KEY = "SESSION_ID";

    // LOCAL VARIABLES

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;
    private Session session;

    /**
     * Called when the activity is created, exerciseSets up layout and data
     *
     * @param savedInstanceState Instance state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get data handler
        PrettyRippedData data = PrettyRippedData.getInstance();

        // Get passed session ID
        final long sessionID = getIntent().getLongExtra(this.SESSION_ID_KEY, 0);

        // Get session from data handler
        session = data.getSessionById(sessionID);

        // Set title from session
        this.setTitle(session.toString());

        // Get exercise list from session
        List<Exercise> exercises = session.getExercises();

        // Set up expandable list
        listView = (ExpandableListView) findViewById(R.id.exerciseList);

        // Create adapter
        listAdapter = new ExerciseExpandableListAdapter(SessionActivity.this, session);

        // Attach adapter to list
        listView.setAdapter(listAdapter);

        // Expand all our groups
        for (int i=0; i < listAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }

        // Set up pink icon
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the calling activity
                SessionActivity sessionActivity = (SessionActivity) view.getContext();
                Session session = sessionActivity.session;

                // get data handler
                PrettyRippedData data = PrettyRippedData.getInstance();
                Exercise ex = data.createExercise(session);

                // Give it a default name
                // TODO: Get the name from the user
                ex.name = "Created Exercise";
                data.updateExercise(ex);

                // Refresh the SessionActivity's list
                sessionActivity.refreshList();

                // Give some output
                Snackbar.make(view, "Created a new exercise", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Context context = this;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(session.getTime());
        final Session passedSession = session;

        // Set up long press on title
        toolbar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Get data handler
                        PrettyRippedData data = PrettyRippedData.getInstance();

                        // Make a calendar and all the junk that you have to do to get a date
                        Calendar cal = new GregorianCalendar();
                        cal.set(year, monthOfYear, dayOfMonth);

                        // Assign the time from the picker to the session
                        passedSession.time = cal.getTime();

                        // Update the session with the handler
                        data.updateSession(passedSession);

                        // TODO: Update view with new date
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
                return false;
            }
        });
    }

    public void refreshList() {
        // Create adapter
        listAdapter = new ExerciseExpandableListAdapter(SessionActivity.this, session);

        // Attach adapter to list
        listView.setAdapter(listAdapter);

        for (int i=0; i < listAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
    }

}
