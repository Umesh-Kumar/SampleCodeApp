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

public class TitleMediumTextView extends TextView {

    public TitleMediumTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public TitleMediumTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleMediumTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), Const.sfuiTextMedium);
        setTypeface(tf);
    }

}
