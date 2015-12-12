package edu.byui.cs246.prettyripped.controls;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.byui.cs246.prettyripped.models.PrettyRippedData;
import edu.byui.cs246.prettyripped.R;
import edu.byui.cs246.prettyripped.views.RippedTextView;
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
    private List<String> groups;        // We use this for the 'index' to the group strings
    private Map<String, ArrayList<Session>> mappedSessions = new HashMap<>();
    private PrettyRippedData data;

    /**
     * Creates a new instance of SessionsExpandableListAdapter
     *
     * @param context Context
     * @param sessions list of sessions to list
     */
    public SessionsExpandableListAdapter(Context context, List<Session> sessions) {
        this.context = context;
        this.sessions = sessions;

        data = PrettyRippedData.getInstance();

        groups = new ArrayList<>();

        // Iterate through and add groups for each session in the list
        java.text.DateFormat format = new java.text.SimpleDateFormat("MMM yyyy");

        for (Session session : sessions) {
            Date groupDate = getCategoryDate(session.getTime());

            String dateString = format.format(groupDate);
            // If it's not already there, add it
            if (!groups.contains(dateString)) {
                // Add it to the index...
                groups.add(dateString);
                // and to the map
                mappedSessions.put(dateString, new ArrayList<Session>());
            }
            // Add the session
            mappedSessions.get(dateString).add(session);
        }
    }

    /**
     * Gets the number of groupDescription elements
     *
     * @return the number of groupDescription elements
     */
    @Override
    public int getGroupCount() {
        return mappedSessions.size();
    }

    /**
     * Gets the number of children elements within a specified groupDescription
     *
     * @param groupPosition the specified groupDescription
     * @return the number of children elements within the specified groupDescription
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        String key = groups.get(groupPosition);
        return mappedSessions.get(key).size();
    }

    /**
     * Gets the object at a specified position
     *
     * @param groupPosition the specified groupDescription position
     * @return the object at the specified groupDescription position
     */
    @Override
    public Object getGroup(int groupPosition) {
        String key = groups.get(groupPosition);
        return mappedSessions.get(key);
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
     * @param isExpanded flag describing whether the group is expanded or not
     * @param convertView destination view
     * @param parent parent ViewGroup
     * @return a view representing the groupDescription element
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.sessions_list_group, null);
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
            convertView = inflater.inflate(R.layout.sessions_list_child, null);
        }

        RippedTextView label = (RippedTextView) convertView.findViewById(R.id.labelSessionDescription);
        ImageView delButton = (ImageView) convertView.findViewById(R.id.buttonDeleteSession);

        // Set label test
        label.setText(session.toString());

        // Hook up delete button
        delButton.setFocusable(false);
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Context context = v.getContext();
                new AlertDialog.Builder(context)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(R.string.dialog_title_delete_workout)
                        .setMessage(R.string.prompt_delete_workout)
                        .setPositiveButton(R.string.button_confirm_delete, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                data.deleteSession(session);
                            }
                        })
                        .setNegativeButton(R.string.button_cancel_delete, null)
                        .show();
            }
        });

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

    private Date getCategoryDate(Date date) {
        return date;
    }
}
