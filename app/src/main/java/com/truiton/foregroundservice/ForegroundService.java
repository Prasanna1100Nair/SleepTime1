/*
 * Copyright (c) 2016. Truiton (http://www.truiton.com/).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 * Mohit Gupt (https://github.com/mohitgupt)
 *
 */

package com.truiton.foregroundservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.truiton.foregroundservice.BroadcastReceiver.UserPresentBroadcastReceiver;
import com.truiton.foregroundservice.Global.GlobalApplication;

public class ForegroundService extends Service {
    private static final String LOG_TAG = "ForegroundService";
    UserPresentBroadcastReceiver myReceiver;
    Handler handler;
    long  i=0;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals(Constants.ACTION.STARTFOREGROUND_ACTION)) {
            Log.i(LOG_TAG, "Received Start Foreground Intent ");
            Intent notificationIntent = new Intent(this, MainActivity.class);
            notificationIntent.setAction(Constants.ACTION.MAIN_ACTION);
            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                    notificationIntent, 0);


            Bitmap icon = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.ic_moon);
            Notification notification;
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                 notification = new NotificationCompat.Builder(this)
                        .setContentTitle("Sleep time Recorder")
                        .setSmallIcon( R.mipmap.ic_small_moon)
                .setColor(getResources().getColor(R.color.white))
                        .setLargeIcon(
                                Bitmap.createScaledBitmap(icon, 128, 128, false))
                        .setContentIntent(pendingIntent)
                        .setOngoing(true).build();

            } else {
                 notification = new NotificationCompat.Builder(this)
                        .setContentTitle("Sleep time Recorder")
                        .setSmallIcon( R.mipmap.ic_small_moon)
                        .setLargeIcon(
                                Bitmap.createScaledBitmap(icon, 128, 128, false))
                        .setContentIntent(pendingIntent)
                        .setOngoing(true).build();
            }

            startForeground(Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
                    notification);
            startTimer();
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N){
                // Do something for lollipop and above versions
                StartTimer2();
            } 
        } else if (intent.getAction().equals(
                Constants.ACTION.STOPFOREGROUND_ACTION)) {
            try{
                Log.i(LOG_TAG, "Received Stop Foreground Intent");
                stopForeground(true);
                unregisterReceiver(myReceiver);
                stopSelf();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return START_STICKY;
    }

    private void StartTimer2() {
        SharedPreferences prefs = getApplicationContext().getSharedPreferences(GlobalApplication.MyPREFERENCES, MODE_PRIVATE);
        boolean isThreadalive = prefs.getBoolean(GlobalApplication.mThreadAlive, false);
        if (!isThreadalive) {
            handler = new Handler();

            final Runnable r = new Runnable() {
                public void run() {
                    Log.e("TAG1", "I::" + i);

                    SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(GlobalApplication.MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putBoolean(GlobalApplication.mThreadAlive, true);
                    editor.commit();
                    i++;
                    handler.postDelayed(this, 1000);
                }
            };

            handler.postDelayed(r, 1000);
        }
    }

    private void startTimer() {
        Log.e("TAG1","Service started");
        Toast.makeText(getApplicationContext(),"Service started",Toast.LENGTH_SHORT).show();
        IntentFilter filter = new IntentFilter();
        GlobalApplication.isServiceOn=true;
        filter.addAction(Intent.ACTION_USER_PRESENT);
        filter.addAction(Intent.ACTION_USER_UNLOCKED);
        filter.addAction(Intent.ACTION_SCREEN_OFF);

        myReceiver = new UserPresentBroadcastReceiver();
        registerReceiver(myReceiver, filter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"Service Stopped",Toast.LENGTH_SHORT).show();
        Log.i(LOG_TAG, "In onDestroy");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // Used only in case of bound services.
        return null;
    }
}
