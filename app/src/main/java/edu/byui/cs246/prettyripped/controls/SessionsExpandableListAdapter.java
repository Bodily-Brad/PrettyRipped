package edu.byui.cs246.prettyripped.controls;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.models.ISession;
import edu.byui.cs246.prettyripped.SessionTextView;
import edu.byui.cs246.prettyripped.models.Session;

/**
 * Represents an adapter between a list of sessions and an expandable list
 *
 * @author Brad Bodliy
 * @since 2015-11-14
 */
public class SessionsExpandableListAdapter extends BaseExpandableListAdapter {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionsListAdapter";

    // LOCAL VARIABLES
    private Context context;
    private List<ISession> sessions = new ArrayList<>();
    private List<Date> dates;

    /**
     * Creates a new instance of SessionsExpandableListAdapter
     *
     * @param context Context
     * @param sessions list of sessions to list
     */
    public SessionsExpandableListAdapter(Context context, List<ISession> sessions) {
        this.context = context;
        this.sessions = sessions;

        // Build dates list
        // TODO: This is not treating equal dates as equal, and we get identical dates showing up
        // multiple times

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

        dates = new ArrayList<>();
        for (ISession session : sessions) {
            cal.setTime(session.getTime());
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            if (!dates.contains(cal.getTime() ) ) {
                dates.add(cal.getTime());
            }
        }
    }

    /**
     * Gets the number of group elements
     *
     * @return the number of group elements
     */
    @Override
    public int getGroupCount() {
        return dates.size();
    }

    /**
     * Gets the number of children elements within a specified group
     *
     * @param groupPosition the specified group
     * @return the number of children elements within the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        // Get date
        Date date = dates.get(groupPosition);

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

        // Find sessions with matching date (not the best way to do this)
        int count = 0;
        for (ISession session : sessions) {
            cal.setTime(session.getTime());
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

            if ( dates.contains(cal.getTime()) ) {
                count++;
            }
        }

        return count;

    }

    /**
     * Gets the object at a specified position
     *
     * @param groupPosition the specified group position
     * @return the object at the specified group position
     */
    @Override
    public Object getGroup(int groupPosition) {
        // Get date
        Date date = dates.get(groupPosition);
        List<ISession> children = new ArrayList<>();

        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));

        // Find sessions with matching date (not the best way to do this)
        int count = 0;
        for (ISession session : sessions) {
            cal.setTime(session.getTime());
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);

            if ( dates.contains(cal.getTime()) ) {
                children.add(session);
            } else {
                Log.e(TAG, "getGroup(): no matching date for session.");
            }
        }

        return children;
    }

    /**
     * Gets a child object from a specified position
     *
     * @param groupPosition position of the group within the group list
     * @param childPosition position of the child within the child list
     * @return the child at the specified position
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ISession> children = (List<ISession>) getGroup(groupPosition);
        return children.get(childPosition);
    }

    /**
     * Returns the id for a particular groupPosition
     *
     * @param groupPosition groupPosition in question
     * @return the id of the groupPosition
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Returns the id for a particular childPosition
     *
     * @param groupPosition groupPosition of the childPosition in question
     * @param childPosition childPosition in question
     * @return the id of the childPosition
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * Gets a flag indicator of whether the IDs are stable or not
     *
     * @return true if the IDs are stable; otherwise, false.
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * Gets a view for a group element (Session)
     *
     * @param groupPosition group position within the session list
     * @param isExpanded
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the group element
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Date date = dates.get(groupPosition);
        if (date == null) {
            date = new Date();
        }

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.date_listview, null);
        }

        TextView label = (TextView) convertView.findViewById(R.id.editTextDate);
        label.setText(DateFormat.getDateInstance().format(date));

        return convertView;
    }

    /**
     * Gets a view for a child element (Set)
     *
     * @param groupPosition group position within the exercises list
     * @param childPosition child position within the specific exercise
     * @param isLastChild boolean indicator as to whether this is the last child in the list
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the child element
     */
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Session session = (Session) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.session_listview, null);
        }

        SessionTextView label = (SessionTextView) convertView.findViewById(R.id.labelSessionDescription);
        label.setText("Session " + Integer.toString(childPosition + 1) + ": " + Integer.toString(session.getExercises().size()) + " Exercise(s)");
        label.setText(session.toString());
        label.exercises = session.getExercises();

        return convertView;
    }

    /**
     * Returns a flag indicating whether or not a specified child is selectable
     *
     * @param groupPosition group position within the exercises list
     * @param childPosition child position within the specific exercise
     * @return true if the child is selectable; otherwise, false.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
