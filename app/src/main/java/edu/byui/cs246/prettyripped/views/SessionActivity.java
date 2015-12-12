package edu.byui.cs246.prettyripped.views;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

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
public class SessionActivity extends AppCompatActivity implements Observer {
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
    private PrettyRippedData data;

    /**
     * Called when the activity is created, exerciseSets up layout and data
     *
     * @param savedInstanceState Instance state information
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate()");

        super.onCreate(savedInstanceState);

        // Get app data handler, and hook up as observer
        data = PrettyRippedData.getInstance();
        data.addObserver(this);

        setContentView(R.layout.activity_session);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get passed session ID
        final long sessionID = getIntent().getLongExtra(this.SESSION_ID_KEY, 0);

        // Get session from data handler
        session = data.getSessionById(sessionID);


        this.setTitle("Workout Details");

        // Set title from session
        TextView sessionDate = (TextView) findViewById(R.id.sessionDate);
        sessionDate.setText(session.toString());

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

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.d(TAG, "onChildClick");
                return false;
            }
        });



        // Set up pink icon
        initFloatingActionButton();

        // Set up long click listener
        initLongClickListener();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initToolbar();
    }


    /**
     * Refreshes the UI to match the current data
     */
    public void refreshUI() {
        Log.d(TAG, "refreshUI()");
        // refresh the titles
        refreshTitle();

        // refresh the exercise list
        refreshExerciseList();
    }

    // PRIVATE FUNCTIONS

    /**
     * Initializes the floating action button, including setting up a listener
     */
    private void initFloatingActionButton() {
        final Session session = this.session;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Prompt the user for an exercise name
                Context context = view.getContext();

                // Inflate the dialog view
                LayoutInflater li = LayoutInflater.from(context);
                final View dialogView = li.inflate(R.layout.dialog_prompt, null);

                // Build the alert dialog
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setView(dialogView);

                // Get Exercise names from data handler & assign to auto complete
                //final PrettyRippedData data = PrettyRippedData.getInstance();
                List<String> names = data.getExerciseNames();
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, names);

                // Get a handle to the edit control, and populate it with a default name
                final AutoCompleteTextView editText = (AutoCompleteTextView) dialogView.findViewById(R.id.userInput);
                editText.setText(R.string.default_exercise_name);
                editText.selectAll();
                editText.setAdapter(arrayAdapter);

                final View viewHandle = view;

                // Setup and show dialog
                alertDialogBuilder
                        .setIcon(android.R.drawable.ic_menu_edit)
                        .setTitle(R.string.prompt_new_exercise_name)
                        .setPositiveButton(R.string.button_create_exercise_name, new DialogInterface.OnClickListener() {
                            // Do this when the user confirms
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Capture the entered name
                                String name = editText.getText().toString();

                                // Create a new exercise
                                Exercise exercise = data.createExercise(session, name);

                                // Update the exercise
                                data.updateExercise(exercise);
                                refreshExerciseList();
                            }
                        })
                        .setNegativeButton(R.string.button_cancel_exercise_name, null)
                        .show();
            }
        });
    }

    /**
     * Configures the listener for long clicks, this is where we handle renaming exercises
     */
    private void initLongClickListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Get packedID from position (this is a weird thing we have to do)
                long packedID = ((ExpandableListView) parent).getExpandableListPosition(position);
                // Convert the packedID into a group position
                int groupPosition = ExpandableListView.getPackedPositionGroup(packedID);
                // Also get a child position (it should be -1 if we clicked on a group)
                int childPosition = ExpandableListView.getPackedPositionChild(packedID);
                Log.d(TAG, "childPosition: " + childPosition);

                // If we're not a child, we're a group
                if (childPosition < 0) {
                    final Exercise exercise = (Exercise) listAdapter.getGroup(groupPosition);

                    // Get context and inflater so we can inflate the view
                    Context context = view.getContext();
                    LayoutInflater layoutInflater = LayoutInflater.from(context);

                    // Create a dialog view
                    final View dialogView = layoutInflater.inflate(R.layout.dialog_prompt, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                    alertDialogBuilder.setView(dialogView);

                    // Get a handle to the edit control, and populate it with the current exercise name
                    final AutoCompleteTextView editText = (AutoCompleteTextView) dialogView.findViewById(R.id.userInput);
                    editText.setThreshold(1);

                    // Get Exercise names from data handler & assign to auto complete
                    List<String> names = data.getExerciseNames();
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, names);


                    editText.setText(exercise.getName());
                    editText.selectAll();
                    editText.setAdapter(arrayAdapter);


                    alertDialogBuilder
                            .setIcon(android.R.drawable.ic_menu_edit)
                            .setTitle(R.string.prompt_edit_exercise_name)
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String input = editText.getText().toString();
                                    exercise.name = input;

                                    // Update the exercise
                                    data.updateExercise(exercise);

                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                }


                return false;
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final Context context = this;
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(session.getTime());

        TextView sessionDate = (TextView) findViewById(R.id.sessionDate);


        // Set up long press on title
        sessionDate.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                // Create a DatePicker
                DatePickerDialog dialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // Make a calendar and all the junk that you have to do to get a date
                        Calendar cal = new GregorianCalendar();
                        cal.set(year, monthOfYear, dayOfMonth);

                        // Assign the time from the picker to the session
                        session.time = cal.getTime();

                        // Update the session with the handler
                        data.updateSession(session);

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dialog.show();
                return false;
            }
        });
    }

    /**
     * Refreshes the exercise list
     */
    public void refreshExerciseList() {
        Log.d(TAG, "refreshExerciseList()");
        // Create adapter
        listAdapter = new ExerciseExpandableListAdapter(SessionActivity.this, session);

        // Attach adapter to list
        listView.setAdapter(listAdapter);

        // Expand all the lists
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            listView.expandGroup(i);
        }
    }

    /**
     * Refreshes the exercise title
     */
    private void refreshTitle() {
        TextView sessionDate = (TextView) findViewById(R.id.sessionDate);
        sessionDate.setText(session.toString());
    }

    @Override
    public void update(Observable observable, Object data) {
        Log.d(TAG, "update(Observable, Object)");
        initToolbar();

        refreshTitle();

        if (observable.getClass() == PrettyRippedData.class) {
            //refreshUI();
        }
    }
}
