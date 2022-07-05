/**
 *
 */
package com.samplecodeapp.widgets;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.samplecodeapp.Utils.Const;

/**
 * Created by Umesh Kumar on Nov 01, 2017
 */

public class TitleBoldTextView extends TextView {

    public TitleBoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitleBoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleBoldTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Const.sfuiTextBold);
        setTypeface(tf);
    }

}
