package technology.tylersprojects.casper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MainService extends Service {
    private static final String TAG = "MainService";


    public MainService() {
        int i = 0;
        while(i < 5) {
            i++;
            try {
                Thread.sleep(5000);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Casper busy service id still going");
        }
    }

    // Make sure this service is not killed.
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Casper MainService started");
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
