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

package com.truiton.foregroundservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.truiton.foregroundservice.Classes.Note;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by prasannakumar.nair on 27-Nov-18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Note.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public long insertNote(String lock, String unlock, String timeDiff, int hours, int mins, int seconds, String mStartDate_full, String mEndDate_full) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_LOCK, lock);
        values.put(Note.COLUMN_UNLOCK, unlock);
        values.put(Note.COLUMN_TIME_DIFFERENCE, timeDiff);
        values.put(Note.COLUMN_LOCK_FULL, mStartDate_full);
        values.put(Note.COLUMN_UNLOCK_FULL, mEndDate_full);
        values.put(Note.COLUMN_HOURS, hours);
        values.put(Note.COLUMN_MINUTES, mins);
        values.put(Note.COLUMN_SECONDS, seconds);

        // insert row
        long id = db.insert(Note.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<Note> getAllNotes(String date) {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        // String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
        //        Note.COLUMN_TIMESTAMP + " DESC";
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " where   lock =?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{date});

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)), cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK)), cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK)), cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME_DIFFERENCE)), cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK_FULL)), cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK_FULL)), cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HOURS)), cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MINUTES)), cursor.getInt(cursor.getColumnIndex(Note.COLUMN_SECONDS)), cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setLock(cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK)));
                note.setUnlock(cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK)));
                note.setTimeDiff(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME_DIFFERENCE)));
                note.setLock_full(cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK_FULL)));
                note.setUnlock_full(cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK_FULL)));
                note.setHours(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HOURS)));
                note.setMinutes(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MINUTES)));
                note.setSeconds(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_SECONDS)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";
        //String selectQuery = "SELECT  * FROM " + Note.TABLE_NAME +" where   lock =?";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK)),
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK)),
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME_DIFFERENCE)),
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK_FULL)),
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK_FULL)),
                        cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HOURS)),
                        cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MINUTES)),
                        cursor.getInt(cursor.getColumnIndex(Note.COLUMN_SECONDS)),
                        cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setLock(cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK)));
                note.setUnlock(cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK)));
                note.setTimeDiff(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME_DIFFERENCE)));
                note.setLock_full(cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK_FULL)));
                note.setUnlock_full(cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK_FULL)));
                note.setHours(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HOURS)));
                note.setMinutes(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MINUTES)));
                note.setSeconds(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_SECONDS)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

                notes.add(note);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + Note.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public Note getNote() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Note.TABLE_NAME,
                new String[]{Note.COLUMN_ID, Note.COLUMN_LOCK, Note.COLUMN_UNLOCK, Note.COLUMN_TIME_DIFFERENCE, Note.COLUMN_TIMESTAMP},
                Note.COLUMN_ID + "=?",
                //new String[]{String.valueOf(id)}, null, null, null, null);
                new String[]{"MAX(" + Note.COLUMN_SECONDS + ")"}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIME_DIFFERENCE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_LOCK_FULL)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_UNLOCK_FULL)),
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HOURS)),
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_MINUTES)),
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_SECONDS)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));

        // close the db connection
        cursor.close();

        return note;
    }
}
