package com.jds.varadhi.jds.UtillClasses;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.jds.varadhi.jds.Login.LoginActivity;


/**
 * Created by varadhi on 5/10/18.
 */

public class AppRestartExceptionalHandler implements Thread.UncaughtExceptionHandler {

    private Activity activity;

    public AppRestartExceptionalHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        Intent intent = new Intent(activity, LoginActivity.class);
        intent.putExtra("crash",true);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_CLEAR_TASK
                | Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getInstance().getBaseContext(),0,
                                      intent,PendingIntent.FLAG_ONE_SHOT);

        AlarmManager alarmManager = (AlarmManager) MyApplication.getInstance().getBaseContext().getSystemService(Context.ALARM_SERVICE);

        alarmManager.set(AlarmManager.RTC,System.currentTimeMillis()+100,pendingIntent);

        activity.finish();

        System.exit(2);


    }
}
