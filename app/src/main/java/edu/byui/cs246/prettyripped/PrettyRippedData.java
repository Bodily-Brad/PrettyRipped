package edu.byui.cs246.prettyripped;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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
        boolean devFlag = true;

        // Get our session data from the db
        loadSessionDataFromDB();

        // If this is dev, and we have no sessions, create some default data
        if (devFlag) {
            if (this.sessions.size() < 1) {
                debugCreateDefaultData();
                saveData();;
            }
        }
    }

    // METHODS

    /**
     * Attempts to retrieve an Exercise by Id
     *
     * @param id Id of the Exercise to retrieve
     * @return the specified Exercise
     */
    public Exercise getExerciseById(long id) {
        Log.d(TAG, "getExerciseById(id)");
        Exercise exercise = Exercise.findById(Exercise.class, id);
        // Populate Sets
        exercise.exerciseSets = loadExercisesSetsFromDB(exercise);

        return exercise;
    }

    /**
     * Gets a list of all of the exercise names being used
     *
     * @return a list of unique exercise names
     */
    public List<String> getExerciseNames() {
        // Brute force way
        List<Exercise> exercises = Exercise.listAll(Exercise.class);

        List<String> names = new ArrayList<>();

        for (Exercise exercise : exercises) {
            if (!names.contains(exercise.getName())) {
                names.add(exercise.name);
            }
        }

        Collections.sort(names);

        return names;
    }

    public Session getSessionById(long id) {
        Log.d(TAG, "getSessionById(long)");
        Session session = Session.findById(Session.class, id);
        populateSession(session);

        return session;
    }

    /**
     * Attempts to retrieve a ExerciseSet by Id
     *
     * @param id Id of the ExerciseSet to retrieve
     * @return the specified ExerciseSet
     */
    public ExerciseSet getSetById(long id) {
        Log.d(TAG, "getSetById(id)");
        return ExerciseSet.findById(ExerciseSet.class, id);
    }

    // PRIVATE FUNCTIONS

    /**
     * Creates some default data for debugging
     */
    private void debugCreateDefaultData() {
        sessions = new ArrayList<>();

        sessions.add(createRandomSession(2015,2,12));
        sessions.add(createRandomSession(2015, 3, 11));
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

    /**
     * Creates a new, empty Exercise and updates the database
     *
     * @param parent the Session this Exercise will belong to
     * @return a new Exercise belonging to the specified Session, and stored in the database
     */
    public Exercise createExercise(Session parent) {
        Log.d(TAG, "createExercise(Session)");

        return createExercise(parent, "");
    }

    /**
     * Create a new Exercise with the specified name, and updates the database
     *
     * @param parent the Session this Exercise will belong to
     * @param name the name this Exercise will have
     * @return a new Exercise belonging to the specified Session, and stored in the database
     */
    public Exercise createExercise(Session parent, String name) {
        Log.d(TAG, "createExercise(Session, String");

        Exercise ex = new Exercise();
        ex.name = name;

        // hook up parent
        parent.addExercise(ex);

        // update the parent
        updateSession(parent);

        // return the Exercise
        return  ex;
    }

    /**
     * Creates a new ExerciseSet and updates the database
     *
     * @param parent the Exercise this ExerciseSet will belong to
     * @return a new ExerciseSet belonging to the specified Exercise, and stored in the database
     */
    public ExerciseSet createExerciseSet(Exercise parent) {
        Log.d(TAG, "createExerciseSet(Exercise)");
        ExerciseSet es = new ExerciseSet();

        // hook up parent
        parent.addSet(es);
        es.exercise = parent;       // addSet handles setting the parent exercise as well, but we're
                                    // being safe.

        // update the parent
        updateExercise(parent);

        // return the ExerciseSet
        return  es;
    }

    /**
     * Creates a new Session and updates the database
     *
     * @return a new Session that's stored in the database
     */
    public Session createSession() {
        Log.d(TAG, "createSession()");
        Session session = new Session();

        // update the session
        updateSession(session);

        // Add it to our list of sessions
        this.sessions.add(session);

        // return the session
        return session;
    }

    /**
     * Deletes an Exercise from the database, including its children ExerciseSets, and updates its
     * parent Exercise in the database
     *
     * @param exercise the Exercise to delete
     */
    public void deleteExercise(Exercise exercise) {
        // first, remove it from its parent
        Session parent = exercise.session;
        if (parent != null) {
            parent.removeExercise(exercise);

            // update parent
            updateSession(parent);
        }

        // delete children
        List<ExerciseSet> exerciseSets = exercise.getExerciseSets();

        // we'll set each ExerciseSet's exercise to null to prevent each removal from triggering
        // a database update
        for (ExerciseSet es: exerciseSets) {
            es.exercise = null;
            deleteExerciseSet(es);
        }

        // now we'll delete the Exercise from the database
        exercise.delete();
    }

    /**
     * Deletes an ExerciseSet from the database, and updates its parent Exercise in the database
     *
     * @param exerciseSet the ExerciseSet to delete
     */
    public void deleteExerciseSet(ExerciseSet exerciseSet) {
        // first, remove from its parent
        Exercise parent = exerciseSet.exercise;
        if (parent != null) {
            parent.removeSet(exerciseSet);

            // update parent
            updateExercise(parent);
        }

        // delete ExerciseSet from the database
        exerciseSet.delete();
    }

    /**
     * Deletes a session, its children Exercises, and their children ExerciseSets from the database
     *
     * @param session the session to delete
     */
    public void deleteSession(Session session) {
        // first, remove it from the sessions list
        sessions.remove(session);

        // now, remove its children Exercises
        List<Exercise> exercises = session.getExercises();
        for (Exercise ex : exercises) {
            deleteExercise(ex);
        }

        // finally, delete it from the database
        session.delete();
    }

    /**
     * Attempts to retrieve all of an Exercise's Sets from the database
     *
     * @param exercise Exercise to retrieve the Sets for
     * @return the Sets of the specified Exercise
     */
    public List<ExerciseSet> loadExercisesSetsFromDB(Exercise exercise) {
        Log.d(TAG, "loadExercisesSetsFromDB(Exercise)");
        List<ExerciseSet> exerciseSets = ExerciseSet.find(ExerciseSet.class, "exercise = ?", exercise.getId().toString());
        return exerciseSets;
    }

    /**
     * Attempts to retrieve all of a Session's exercises from the database
     *
     * @param session Session to retrieve the Exercises for
     * @return the Exercises for the specified Session
     */
    public List<Exercise> loadSessionsExercisesFromDB(Session session) {
        Log.d(TAG, "loadSessionsExercisesFromDB(Session)");
        List<Exercise> exercises = Exercise.find(Exercise.class, "session = ?", session.getId().toString());
        return exercises;
    }

    /**
     * Gets a populated list of the workout Sessions from the database
     *
     * @return a list of populated Sessions
     */
    public List<Session> loadWorkoutSessionsFromDB() {
        Log.d(TAG, "loadWorkoutSessionsFromDB()");
        // Get all Sessions from the store
        //sessions = Session.listAll(Session.class);
        sessions = Session.findWithQuery(Session.class, "SELECT * FROM Session ORDER BY Time");

        // Populate each Session
        for (Session session : sessions) {
            populateSession(session);
        }

        // Return populated sessions
        return sessions;
    }

    /**
     * Saves all session data to the database
     */
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

    /**
     * Updates an Exercise (including its ExerciseSets) in the database
     *
     * @param exercise the Exercise to update
     */
    public void updateExercise(Exercise exercise) {
        Log.d(TAG, "updateExercise(Exercise)");
        // first update the Exercise itself
        exercise.save();

        // Now update the Exercise's ExerciseSets
        for (ExerciseSet exerciseSet : exercise.getExerciseSets()) {
            updateExerciseSet(exerciseSet);
        }
    }

    /**
     * Updates an ExerciseSet in the database
     *
     * @param exerciseSet the ExerciseSet to update
     */
    public void updateExerciseSet(ExerciseSet exerciseSet) {
        Log.d(TAG, "updateExerciseSet(ExerciseSet)");
        exerciseSet.save();
    }

    /**
     * Updates a Session (including its Exercises and their ExerciseSets) in the database
     *
     * @param session the Session to update
     */
    public void updateSession(Session session) {
        Log.d(TAG, "updateSession(Session)");
        // first, update the session
        session.save();

        // next, update its Exercises
        // (this will handle the ExerciseSets as well)
        for (Exercise exercise : session.getExercises()) {
            updateExercise(exercise);
        }
    }

    // PRIVATE FUNCTIONS

    /**
     * Loads the stored session data from the database
     */
    private void loadSessionDataFromDB() {
        //this.sessions = Session.listAll(Session.class);
        this.sessions = loadWorkoutSessionsFromDB();
    }

    /**
     * Populates an Exercise with its sets
     *
     * @param exercise the Exercise to populate
     */
    private void populateExercise(Exercise exercise) {
        // Get this Exercise's ExerciseSets from the store
        List<ExerciseSet> sets = loadExercisesSetsFromDB(exercise);

        // Hook up each set
        for (ExerciseSet set : sets) {
            exercise.addSet(set);
        }
    }

    private void populateSession(Session session) {
        // Get this Session's Exercises from the store
        List<Exercise> exercises = loadSessionsExercisesFromDB(session);

        // populate and hookup each Exercise
        for (Exercise exercise : exercises) {
            populateExercise(exercise);
            session.addExercise(exercise);
        }

    }

}
