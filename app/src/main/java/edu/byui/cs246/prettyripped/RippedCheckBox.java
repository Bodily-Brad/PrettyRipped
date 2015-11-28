package edu.byui.cs246.prettyripped;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * CheckBox with an ID member to store IDs
 *
 * @author Brad Bodily
 * @since 2015-11-28
 */
public class RippedCheckBox extends CheckBox {

    // PUBLIC MEMBERS
    /**
     * ID associated with this view
     */
    public long rippedID;

    public RippedCheckBox(Context context) {
        super(context);
    }

    public RippedCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RippedCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
