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
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            i.putExtra("LOGOUT", true);
            c.startActivity(i);
            c.finish();
            return true;
        } else {
            return false;
        }

    }
}
