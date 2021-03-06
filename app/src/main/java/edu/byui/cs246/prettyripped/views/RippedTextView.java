package edu.byui.cs246.prettyripped.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * TextView with an ID member to store IDs
 *
 * @author Brad Bodily
 * @since 2015-11-28
 */
public class RippedTextView extends TextView {

    // PUBLIC MEMBERS
    /**
     * ID associated with this view
     */
    public long rippedID;

    /**
     * Object associated with this view
     */
    public Object rippedObject;

    /**
     * Creates a new instance of SessionTextView
     *
     * @param context context
     * @param attrs attribute set
     * @param defStyleAttr default style attribute
     */
    public RippedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Creates a new instance of SessionTextView
     *
     * @param context context
     * @param attrs attribute set
     */
    public RippedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Creates a new instance of SessionTextView
     *
     * @param context context
     */
    public RippedTextView(Context context) {
        super(context);
    }
}
