package studios.thinkup.com.apprunning.model;

import android.content.Intent;

import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.MainNavigationActivity;
import studios.thinkup.com.apprunning.adapter.DrawerItem;

/**
 * Created by FaQ on 09/06/2015.
 */
public class LogOutItem extends DrawerItem {
    public LogOutItem(String label, int icon) {
        super(label, icon, MainActivity.class);


    }

    @Override
    public boolean navigate(MainNavigationActivity c) {

        if (this.getActivity() != null) {

            Intent i = new Intent(c, this.getActivity());
            i.putExtra("LOGOUT", true);
            c.startActivity(i);
            return true;
        } else {
            return false;
        }

    }
}
