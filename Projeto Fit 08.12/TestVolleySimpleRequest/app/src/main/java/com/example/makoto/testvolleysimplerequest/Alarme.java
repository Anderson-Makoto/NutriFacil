package com.example.makoto.testvolleysimplerequest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

public class Alarme {
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public void startAlarmManager(Context ctx){
        Intent dialogIntent= new Intent(ctx, Receiver.class);

        alarmManager= (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        pendingIntent= PendingIntent.getBroadcast(ctx, 0, dialogIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000, pendingIntent);
    }
}
