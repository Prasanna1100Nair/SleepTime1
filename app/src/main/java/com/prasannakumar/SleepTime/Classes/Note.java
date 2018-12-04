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

package com.prasannakumar.SleepTime.Classes;

/**
 * Created by prasannakumar.nair on 27-Nov-18.
 */

public class Note {
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOCK = "lock";
    public static final String COLUMN_LOCK_FULL = "lock_full";
    public static final String COLUMN_UNLOCK = "unlock";
    public static final String COLUMN_UNLOCK_FULL = "unlock_full";
    public static final String COLUMN_HOURS = "hours";
    public static final String COLUMN_MINUTES = "mins";
    public static final String COLUMN_SECONDS = "seconds";
    public static final String COLUMN_TIME_DIFFERENCE = "time";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    private int id;
    private String timeDiff;
    private String lock;
    private String lock_full;
    private String unlock;

    public String getLock_full() {
        return lock_full;
    }

    public void setLock_full(String lock_full) {
        this.lock_full = lock_full;
    }

    public String getUnlock_full() {
        return unlock_full;
    }

    public void setUnlock_full(String unlock_full) {
        this.unlock_full = unlock_full;
    }

    private String unlock_full;
    private int hours;
    private int minutes;



    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    private int seconds;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(String timeDiff) {
        this.timeDiff = timeDiff;
    }

    public String getLock() {
        return lock;
    }

    public void setLock(String lock) {
        this.lock = lock;
    }

    public String getUnlock() {
        return unlock;
    }

    public void setUnlock(String unlock) {
        this.unlock = unlock;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String timestamp;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_LOCK + " TEXT," + COLUMN_UNLOCK + " TEXT," + COLUMN_TIME_DIFFERENCE + " TEXT," +
                    COLUMN_LOCK_FULL + " TEXT," +
                    COLUMN_UNLOCK_FULL + " TEXT," +
                    COLUMN_HOURS + " INTEGER," +
                    COLUMN_MINUTES + " INTEGER," +
                    COLUMN_SECONDS + " INTEGER," + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";



    public Note(int id, String lock, String unlock, String timeDiffer,String lock_full,String unlock_full, int hours, int minutes, int seconds, String timestamp) {
        this.id = id;
        this.lock = lock;
        this.unlock = unlock;
        this.timeDiff = timeDiffer;
        this.timestamp = timestamp;
        this.hours=hours;
        this.minutes=minutes;
        this.seconds=seconds;
        this.lock_full=lock_full;
        this.unlock_full=unlock_full;
    }
}
