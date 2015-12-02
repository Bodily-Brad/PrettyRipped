package edu.byui.cs246.prettyripped;

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
    public Object rippedObject;

    public RippedEditText(Context context) {
        super(context);
    }

    public RippedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RippedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
