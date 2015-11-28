package edu.byui.cs246.prettyripped.controls;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import edu.byui.cs246.prettyripped.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.RippedTextView;
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
    private List<Session> sessions = new ArrayList<>();
    private List<String> groups;

    /**
     * Creates a new instance of SessionsExpandableListAdapter
     *
     * @param context Context
     * @param sessions list of sessions to list
     */
    public SessionsExpandableListAdapter(Context context, List<Session> sessions) {
        this.context = context;
        this.sessions = sessions;

        // TODO: This is not treating equal dates as equal, and we get identical dates showing up

        // Just make one huge groupDescription for now


        Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
        groups = new ArrayList<>();
        groups.add("All Sessions");
    }

    /**
     * Gets the number of groupDescription elements
     *
     * @return the number of groupDescription elements
     */
    @Override
    public int getGroupCount() {
        return groups.size();
    }

    /**
     * Gets the number of children elements within a specified groupDescription
     *
     * @param groupPosition the specified groupDescription
     * @return the number of children elements within the specified groupDescription
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        // Just one big groupDescription, so regardless of groupPosition, child count is session size
        return sessions.size();
    }

    /**
     * Gets the object at a specified position
     *
     * @param groupPosition the specified groupDescription position
     * @return the object at the specified groupDescription position
     */
    @Override
    public Object getGroup(int groupPosition) {
        return sessions;
    }

    /**
     * Gets a child object from a specified position
     *
     * @param groupPosition position of the groupDescription within the groupDescription list
     * @param childPosition position of the child within the child list
     * @return the child at the specified position
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Session> children = (List<Session>) getGroup(groupPosition);
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
     * Gets a view for a groupDescription element (Session)
     *
     * @param groupPosition groupDescription position within the session list
     * @param isExpanded
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the groupDescription element
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.date_listview, null);
        }

        TextView label = (TextView) convertView.findViewById(R.id.editTextDate);
        label.setText(groups.get(groupPosition));

        return convertView;
    }

    /**
     * Gets a view for a child element (ExerciseSet)
     *
     * @param groupPosition groupDescription position within the exercises list
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

        RippedTextView label = (RippedTextView) convertView.findViewById(R.id.labelSessionDescription);
        label.setText("Session " + Integer.toString(childPosition + 1) + ": " + Integer.toString(session.getExercises().size()) + " Exercise(s)");
        label.setText(session.toString());

        PrettyRippedData data = PrettyRippedData.getInstance();
        // Set session ID
        label.rippedID = session.getId();

        return convertView;
    }

    /**
     * Returns a flag indicating whether or not a specified child is selectable
     *
     * @param groupPosition groupDescription position within the exercises list
     * @param childPosition child position within the specific exercise
     * @return true if the child is selectable; otherwise, false.
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
