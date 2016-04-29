package com.majorassets.betterhalf.DataItemController;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.R;

import java.util.ArrayList;

/**
 * Created by justinbowman on 4/28/16.
 */
public class DataItemActivityAdapter extends ArrayAdapter<BaseDataItem> {

    public DataItemActivityAdapter(Context context, int resource, int textViewResourceId, ArrayList objects) {
        super(context, resource, textViewResourceId, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String item = getItem(position).getLabel();
        String item2 = getItem(position).getValue();

        View view = super.getView(position, convertView, parent);
        ((TextView)view.findViewById(R.id.text_label)).setText(item);
        ((TextView)view.findViewById(R.id.text_value)).setText(item2);

        return view;
    }
}