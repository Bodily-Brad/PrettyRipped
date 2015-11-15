package edu.byui.cs246.prettyripped.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.List;

import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.controls.ExerciseExpandableListAdapter;
import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.IExercise;
import edu.byui.cs246.prettyripped.models.ISet;
import edu.byui.cs246.prettyripped.models.Set;

public class SessionActivity extends AppCompatActivity {
    // LOCAL VARIABLES
    private List<IExercise> exercises;

    private ExpandableListAdapter listAdapter;
    private ExpandableListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        // To-do: Get rid of this, this is just a test to see if it will
        // set the title in the "blue bar"
        this.setTitle("Date");

        // Make up some data tha
        createDefaultData();

        // Set up expandable list
        listView = (ExpandableListView) findViewById(R.id.exerciseList);

        // Create adapter
        listAdapter = new ExerciseExpandableListAdapter(SessionActivity.this, exercises);

        // Attach adapter to list
        listView.setAdapter(listAdapter);
    }

    private void createDefaultData() {
        // Just make some dummy data
        exercises = new ArrayList<IExercise>();

        IExercise ex;
        ISet set;

        // Exercise 1
        ex = new Exercise();
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

        exercises.add(ex);

        // Exercise 2
        ex = new Exercise();
        ex.setName("Bench Press");

        // Set of 60lb x 5
        set = new Set();
        set.setReps(5);
        set.setWeight(60);
        ex.addSet(set);

        // Set of 72 lbs x 6
        set = new Set();
        set.setReps(6);
        set.setWeight(72);
        ex.addSet(set);

        exercises.add(ex);




    }

}
