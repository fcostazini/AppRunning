package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;

/**
 * Created by fcostazini on 06/08/2015.
 *
 */
public class TutorialActivity  extends MainNavigationActivity{

    public static final String PAGINA_TUTORIAL = "pagina";

      @Override
    protected void defineContentView() {
        setContentView(R.layout.tutorial_activity);
    }
}
