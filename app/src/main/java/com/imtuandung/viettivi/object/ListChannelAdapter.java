package com.imtuandung.viettivi.object;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.imtuandung.viettivi.R;

import java.util.ArrayList;

/**
 * Created by Dung Nguyen on 12/25/15.
 */
public class ListChannelAdapter extends ArrayAdapter<Channel> {
    public ListChannelAdapter(Context context, ArrayList<Channel> listChannel) {
        super(context, 0, listChannel);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Channel tmpChannel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_channel, parent, false);
        }
        // Lookup view for data population
        TextView tvCharacter = (TextView) convertView
                .findViewById(R.id.tvCharacter);
        TextView tvChannelTitle = (TextView) convertView
                .findViewById(R.id.tvChannelTitle);
        TextView tvChannelDesc = (TextView) convertView
                .findViewById(R.id.tvChannelDesc);
        // Populate the data into the template view using the data object
        tvCharacter.setText(tmpChannel.getTitle().substring(0,1));
        tvChannelTitle.setText(tmpChannel.getTitle());
        tvChannelDesc.setText(tmpChannel.getDescription());
        // Return the completed view to render on screen
        return convertView;
    }
}