package technology.tylersprojects.casper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class FirebaseInstanceIdService extends Service {
    public FirebaseInstanceIdService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
