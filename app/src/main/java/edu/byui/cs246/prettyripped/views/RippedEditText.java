package edu.byui.cs246.prettyripped.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * EditText with an ID member to store IDs
 *
 * @author Brad Bodily
 * @since 2015-11-28
 */
public class RippedEditText extends EditText {

    // PUBLIC MEMBERS
    /**
     * ID associated with this view
     */
    public long rippedID;

    /**
     * Ripped object associated with this view
     */
    public Object rippedObject;

    /**
     * Creates a new instance of RippedEditText
     *
     * @param context context
     */
    public RippedEditText(Context context) {
        super(context);
    }

    /**
     * Creates a new instance of RippedEditText
     *
     * @param context context
     * @param attrs attribute set
     */
    public RippedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Creates a new instance of RippedEditText
     *
     * @param context context
     * @param attrs attribute set
     * @param defStyleAttr default style attribute
     */
    public RippedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
