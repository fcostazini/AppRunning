package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.webkit.WebView;

public class VerTerminosYCondiciones extends MainNavigationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView terminos = (WebView) findViewById(R.id.content_terminos);
        terminos.loadUrl("file:///android_asset/terminosycondiciones.html");

    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_terminos_y_condiciones);
    }

}
