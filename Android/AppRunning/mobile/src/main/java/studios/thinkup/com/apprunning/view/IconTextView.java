package studios.thinkup.com.apprunning.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import studios.thinkup.com.apprunning.provider.TypefaceProvider;

public class IconTextView extends TextView {
    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Typeface typeface = TypefaceProvider.getInstance(
                getContext()).getTypeface(TypefaceProvider.ICOMOON);

        setTypeface(typeface);

    }
}
