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

package com.prasannakumar.SleepTime.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.prasannakumar.SleepTime.Classes.Note;

import java.util.ArrayList;
import com.prasannakumar.SleepTime.R;



/**
 * Created by prasannakumar.nair on 27-Nov-18.
 */


public class CustomAdapter extends ArrayAdapter<Note> implements View.OnClickListener{

    private ArrayList<Note> dataSet;
    Context mContext;

    // View lookup cache
    //hello test messages
    private static class ViewHolder {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;

    }

    public CustomAdapter(ArrayList<Note> data, Context context) {
        super(context, R.layout.activity_listview, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        Note dataModel=(Note)object;


    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Note dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.activity_listview, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.sleep);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.wakeup);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.interval);


           // result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            //result=convertView;
        }


        lastPosition = position;
try{
    viewHolder.txtName.setText(dataModel.getLock_full());
    viewHolder.txtType.setText(dataModel.getUnlock_full());
    viewHolder.txtVersion.setText(dataModel.getTimeDiff());
}
catch (Exception e)
{
    e.printStackTrace();
}


        // Return the completed view to render on screen
        return convertView;
    }
}
