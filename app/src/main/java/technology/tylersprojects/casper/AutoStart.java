package technology.tylersprojects.casper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoStart extends BroadcastReceiver {
    private static final String TAG = "AutoStart";

    // Called on boot when BOOT_COMPLETED broadcast is sent.
    @Override
    public void onReceive(Context context, Intent intent) {
        if(context != null) {
            Intent casperIntent = new Intent(context, MainService.class);
            context.startService(casperIntent);
            Log.i(TAG, "Casper has started");
        }
    }
}
