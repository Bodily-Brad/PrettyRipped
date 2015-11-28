package edu.byui.cs246.prettyripped;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.Session;
import edu.byui.cs246.prettyripped.models.Set;

/**
 * Application data singleton
 *
 * Holds all the application data for Pretty Ripped
 *
 * @author Brad Bodily
 * @since 2015-11-27
 */
public class PrettyRippedData {
    private static final String TAG = "PrettyRippedData";

    // LOCAL VARIABLES
    public List<Session> sessions = new ArrayList<>();

    private static PrettyRippedData ourInstance = new PrettyRippedData();

    /**
     * Gets the single instance of PrettyRippedData
     *
     * @return the instance of PrettyRippedData
     */
    public static PrettyRippedData getInstance() {
        return ourInstance;
    }

    private PrettyRippedData() {
        // Create our default data
        createDefaultData();
        // Save, just this once
        //saveData();
        //loadData();
    }

    // METHODS

    /**
     * Attempts to retrieve an Exercise by Id
     *
     * @param id Id of the Exercise to retrieve
     * @return the specified Exercise
     */
    public Exercise getExerciseById(long id) {
        return Exercise.findById(Exercise.class, id);
    }

    /**
     * Attempts to retrieve all of an Exercise's Sets
     *
     * @param exercise Exercise to retrieve the Sets for
     * @return the Sets of the specified Exercise
     */
    public List<Set> getExercisesSets(Exercise exercise) {
        List<Set> sets = Set.find(Set.class, "exercise = ?", exercise.getId().toString());
        return sets;
    }

    /**
     * Attempts to retrieve all of a Session's exercises
     *
     * @param session Session to retrieve the Exercises for
     * @return the Exercises for the specified Session
     */
    public List<Exercise> getSessionsExercises(Session session) {
        List<Exercise> exercises = Exercise.find(Exercise.class, "session = ?", session.getId().toString());
        return exercises;
    }

    /**
     * Attempts to retrieve a Set by Id
     *
     * @param id Id of the Set to retrieve
     * @return the specified Set
     */
    public Set getSetById(long id) {
        return Set.findById(Set.class, id);
    }

    // PRIVATE FUNCTIONS

    /**
     * Creates some default data
     */
    private void createDefaultData() {
        sessions = new ArrayList<>();

        sessions.add(createRandomSession(2015,2,12));
        sessions.add(createRandomSession(2015,3,11));
        sessions.add(createRandomSession(2015,4,10));
        sessions.add(createRandomSession(2015,5,9));
        sessions.add(createRandomSession(2015,6,8));
        sessions.add(createRandomSession(2015,7,7));
    }

    private Session createRandomSession(int year, int month, int day) {
        Calendar cal = new GregorianCalendar();
        cal.set(year, month, day);

        // Create exercises
        List<Exercise> exercises = new ArrayList<>();

        int exCount = (int)Math.ceil(Math.random() * 3 + 1);
        for (int i=0; i < exCount; i++) {
            exercises.add(createRandomExercise());
        }

        return new Session(cal.getTime(), exercises);

    }

    private Exercise createRandomExercise() {
        List<Set> sets = new ArrayList<>();

        // Pick name/group
        int randomName = (int)Math.ceil(Math.random() * 2);
        String name = "Bench Press";
        String group = "Chest";
        switch (randomName) {
            case 1:
                name = "Curls";
                group = "Arms";
                break;
            case 2:
                name = "Dead";
                group = "Legs";
                break;
        }

        // Create sets
        int setCount = (int)Math.ceil(Math.random() * 4 + 1);
        for (int i=0; i < setCount; i++) {
            sets.add(createRandomSet());
        }

        return new Exercise(name, group, sets);
    }

    private Set createRandomSet() {
        int reps = (int)Math.ceil(Math.random() * 10 + 5);
        float weight = (float)Math.ceil(Math.random() * 50 + 10);
        boolean completed = false;

        return new Set(reps, weight, completed);
    }

    private void loadData() {
        Log.d(TAG, "loadData()");

        List<Session> allSessions = Session.listAll(Session.class);
        Log.i(TAG, "allSessions count: " + allSessions.size());
    }

    private void saveData() {
        Log.d(TAG, "saveData()");

        // Just save one session, that's all I ask
        Session sessiona = sessions.get(0);
        sessiona.save();

        if (true) {
            return;
        }


        // Loop through all sessions for key "hook ups"
        for (Session session : sessions) {

            // Loop through all exercises
            for (Exercise exercise : session.getExercises()) {
                // Make sure session is hooked up
                if (exercise.session != session) {
                    Log.e(TAG, "mismatched session/exercise");
                }
                exercise.session = session;

                // Loop through all sets
                for (Set set : exercise.getSets()) {
                    // Make sure exercise is hooked up
                    if (set.exercise != exercise) {
                        Log.e(TAG, "mismatched exercise/set");
                    }
                    set.exercise = exercise;
                }

            }
        }

        // Now, let's save everything

        /*
        for (Session session : sessions) {
            session.save();
            for (Exercise exercise : session.getExercises()) {
                exercise.save();
                for (Set set : exercise.getSets()) {
                    set.save();
                }
            }
        }
        */
    }

}
