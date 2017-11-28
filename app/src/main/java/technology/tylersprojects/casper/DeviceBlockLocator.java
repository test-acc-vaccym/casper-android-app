package technology.tylersprojects.casper;

import android.util.Log;

import java.util.List;

import eu.chainfire.libsuperuser.Shell;

/**
 * Created by Tyler on 11/28/2017.
 */

public class DeviceBlockLocator {
    private static final DeviceBlockLocator ourInstance = new DeviceBlockLocator();
    private static final String TAG = "DeviceBlockLocator";
    private static final String BLK_DEV_BY_NAME = "ls -la /dev/block/platform/*/by-name";
    private static String mBootPartition;
    private static String mCachePartition;
    private static String mEFSPartition;
    private static String mSystemPartition;
    private static String mUserDataPartition;

    public static DeviceBlockLocator getInstance() {
        return ourInstance;
    }

    private DeviceBlockLocator() {
        String[] splitStr;
        List<String> blkDevLocStrs;
        String devName, devPath;
        /*
        The following command returns a list of the blk dev's id.
        Example: lrwxrwxrwx root root 2017-11-27 17:28 BOOT -> /dev/block/mmcblk0p9
         */
        blkDevLocStrs = Shell.SU.run(BLK_DEV_BY_NAME);
        for(int i=0; i<blkDevLocStrs.size(); i++) {
            splitStr = blkDevLocStrs.get(i).split(" +"); // split on whitespace chunks
            if(splitStr.length >= 3) {
                devName = splitStr[splitStr.length - 3];
                devPath = splitStr[splitStr.length - 1];
                switch(devName.toLowerCase()) {
                    case "boot":
                        mBootPartition = devPath;
                        break;
                    case "cache":
                        mCachePartition = devPath;
                        break;
                    case "efs":
                        mEFSPartition = devPath;
                        break;
                    case "system":
                        mSystemPartition = devPath;
                        break;
                    case "data":
                    case "userdata":
                        mUserDataPartition = devPath;
                        break;
                    default:
                        Log.i(TAG, devName+" is not needed");
                }
            }
        }
    }

    public String getBootPartitionPath() {
        return mBootPartition;
    }

    public String getCachePartitionPath() {
        return mCachePartition;
    }

    public String getEFSPartitionPath() {
        return mEFSPartition;
    }

    public String getSystemPartitionPath() {
        return mSystemPartition;
    }

    public String getUserDataPartitionPath() {
        return mUserDataPartition;
    }
}
