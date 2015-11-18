package edu.byui.cs246.prettyripped.controls;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
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

/**
 * Created by bradb on 11/14/2015.
 */
public class SessionsExpandableListAdapter extends BaseExpandableListAdapter {
    // CONSTANTS & SETTINGS
    private final static String TAG = "SessionsExpandableListAdapter";

    // LOCAL VARIABLES
    private Context context;
    private List<ISession> sessions = new ArrayList<>();
    private List<Date> dates;

    /**
     * Creates a new instance of SessionsExpandableListAdapter
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

    @Override
    public int getGroupCount() {
        return dates.size();
    }

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


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<ISession> children = (List<ISession>) getGroup(groupPosition);
        return children.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

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

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ISession session = (ISession) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.session_listview, null);
        }

        TextView label = (TextView) convertView.findViewById(R.id.labelSessionDescription);
        label.setText("Session " + Integer.toString(childPosition + 1) + ": " + Integer.toString(session.getExercises().size()) + " Exercise(s)");

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}
