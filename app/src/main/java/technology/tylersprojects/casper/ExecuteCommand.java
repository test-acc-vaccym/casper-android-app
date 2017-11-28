package technology.tylersprojects.casper;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Tyler Lambert on 11/27/2017.
 */

public class ExecuteCommand extends IntentService {
    private static final String TAG = "ExecuteCommand";
    private static final String CMD = "Command";

    private Command mCmd;

    public enum Command {
        WIPE,
        NUKE,
        GET_CONTACTS
    }

    public static void launchService(Context context, Command cmd) {
        Log.i(TAG, "launchService");
        if(context == null) {
            return;
        }
        if(cmd == null) {
            Log.e(TAG, "command cannot be null");
            return;
        }
        if(!Shell.SU.available()) {
            Log.e(TAG, "SU access is not available");
            return;
        }
        Intent intent = new Intent(context, technology.tylersprojects.casper.ExecuteCommand.class);
        intent.putExtra(CMD, cmd);
        context.startService(intent);
    }

    public ExecuteCommand() {
        super("Casper Execute Command");
        Log.i(TAG, "default ExecuteCommand constructor");
    }

    public ExecuteCommand(String name) {
        super(name);
        Log.i(TAG, "ExecuteCommand constructor");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand");
        //mCmd = (Command) intent.getSerializableExtra(CMD);
        Bundle bundle = intent.getExtras();
        if(bundle == null) {
            Log.e(TAG, "onStartCommand had an intent without a Bundle");
        }
        if(!bundle.containsKey(CMD)) {
            Log.e(TAG, "onStartCommand had a Bundle without a command");
        }
        mCmd = (Command) bundle.getSerializable(CMD);
        Log.i(TAG, "received command: "+mCmd);
        if(mCmd == null) {
            Log.e(TAG, "mCmd is initially null");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        while(mCmd == null) {
            Log.e(TAG, "mCmd is null");
            return;
        }
        switch (mCmd) {
            case WIPE:
                wipePhone();
                break;
            case NUKE:
                nukePhone();
                break;
            case GET_CONTACTS:
                getContacts();
                break;
            default:
                Log.e(TAG, "command not found");
        }
    }

    /**
     * Overwrites the cache and data partitions to remove user data.
     * @return
     */
    public boolean wipePhone() {
        Log.i(TAG, "inside of wipePhone function");
        DeviceBlockLocator devBlkLoc = DeviceBlockLocator.getInstance();
        if(devBlkLoc.getCachePartitionPath() == null ||
                devBlkLoc.getUserDataPartitionPath() == null) {
            Log.e(TAG, "Cache or UserData partition not found in wipePhone");
            return false;
        }
        Log.i(TAG, String.format("dd if=/dev/urandom of=%s", devBlkLoc.getCachePartitionPath()));
        Log.i(TAG, String.format("dd if=/dev/urandom of=%s", devBlkLoc.getUserDataPartitionPath()));
        // Remove comment in production
//        Shell.SU.run(new String[]{
//           String.format("dd if=/dev/urandom of=%s", devBlkLoc.getCachePartitionPath()),
//           String.format("dd if=/dev/urandom of=%s", devBlkLoc.getUserDataPartitionPath())
//        });
        return true;
    }

    /**
     * Overwrites the data and cache partitions to remove user data and then overwrites the
     * bootloader, system and EFS partitions. This will render the phone useless... be careful.
     * @return
     */
    public boolean nukePhone() {
        ArrayList<String> cmdList = new ArrayList<>();

        Log.i(TAG, "inside of nukePhone function");
        wipePhone();
        DeviceBlockLocator devBlkLoc = DeviceBlockLocator.getInstance();
        if(devBlkLoc.getBootPartitionPath() != null) {
            Log.i(TAG, String.format("dd if=/dev/urandom of=%s", devBlkLoc.getBootPartitionPath()));
            cmdList.add(String.format("dd if=/dev/urandom of=%s", devBlkLoc.getBootPartitionPath()));
        }
        if(devBlkLoc.getEFSPartitionPath() != null) {
            Log.i(TAG, String.format("dd if=/dev/urandom of=%s", devBlkLoc.getEFSPartitionPath()));
            cmdList.add(String.format("dd if=/dev/urandom of=%s", devBlkLoc.getEFSPartitionPath()));
        }
        if(devBlkLoc.getSystemPartitionPath() != null) {
            Log.i(TAG, String.format("dd if=/dev/urandom of=%s", devBlkLoc.getSystemPartitionPath()));
            cmdList.add(String.format("dd if=/dev/urandom of=%s", devBlkLoc.getSystemPartitionPath()));
        }
        // Remove comment in production
        //Shell.SU.run(cmdList);
        return true;
    }

    /**
     * Sends the contact SQLite database to the admin where it is then parsed and presented in
     * a readable format.
     * @return
     */
    public boolean getContacts() {
        Log.i(TAG, "inside getContacts");
        if(!isToolboxAvailable()) {
            Log.e(TAG, "getContacts could not find Toolbox");
            return false;
        }
        Shell.SU.run(new String[]{
                "cd /data/local/tmp",
                "mkdir contacts",
                "cp /data/data/*.providers.contacts/databases/* ./contacts",
                "tar -cf contacts.tar ./contacts/*",
                "chmod 777 contacts.tar",
                "rm -rf ./contacts"
        });
        // TODO: send contacts.tar to remote server
        Log.i(TAG, "getContacts complete");
        return false;
    }

    /**
     * Determines is our shell can access Toolbox.
     * @return
     */
    private boolean isToolboxAvailable() {
        Log.i(TAG, "inside isToolboxAvail");
        try {
            return Shell.SU.run("toolbox").get(0).equals("Toolbox!");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
