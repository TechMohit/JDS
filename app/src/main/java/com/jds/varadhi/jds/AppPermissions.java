package com.jds.varadhi.jds;

/**
 * Created by Abhishek on 6/7/2018.
 */

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import java.util.ArrayList;
import java.util.List;

public class AppPermissions {
    private Activity mActivity;

    public AppPermissions(Activity activity) {
        mActivity = activity;
    }


    public boolean hasPermission(String[] permissionsList) {
        for (String permission : permissionsList) {
            if (ActivityCompat.checkSelfPermission(mActivity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public void requestPermission(Activity activity, String[] permissionsList, int requestCode) {
        List<String> permissionNeeded = new ArrayList<>();
        for (String permission : permissionsList) {
            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionNeeded.add(permission);
            }
        }
        if (permissionNeeded.size() > 0) {
            ActivityCompat.requestPermissions(activity,
                    permissionNeeded.toArray(new String[permissionNeeded.size()]), requestCode);
        }
    }
}
