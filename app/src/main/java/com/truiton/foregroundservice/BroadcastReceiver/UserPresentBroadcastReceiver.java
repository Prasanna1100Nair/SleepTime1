/*
 * Copyright (c) 2018. Truiton (http://www.truiton.com/).
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

package com.truiton.foregroundservice.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.truiton.foregroundservice.Database.DatabaseHelper;
import com.truiton.foregroundservice.Global.GlobalApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import static android.content.Context.MODE_PRIVATE;

/**
 * Created by prasannakumar.nair on 27-Nov-18.
 */

public class UserPresentBroadcastReceiver extends BroadcastReceiver {
    private DatabaseHelper db;
    public UserPresentBroadcastReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedpreferences = null;
        Log.d("TAG1","Intent got::"+intent.getAction());
       if(intent.getAction().equals(Intent.ACTION_USER_UNLOCKED))
        {
            Log.d("TAG1","Intent got::True");
        }
        if(intent.getAction().equals(Intent.ACTION_USER_PRESENT)){
            if(GlobalApplication.isServiceOn)
            {
                try {
                    Log.e("TAG1","Unlocked");
                    // Date currentTime = Calendar.getInstance().getTime();
                    Calendar c = Calendar.getInstance();
                    String unlockDate=FormatDate(c);
                    Log.e("TAG1","Unlocked Time::"+unlockDate);
                    SharedPreferences.Editor editor = context.getSharedPreferences(GlobalApplication.MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString(GlobalApplication.mUnlockTime, unlockDate+"");
                    editor.commit();
                    SharedPreferences prefs = context.getSharedPreferences(GlobalApplication.MyPREFERENCES, MODE_PRIVATE);
                    String lock = prefs.getString(GlobalApplication.mLockTime, "0");
                    String unlock = prefs.getString(GlobalApplication.mUnlockTime, "0");
                    if(!lock.equals("0"))
                    {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");

                        Date date1 = simpleDateFormat.parse(lock);
                        Date date2 = simpleDateFormat.parse(unlock);
                        // Date date2 = simpleDateFormat.parse("30/11/2018 17:38:11");
                        SimpleDateFormat df3 = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");

                        Log.e("TAG1","lock Time::"+df3.format(date1));
                        Log.e("TAG1","unlock Time::"+df3.format(date2));
                        printDifference(date1,date2,context);
                        //  System.out.println(date1);
                        //  System.out.println(date2);


                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

        }
        else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            if(GlobalApplication.isServiceOn)
            {
                try{
                    Log.e("TAG1","Locked");
                    Calendar c = Calendar.getInstance();
                    String lockDate=FormatDate(c);
                    Log.e("TAG1","Locked Time::"+lockDate);
                    SharedPreferences.Editor editor = context.getSharedPreferences(GlobalApplication.MyPREFERENCES, MODE_PRIVATE).edit();
                    editor.putString(GlobalApplication.mLockTime, lockDate+"");
                    editor.commit();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }

        }
    }
    public String FormatDate(Calendar c) {

        // SimpleDateFormat df3 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        SimpleDateFormat df3 = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
        String formattedDate3 = df3.format(c.getTime());

        return formattedDate3;
    }
    public void printDifference(Date startDate, Date endDate, Context con) {
        //milliseconds
        long days=0;
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;
        if(elapsedDays>0)
        {
            days =elapsedDays*24;
        }
        long sumOfDaysAndHours=days+elapsedHours;
        String newTime=sumOfDaysAndHours+":"+elapsedMinutes+":"+elapsedSeconds;
        String difference=elapsedDays+" days,"+elapsedHours+" hours,"+elapsedMinutes +" minutes,"+elapsedSeconds+" seconds";
        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays, elapsedHours, elapsedMinutes, elapsedSeconds);
        Log.e("TAG1","Hours and mins::"+sumOfDaysAndHours+":"+elapsedMinutes+":"+elapsedSeconds);
        db = new DatabaseHelper(con);

        CreateNote(db,sumOfDaysAndHours,elapsedMinutes,elapsedSeconds,startDate,endDate,difference);
    }

    private void CreateNote(DatabaseHelper db, long sumOfDaysAndHours, long elapsedMinutes, long elapsedSeconds, Date startDate, Date endDate, String difference) {
        SimpleDateFormat df3 = new SimpleDateFormat("dd/M/yyyy");
        SimpleDateFormat df_full = new SimpleDateFormat("dd/M/yyyy HH:mm:ss");
        String mStartDate=df3.format(startDate);
        String mEndDate=df3.format(endDate);
        String mStartDate_full=df_full.format(startDate);
        String mEndDate_full=df_full.format(endDate);
        int hours = (int) sumOfDaysAndHours;
        int mins = (int) elapsedMinutes;
        int seconds = (int) elapsedSeconds;
        //string lock,String unlock,String timeDiff,int hours,int mins,int seconds
        long id = db.insertNote(mStartDate,mEndDate,difference,hours,mins,seconds,mStartDate_full,mEndDate_full);
        Log.e("TAG1","ID::"+id);
    }

}
