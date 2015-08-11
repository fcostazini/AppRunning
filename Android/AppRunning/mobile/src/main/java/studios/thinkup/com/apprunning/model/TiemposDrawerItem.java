package studios.thinkup.com.apprunning.model;

import android.content.Intent;
import android.os.Bundle;

import studios.thinkup.com.apprunning.MainNavigationActivity;
import studios.thinkup.com.apprunning.TiemposCarrerasActivity;
import studios.thinkup.com.apprunning.TutorialActivity;
import studios.thinkup.com.apprunning.adapter.DrawerItem;

/**
 * Created by Facundo on 11/08/2015.
 */
public class TiemposDrawerItem extends DrawerItem {
    public TiemposDrawerItem(String text, int iconId) {
        super(text, iconId, TiemposCarrerasActivity.class);
    }

    @Override
    public boolean navigate(MainNavigationActivity c) {
        Intent i = new Intent(c, this.getActivity());
        Bundle b = new Bundle();
        Filtro f = new Filtro();
        f.clean();
        f.setIdUsuario(((RunningApplication)c.getApplication()).getUsuario().getId());
        b.putSerializable(Filtro.FILTRO_ID, f);
        i.putExtras(b);
        c.startActivity(i);
        return true;
    }
}
