package technology.tylersprojects.casper.messaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Tyler on 11/28/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private final static String TAG = "MyFBInstanceIDService";
    public String registrationToken;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        registrationToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + registrationToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(refreshedToken);
    }
}
