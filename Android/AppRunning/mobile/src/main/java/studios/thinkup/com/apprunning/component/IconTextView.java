package studios.thinkup.com.apprunning.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import studios.thinkup.com.apprunning.R;

/**
 * TODO: document your custom view class.
 */
public class IconTextView extends TextView {
    public IconTextView(Context context) {
        super(context);
        init(null, 0, context);
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, context);
    }

    public IconTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle, context);
    }

    private void init(AttributeSet attrs, int defStyle, Context context) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.IconTextView, defStyle, 0);
        Typeface type;
        if (a.hasValue(R.styleable.IconTextView_fontName)) {
            type = Typeface.createFromAsset(context.getAssets(), a.getString(R.styleable.IconTextView_fontName));

        } else {
            type = Typeface.createFromAsset(context.getAssets(), "font/icomoon.ttf");
        }
        this.setTypeface(type);

        a.recycle();


    }
}

