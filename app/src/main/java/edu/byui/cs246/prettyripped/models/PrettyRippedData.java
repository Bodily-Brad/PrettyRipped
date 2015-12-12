package edu.byui.cs246.prettyripped.models;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Observable;

/**
 * Application data singleton
 *
 * Holds all the application data for Pretty Ripped
 *
 * @author Brad Bodily
 * @since 2015-11-27
 */
public class PrettyRippedData extends Observable {
    /**
     * Debugging Tag
     */
    private static final String TAG = "PrettyRippedData";
    private static final boolean DEV_MODE = false;

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
        // Get our session data from the db
        loadSessionDataFromDB();

        // If this is dev, and we have no sessions, create some default data
        if (DEV_MODE) {
            if (this.sessions.size() < 1) {
                debugCreateDefaultData();
                saveData();
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
        for (Session checkSession : sessions) {
            if (checkSession.getId() == id) {
                return checkSession;
            }
        }

        return null;
        //Session session = Session.findById(Session.class, id);
        //populateSession(session);

        //return session;
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
    private Session createRandomSession(int year, int month, int day) {
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
        // Call the more-specific method
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
        // Create a new Exercise, and set the name
        Exercise ex = new Exercise();
        ex.name = name;

        // hook up parent
        parent.addExercise(ex);

        // update the parent
        updateSessionWithoutNotification(parent);

        // Now create an empty exercise set
        ExerciseSet es = createExerciseSet(ex);

        // Notify Observers
        setChanged();
        notifyObservers();

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
        // Create a new ExerciseSet
        ExerciseSet es = new ExerciseSet();

        // hook up parent
        parent.addSet(es);
        es.exercise = parent;       // addSet handles setting the parent exercise as well, but we're
                                    // being safe.

        // update the parent
        updateExerciseWithoutNotification(parent);

        // Notify Observers
        setChanged();
        notifyObservers();

        // return the ExerciseSet
        return  es;
    }

    /**
     * Creates a new Session and updates the database
     *
     * @return a new Session that's stored in the database
     */
    public Session createSession() {
        Session session = new Session();

        // update the session
        updateSessionWithoutNotification(session);

        // Add it to our list of sessions
        this.sessions.add(session);

        // Requery db to keep sessions in order
        loadWorkoutSessionsFromDB();

        // Notify Observers
        setChanged();
        notifyObservers();

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
        // Delete the Exercise (and everything that entails) without notifications,
        // we'll notify the observers after all the work is done
        deleteExerciseWithoutNotification(exercise);

        // Notify observers
        setChanged();
        notifyObservers();
    }

    /**
     * Deletes an ExerciseSet from the database, and updates its parent Exercise in the database
     *
     * @param exerciseSet the ExerciseSet to delete
     */
    public void deleteExerciseSet(ExerciseSet exerciseSet) {
        // Delete the ExerciseSet (and everything that entails) without notifications,
        // we'll notify the observers after all the work is done
        deleteExerciseSetWithoutNotification(exerciseSet);

        // Notify observers
        setChanged();
        notifyObservers();
    }

    /**
     * Deletes a session, its children Exercises, and their children ExerciseSets from the database
     *
     * @param session the session to delete
     */
    public void deleteSession(Session session) {
        // Delete the session (and everything that entails) without notifications,
        // we'll notify the observers after all the work is done
        deleteSessionWithoutNotification(session);

        // Notify observers
        setChanged();
        notifyObservers();
    }

    /**
     * Attempts to retrieve all of an Exercise's Sets from the database
     *
     * @param exercise Exercise to retrieve the Sets for
     * @return the Sets of the specified Exercise
     */
    public List<ExerciseSet> loadExercisesSetsFromDB(Exercise exercise) {
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
        List<Exercise> exercises = Exercise.find(Exercise.class, "session = ?", session.getId().toString());
        return exercises;
    }

    /**
     * Gets a populated list of the workout Sessions from the database
     *
     * @return a list of populated Sessions
     */
    public List<Session> loadWorkoutSessionsFromDB() {
        // Get all Sessions from the store
        //sessions = Session.listAll(Session.class);
        sessions = Session.findWithQuery(Session.class, "SELECT * FROM Session ORDER BY Time DESC");

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
        // Loop through all sessions for key "hook ups"
        for (Session session : sessions) {

            // Loop through all exercises
            for (Exercise exercise : session.getExercises()) {
                // Make sure session is hooked up
                exercise.session = session;

                // Loop through all exerciseSets
                for (ExerciseSet exerciseSet : exercise.getExerciseSets()) {
                    // Make sure exercise is hooked up
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

    /**
     * Updates an Exercise (including its ExerciseSets) in the database
     *
     * @param exercise the Exercise to update
     */
    public void updateExercise(Exercise exercise) {
        // Update the exercise without notifications...
        updateExerciseWithoutNotification(exercise);

        // ...then, notify the observers
        notifyObservers();
    }

    /**
     * Updates an ExerciseSet in the database
     *
     * @param exerciseSet the ExerciseSet to update
     */
    public void updateExerciseSet(ExerciseSet exerciseSet) {
        // Update the ExerciseSet without notifications...
        updateExerciseSetWithoutNotification(exerciseSet);

        // ...then, notify the observers
        notifyObservers();
    }

    /**
     * Updates a Session (including its Exercises and their ExerciseSets) in the database
     *
     * @param session the Session to update
     */
    public void updateSession(Session session) {
        // Update the Session without notifications...
        updateSessionWithoutNotification(session);

        // Requery to keep sessions in order
        loadWorkoutSessionsFromDB();

        // ...then, notify the observers
        notifyObservers();
    }

    // PRIVATE FUNCTIONS

    /**
     * Deletes an ExerciseSet from the database, and updates its parent Exercise in the database,
     * but does notify observers. This allows us to use this function in a loop without sending
     * notifications for each iteration, for example.
     *
     * @param exerciseSet the ExerciseSet to delete
     */
    private void deleteExerciseSetWithoutNotification(ExerciseSet exerciseSet) {
        // first, remove from its parent
        Exercise parent = exerciseSet.exercise;
        if (parent != null) {
            parent.removeSet(exerciseSet);

            // update parent
            updateExerciseWithoutNotification(parent);
        }

        // delete ExerciseSet from the database
        exerciseSet.delete();

        // Set changed
        setChanged();
    }

    /**
     * Deletes an Exercise from the database, including its children ExerciseSets, and updates its
     * parent Exercise in the database, but does not notify observers. This allows us to use this
     * function in a loop without sending notifications for each iteration, for example.
     *
     * @param exercise the Exercise to delete
     */
    private void deleteExerciseWithoutNotification(Exercise exercise) {
        // first, remove it from its parent
        Session parent = exercise.session;
        if (parent != null) {
            parent.removeExercise(exercise);

            // update parent
            updateSessionWithoutNotification(parent);
        }

        // delete children
        List<ExerciseSet> exerciseSets = exercise.getExerciseSets();

        // we'll set each ExerciseSet's exercise to null to prevent each removal from triggering
        // a database update
        for (ExerciseSet es: exerciseSets) {
            es.exercise = null;
            deleteExerciseSetWithoutNotification(es);
        }

        // now we'll delete the Exercise from the database
        exercise.delete();

        // Set changed
        setChanged();
    }

    /**
     * Deletes a session, its children Exercises, and their children ExerciseSets from the database,
     * but does not notify observers. This allows us to use this function in a loop without sending
     * notifications for each iteration, for example.
     *
     * @param session the session to delete
     */
    private void deleteSessionWithoutNotification(Session session) {
        // first, remove it from the sessions list
        sessions.remove(session);

        // now, we'll unhook and remove its children Exercises
        List<Exercise> exercises = session.getExercises();
        for (Exercise ex : exercises) {
            ex.session = null;
            deleteExerciseWithoutNotification(ex);
        }

        // finally, delete it from the database
        session.delete();

        // Set changed
        setChanged();
    }

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

    /**
     * Updates an Exercise (including its ExerciseSets) in the database, but does not notify
     * observers. This allows us to use this function in a loop without sending out notifications
     * for each iteration, for example.
     *
     * @param exercise the Exercise to update
     */
    private void updateExerciseWithoutNotification(Exercise exercise) {
        // first save the Exercise itself
        exercise.save();

        // Set changed
        setChanged();

        // Now update the Exercise's ExerciseSets
        for (ExerciseSet exerciseSet : exercise.getExerciseSets()) {
            updateExerciseSetWithoutNotification(exerciseSet);
        }
    }

    /**
     * Updates an ExerciseSet in the database, but does not notify observers. This allows us to use
     * this function in a loop without sending out notifications for each iteration, for example.
     *
     * @param exerciseSet the ExerciseSet to update
     */
    private void updateExerciseSetWithoutNotification(ExerciseSet exerciseSet) {
        // save the ExerciseSet
        exerciseSet.save();

        // Set changed
        setChanged();
    }

    /**
     * Updates a Session (including its Exercises, and their ExerciseSets) in the database, but does
     * not notify observers. This allows us to use this function in a loop without sending repeated
     * notifications for each iteration, for example.
     *
     * @param session The Session to update
     */
    private void updateSessionWithoutNotification(Session session) {
        // first, save the session
        session.save();

        // Set changed
        setChanged();

        // next, update its Exercises
        // (this will handle the ExerciseSets as well)
        for (Exercise exercise : session.getExercises()) {
            updateExerciseWithoutNotification(exercise);
        }
    }

}
