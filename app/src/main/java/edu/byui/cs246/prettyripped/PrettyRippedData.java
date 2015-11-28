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
    /**
     * Debugging Tag
     */
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
        Exercise exercise = Exercise.findById(Exercise.class, id);
        // Populate Sets
        exercise.exerciseSets = getExercisesSets(exercise);

        return exercise;
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

    public Session getSessionById(long id) {
        Session session = Session.findById(Session.class, id);
        populateSession(session);

        return session;
    }
    /**
     * Gets a populated list of the workout Sessions
     *
     * @return a list of populated Sessions
     */
    public List<Session> getWorkoutSessions() {
        // Get all Sessions from the store
        sessions = Session.listAll(Session.class);

        // Populate each Session
        for (Session session : sessions) {
            populateSession(session);
        }

        // Return populated sessions
        return sessions;
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

        int exCount = (int)Math.ceil(Math.random() * 2 + 1);
        for (int i=0; i < exCount; i++) {
            exercises.add(createRandomExercise());
        }

        return new Session(cal.getTime(), exercises);

    }

    private Exercise createRandomExercise() {
        List<ExerciseSet> exerciseSets = new ArrayList<>();

        // Pick name/groupDescription
        int randomName = (int)Math.ceil(Math.random() * 6);
        String name = "";
        String group = "";
        switch (randomName) {
            case 1:
                name = "Squat";
                group = "Legs";
                break;
            case 2:
                name = "Leg press";
                group = "Hamstrings";
                break;
            case 3:
                name = "Lunge";
                group = "Legs";
                break;
            case 4:
                name = "Deadlift";
                group = "Legs";
                break;
            case 5:
                name = "Push-up";
                group = "Chest";
                break;
            case 6:
                name = "Pull-up";
                group = "Arms";
                break;
        }

        // Create exerciseSets
        int setCount = (int)Math.ceil(Math.random() * 2 + 1);
        for (int i=0; i < setCount; i++) {
            exerciseSets.add(createRandomSet());
        }

        return new Exercise(name, group, exerciseSets);
    }

    private ExerciseSet createRandomSet() {
        int reps = ((int)Math.ceil(Math.random() * 3)) * 5;
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
        int reps = ((int)Math.ceil(Math.random() * 3)) * 5;
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
            Log.d(TAG, "session.getExercises().size(): " + session.getExercises().size());

            for (Exercise exercise : session.getExercises()) {
                exercise.save();
                Log.d(TAG, "exercise.getId(): " + exercise.getId());
                Log.d(TAG, "exercise.getName(): " + exercise.getName());


                Log.d(TAG, "exercise.getExerciseSets().size(): " + exercise.getExerciseSets().size());
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

    /**
     * Populates an Exercise with its sets
     *
     * @param exercise the Exercise to populate
     */
    private void populateExercise(Exercise exercise) {
        // Get this Exercise's ExerciseSets from the store
        List<ExerciseSet> sets = getExercisesSets(exercise);

        // Hook up each set
        for (ExerciseSet set : sets) {
            exercise.addSet(set);
        }
    }

    private void populateSession(Session session) {
        // Get this Session's Exercises from the store
        List<Exercise> exercises = getSessionsExercises(session);

        // populate and hookup each Exercise
        for (Exercise exercise : exercises) {
            populateExercise(exercise);
            session.addExercise(exercise);
        }

    }

}
