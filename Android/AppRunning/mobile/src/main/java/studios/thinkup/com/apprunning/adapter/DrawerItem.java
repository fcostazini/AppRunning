package studios.thinkup.com.apprunning.adapter;

import android.content.Context;
import android.content.Intent;

/**
 * Created by fcostazini on 27/05/2015.
 * Item del Navigation Drawer
 */
public class DrawerItem {
    private String name;
    private int iconId;
    private Class activity;

    public DrawerItem(String name, int iconId, Class activity) {
        this.name = name;
        this.iconId = iconId;
        this.activity = activity;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public boolean navigate(Context c){
     if(this.activity != null){
         Intent i = new Intent(c,this.getActivity());
         c.startActivity(i);
         return true;
     }else{
         return false;
     }

    }
}