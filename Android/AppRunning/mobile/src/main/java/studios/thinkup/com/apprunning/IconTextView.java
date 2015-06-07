package studios.thinkup.com.apprunning;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class IconTextView extends TextView {
    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Typeface typeface = Typeface.createFromAsset(
                getContext().getAssets(), "fonts/icomoon.ttf");

        setTypeface(typeface);

    }
}
