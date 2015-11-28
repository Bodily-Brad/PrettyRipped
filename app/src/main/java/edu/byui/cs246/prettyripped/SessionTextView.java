package edu.byui.cs246.prettyripped;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.List;

import edu.byui.cs246.prettyripped.models.Exercise;

/**
 * Created by bradb on 11/27/2015.
 */
public class SessionTextView extends TextView {

    // LOCAL VARIABLES
    public long sessionID;

    public SessionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SessionTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SessionTextView(Context context) {
        super(context);
    }
}
