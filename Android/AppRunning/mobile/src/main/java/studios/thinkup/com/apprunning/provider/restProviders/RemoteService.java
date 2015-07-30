package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;

import studios.thinkup.com.apprunning.R;

/**
 * Created by fcostazini on 30/07/2015.
 */
public abstract class RemoteService {
    protected Context context;

    public RemoteService(Context context) {
        this.context =context;
    }

    protected String getBaseURL(){
        return this.context.getString(R.string.host_url) + this.context.getString(R.string.base_service_url) + this.getModule();
    }

    protected abstract String getModule();
}
