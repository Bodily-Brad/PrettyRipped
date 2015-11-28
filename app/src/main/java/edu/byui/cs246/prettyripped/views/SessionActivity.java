package edu.byui.cs246.prettyripped.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;

import edu.byui.cs246.prettyripped.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.controls.ExerciseExpandableListAdapter;
import edu.byui.cs246.prettyripped.models.Exercise;

/**
 * Displays a session
 *
 * @author Brad Bodily
 * @since 2015-11-03
 */
public class SessionActivity extends AppCompatActivity {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionActivity";

    public final static String SESSION_KEY = "SESSION";

    // LOCAL VARIABLES

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;

    /**
     * Called when the activity is created, exerciseSets up layout and data
     *
     * @param savedInstanceState Instance state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate()");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Todo: Get rid of this, this is just a test to see if it will
        // set the title in the "blue bar"
        this.setTitle("Date");

        // Make up some data tha
        PrettyRippedData data = PrettyRippedData.getInstance();

        // Get session
        ArrayList<Exercise> exercises = (ArrayList<Exercise>) getIntent().getSerializableExtra(this.SESSION_KEY);

        // ExerciseSet up expandable list
        listView = (ExpandableListView) findViewById(R.id.exerciseList);

        // Create adapter
        listAdapter = new ExerciseExpandableListAdapter(SessionActivity.this, exercises);

        // Attach adapter to list
        listView.setAdapter(listAdapter);
    }

}
