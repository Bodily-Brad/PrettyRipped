package edu.byui.cs246.prettyripped.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * ImageView with members to store a ripped object
 *
 * @author Brad Bodily
 * @since 2015-12-12
 */
public class RippedImageView extends ImageView {
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
     * Creates a new instance of RippedImageView
     *
     * @param context context
     */
    public RippedImageView(Context context) {
        super(context);
    }

    /**
     * Creates a new instance of RippedImageView
     *
     * @param context context
     * @param attrs attribute set
     */
    public RippedImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Creates a new instance of RippedImageView
     *
     * @param context context
     * @param attrs attribute set
     * @param defStyleAttr default style attribute
     */
    public RippedImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
