package edu.byui.cs246.prettyripped.views;

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

    /**
     * Ripped object associated with this view
     */
    public Object rippedObject;

    /**
     * Creates a new instance of RippedCheckBox
     *
     * @param context context
     */
    public RippedCheckBox(Context context) {
        super(context);
    }

    /**
     * Creates a new instance of RippedCheckBox
     *
     * @param context context
     * @param attrs attribute set
     */
    public RippedCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Creates a new instance of RippedCheckBox
     *
     * @param context context
     * @param attrs attribute set
     * @param defStyleAttr default style attribute
     */
    public RippedCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
