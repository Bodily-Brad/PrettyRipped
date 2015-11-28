package edu.byui.cs246.prettyripped;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;
import edu.byui.cs246.prettyripped.models.ExerciseSet;
import edu.byui.cs246.prettyripped.models.Session;

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
        // Create default data
        debugCreateDefaultData();

        // Save it
        saveData();

        // load session data
        //loadSessionDataFromDB();
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
    public List<ExerciseSet> getExercisesSets(Exercise exercise) {
        List<ExerciseSet> exerciseSets = ExerciseSet.find(ExerciseSet.class, "exercise = ?", exercise.getId().toString());
        return exerciseSets;
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
     * Attempts to retrieve a ExerciseSet by Id
     *
     * @param id Id of the ExerciseSet to retrieve
     * @return the specified ExerciseSet
     */
    public ExerciseSet getSetById(long id) {
        return ExerciseSet.findById(ExerciseSet.class, id);
    }

    // PRIVATE FUNCTIONS

    /**
     * Creates some default data for debugging
     */
    private void debugCreateDefaultData() {
        sessions = new ArrayList<>();

        sessions.add(createRandomSession(2015,2,12));
        sessions.add(createRandomSession(2015,3,11));
        sessions.add(createRandomSession(2015,4,10));
        sessions.add(createRandomSession(2015,5,9));
        sessions.add(createRandomSession(2015,6,8));
        sessions.add(createRandomSession(2015,7,7));
    }

    /**
     * Creates a random Session with the specified date
     *
     * @param year year
     * @param month month (base-0)
     * @param day day
     * @return A random session
     */
    public Session createRandomSession(int year, int month, int day) {
        // TODO: Remove this function
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
        List<ExerciseSet> exerciseSets = new ArrayList<>();

        // Pick name/groupDescription
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

        // Create exerciseSets
        int setCount = (int)Math.ceil(Math.random() * 4 + 1);
        for (int i=0; i < setCount; i++) {
            exerciseSets.add(createRandomSet());
        }

        return new Exercise(name, group, exerciseSets);
    }

    private ExerciseSet createRandomSet() {
        int reps = (int)Math.ceil(Math.random() * 10 + 5);
        float weight = (float)Math.ceil(Math.random() * 50 + 10);
        boolean completed = false;

        return new ExerciseSet(reps, weight, completed);
    }

    /**
     * Creates a random ExerciseSet
     *
     * @param parentExercise Exercise this ExerciseSet will belong to
     * @return a randomized ExerciseSet
     */
    private ExerciseSet createRandomSet(Exercise parentExercise) {
        int reps = (int)Math.ceil(Math.random() * 10 + 5);
        float weight = (float)Math.ceil(Math.random() * 50 + 10);
        boolean completed = false;

        return new ExerciseSet(parentExercise, reps, weight, completed);
    }

    private void loadData() {
        Log.d(TAG, "loadData()");

        List<Session> allSessions = Session.listAll(Session.class);
        Log.i(TAG, "allSessions count: " + allSessions.size());
    }

    public void saveData() {
        Log.d(TAG, "saveData()");

        // Loop through all sessions for key "hook ups"
        for (Session session : sessions) {

            // Loop through all exercises
            for (Exercise exercise : session.getExercises()) {
                // Make sure session is hooked up
                if (exercise.session != session) {
                    Log.e(TAG, "mismatched session/exercise");
                } else {
                    Log.d(TAG, "session/exercise data match");
                }
                exercise.session = session;

                // Loop through all exerciseSets
                for (ExerciseSet exerciseSet : exercise.getExerciseSets()) {
                    // Make sure exercise is hooked up
                    if (exerciseSet.exercise != exercise) {
                        Log.e(TAG, "mismatched exercise/exerciseSet");
                    } else {
                        Log.d(TAG, "exercise/exerciseSet data match");
                    }
                    exerciseSet.exercise = exercise;
                }

            }
        }

        // Now, let's save everything

        for (Session session : sessions) {
            session.save();
            for (Exercise exercise : session.getExercises()) {
                exercise.save();
                for (ExerciseSet set : exercise.getExerciseSets()) {
                    set.save();
                }
            }
        }
    }

    // PRIVATE FUNCTIONS

    /**
     * Loads the stored session data from the database
     */
    private void loadSessionDataFromDB() {
        this.sessions = Session.listAll(Session.class);
    }

}
