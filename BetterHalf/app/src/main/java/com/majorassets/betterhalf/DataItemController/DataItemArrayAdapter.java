package com.majorassets.betterhalf.DataItemController;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.majorassets.betterhalf.Model.BaseDataItem;
import com.majorassets.betterhalf.Model.BaseLikeableItem;
import com.majorassets.betterhalf.R;

import java.util.List;

/**
 * Created by eric on 4/29/16.
 */
public class DataItemArrayAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<BaseLikeableItem> objects;

    private class ViewHolder {
        TextView key;
        TextView label;
        ImageView star;
    }

    public DataItemArrayAdapter(Context context, List<BaseLikeableItem> objects) {
        inflater = inflater.from(context);
        this.objects = objects;
    }


    @Override
    public int getCount() {
        if(objects != null)
            return objects.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    public String getValue(int i) {
        return objects.get(i).getValue();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item_layout, null);
            holder.key = (TextView) view.findViewById(R.id.key_text2);
            holder.label = (TextView) view.findViewById(R.id.value_text2);
            holder.star = (ImageView) view.findViewById(R.id.start_item);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.key.setText(objects.get(i).getLabel());
        holder.label.setText(objects.get(i).getValue());
        if(!objects.get(i).getValue().equals("Phil Collins")) {
            holder.star.setImageDrawable(view.getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
            holder.star.setColorFilter(view.getResources().getColor(R.color.secondary_text));
        }
        return view;

    }
}
