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

package com.prasannakumar.SleepTime;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.prasannakumar.SleepTime.Adapters.CustomAdapter;
import com.prasannakumar.SleepTime.Classes.Note;
import com.prasannakumar.SleepTime.Database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnClickListener {
    private static String TAG=MainActivity.class.getSimpleName();
    ArrayList<Integer> hours = new ArrayList<>();
    ArrayList<Integer> minutes = new ArrayList<>();
    ArrayList<Integer> SampleMinutes = new ArrayList<>();
    ArrayList<Integer> seconds = new ArrayList<>();
    ArrayList<Integer> SampleSeconds = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
 

    Button  mRecords;
    private Spinner spinner1;
    ListView listView;
    TextView sleep,startButton,stopButton;
    private DatabaseHelper db;
    List<Note> notesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();

    }

    private void Init() {
        startButton = (TextView) findViewById(R.id.tvStart);
        stopButton = (TextView) findViewById(R.id.tvStop);


        spinner1 = (Spinner) findViewById(R.id.spinner1);
        sleep = (TextView) findViewById(R.id.maxsleep);
        mRecords = (Button) findViewById(R.id.Records);
        listView = (ListView) findViewById(R.id.mobile_list);

        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        mRecords.setOnClickListener(this);
        db = new DatabaseHelper(this);
        notesList.addAll(db.getAllNotes());
        date.clear();
        for (int i = 0; i < notesList.size(); i++) {

            if (!date.contains(notesList.get(i).getLock())) {
                Log.e(TAG, "Dates::" + notesList.get(i).getLock());
                date.add(notesList.get(i).getLock());
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, date);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvStart:
                Intent startIntent = new Intent(MainActivity.this, ForegroundService.class);
                startIntent.setAction(Constants.ACTION.STARTFOREGROUND_ACTION);
                startService(startIntent);
                break;
            case R.id.tvStop:
                Intent stopIntent = new Intent(MainActivity.this, ForegroundService.class);
                stopIntent.setAction(Constants.ACTION.STOPFOREGROUND_ACTION);
                startService(stopIntent);
                break;
            case R.id.Records:
                if (date.size() > 0) {


                    ArrayList<Note> notesListDate = new ArrayList<>();
                    try {
                        db = new DatabaseHelper(this);
                        String text = spinner1.getSelectedItem().toString();
                        notesListDate.addAll(db.getAllNotes(text));
                        hours.clear();
                        minutes.clear();
                        seconds.clear();


                        for (int i = 0; i < notesListDate.size(); i++) {
                            Log.i(TAG, "getTimeDiff::" + notesListDate.get(i).getTimeDiff());
                            Log.d(TAG, "getLock::" + notesListDate.get(i).getLock_full());
                            Log.w(TAG, "getUnlock::" + notesListDate.get(i).getUnlock_full());
                            hours.add(notesListDate.get(i).getHours());
                            minutes.add(notesListDate.get(i).getMinutes());
                            seconds.add(notesListDate.get(i).getSeconds());

                        }

                        int maxHour=Collections.max(hours);
                        int hourCount=Collections.frequency(hours, maxHour);


                        if(hourCount>1)//hour
                        {
                            SampleMinutes.clear();
                            SampleSeconds.clear();
                            for (int s = 0; s < hours.size(); s++) {
                                Log.d(TAG, "@maxHour@::"+maxHour+"::@hourCount@::" + hours.get(s));
                                if(maxHour==hours.get(s))
                                {
                                    SampleMinutes.add(minutes.get(s));
                                    SampleSeconds.add(seconds.get(s));
                                }

                            }
                            int maxMin=Collections.max(SampleMinutes);
                            int MinuCount=Collections.frequency(minutes, maxMin);
                            if(MinuCount>1)
                            {
                                SampleMinutes.clear();
                                SampleSeconds.clear();
                                for (int s = 0; s < minutes.size(); s++) {

                                    if(maxHour==hours.get(s)&&maxMin==minutes.get(s))
                                    {
                                        Log.w(TAG,"1Max Sleep time:: "+hours.get(s)+":"+minutes.get(s)+":"+seconds.get(s));

                                        SampleSeconds.add(seconds.get(s));
                                    }
                                }
                                int maxSec=Collections.max(SampleSeconds);
                                int maxSecCount=Collections.frequency(SampleSeconds, maxSec);
                                if(maxSecCount>1)
                                {
                                    SampleSeconds.clear();
                                    for (int s = 0; s < seconds.size(); s++) {

                                        if(maxHour==hours.get(s)&&maxMin==minutes.get(s)&&maxSec==seconds.get(s))
                                        {
                                            Log.w(TAG,"2Max Sleep time:: "+hours.get(s)+":"+minutes.get(s)+":"+seconds.get(s));
                                            sleep.setText("Total Sleep time is:: "+notesListDate.get(s).getTimeDiff());
                                            sleep.setVisibility(View.VISIBLE);

                                        }

                                    }
                                }
                                else
                                {
                                    for (int s = 0; s < seconds.size(); s++) {

                                        if(maxHour==hours.get(s)&&maxMin==minutes.get(s) && maxSec==seconds.get(s))
                                        {
                                            Log.w(TAG,"3Max Sleep time:: "+hours.get(s)+":"+minutes.get(s)+":"+seconds.get(s));

                                           sleep.setText("Total Sleep time is:: "+notesListDate.get(s).getTimeDiff());
                                            sleep.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }
                            }
                            else //minutes 0
                            {
                                int maxSec=Collections.max(SampleSeconds);
                                int maxSecCount=Collections.frequency(SampleSeconds, maxSec);
                                if(maxSecCount>1)
                                {
                                    SampleSeconds.clear();
                                    for (int s = 0; s < seconds.size(); s++) {
                                       // Log.d(TAG,"hours.get(i)::"+hours.get(s)+" maxHour::"+maxHour+" minutes.get(i):: "+minutes.get(s)+" maxMin:: "+maxMin+" seconds.get(i):: "+ seconds.get(s)+" maxSec:: "+maxSec);

                                        if(maxHour==hours.get(s)&&maxMin==minutes.get(s))
                                        {
                                            Log.w(TAG,"2Max Sleep time:: "+hours.get(s)+":"+minutes.get(s)+":"+seconds.get(s));
                                            sleep.setText("Total Sleep time is:: "+notesListDate.get(s).getTimeDiff());
                                            sleep.setVisibility(View.VISIBLE);

                                        }

                                    }
                                }
                                else
                                {
                                    for (int s = 0; s < seconds.size(); s++) {

                                        if(maxHour==hours.get(s)&&maxMin==minutes.get(s))
                                        {
                                            Log.d(TAG,"3Max Sleep time:: "+hours.get(s)+":"+minutes.get(s)+":"+seconds.get(s));

                                            sleep.setText("Total Sleep time is:: "+notesListDate.get(s).getTimeDiff());
                                            sleep.setVisibility(View.VISIBLE);
                                        }

                                    }
                                }

                            }


                        }else{
                            for(int i=0;i<hours.size();i++)
                            {
                                if(hours.get(i)==maxHour)
                                {

                                    Log.d(TAG,"Hour::"+maxHour+"minute::"+minutes.get(i)+"second::"+seconds.get(i));
                                    sleep.setText("Total Sleep time is:: "+notesListDate.get(i).getTimeDiff());
                                    sleep.setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        CustomAdapter mAdapter = new CustomAdapter(notesListDate, this);


                        listView.setAdapter(mAdapter);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    db = new DatabaseHelper(this);
                    notesList.clear();
                    notesList.addAll(db.getAllNotes());
                    for (int i = 0; i < notesList.size(); i++) {

                        if (!date.contains(notesList.get(i).getLock())) {
                            Log.e(TAG, "Dates::" + notesList.get(i).getLock());
                            date.add(notesList.get(i).getLock());
                        }
                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, date);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner1.setAdapter(dataAdapter);
                }
                if (date.size() <= 0) {
                    Toast.makeText(getApplicationContext(),"No Data available",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }

    }
}
