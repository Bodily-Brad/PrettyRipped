package edu.byui.cs246.prettyripped;

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
     * @param exercise Exercise to retireve the Sets for
     * @return the Sets of the specified Exercise
     */
    public List<Set> getExercisesSets(Exercise exercise) {
        List<Set> sets = Set.find(Set.class, "exercise = ?", exercise.getId().toString());
        return sets;
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


}
