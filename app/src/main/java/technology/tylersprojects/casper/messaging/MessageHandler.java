package technology.tylersprojects.casper.messaging;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import technology.tylersprojects.casper.ExecuteCommand;

public class MessageHandler extends FirebaseMessagingService {
    private static final String TAG = "MessageHandler";

    public MessageHandler() {

    }

    /**
     * Called when the app is in the foreground. Extracts and parses message then starts the
     * corresponding function. This is used for testing.
     * @param remoteMessage
     */
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        if (remoteMessage.getNotification() != null) {
            String payload = remoteMessage.getNotification().getBody()
                    .replaceAll(" ","")
                    .toLowerCase();
            Log.d(TAG, "Payload Body: "+payload);
            switch(payload) {
                case "getcontacts":
                    ExecuteCommand.launchService(this, ExecuteCommand.Command.GET_CONTACTS);
                    break;
                case "wipe":
                    ExecuteCommand.launchService(this, ExecuteCommand.Command.WIPE);
                    break;
                case "nuke":
                    ExecuteCommand.launchService(this, ExecuteCommand.Command.NUKE);
                    break;
                default:
                    Log.e(TAG, "payload contained unknown command");
            }
        }
    }

    /**
     * Called when the app is in the background. Extracts and parses message then starts the
     * corresponding function. This is used in a production environment.
     * @param intent
     */
    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);
        Log.d(TAG, "in handleIntent");
        Bundle bundle = intent.getExtras();
        String payload = (String) bundle.get("gcm.notification.body");
        Log.d(TAG, "Payload Body: "+payload);
        switch(payload) {
            case "getcontacts":
                ExecuteCommand.launchService(this, ExecuteCommand.Command.GET_CONTACTS);
                break;
            case "wipe":
                ExecuteCommand.launchService(this, ExecuteCommand.Command.WIPE);
                break;
            case "nuke":
                ExecuteCommand.launchService(this, ExecuteCommand.Command.NUKE);
                break;
            default:
                Log.e(TAG, "payload contained unknown command");
        }
        /*
        if (bundle != null) {
            for (String key : bundle.keySet()) {
                Object value = bundle.get(key);
                Log.d(TAG, String.format("%s %s (%s)", key,
                        value.toString(), value.getClass().getName()));
            }
        }
        */
    }
}
