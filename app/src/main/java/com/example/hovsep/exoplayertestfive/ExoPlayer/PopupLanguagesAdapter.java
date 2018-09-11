package com.example.hovsep.exoplayertestfive.ExoPlayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class PopupLanguagesAdapter extends ArrayAdapter<LanguagesModel> {

    private ArrayList<LanguagesModel> dataSet;
    Context mContext;
    int layoutModel;
    int layoutTextViewId;

    private class ViewHolder {
        TextView title;
    }

    public PopupLanguagesAdapter(ArrayList<LanguagesModel> data, Context context,
                                 int layoutModel, int layoutTextViewId) {
        super(context, layoutModel, data);
        this.dataSet = data;
        this.mContext=context;
        this.layoutModel = layoutModel;
        this.layoutTextViewId = layoutTextViewId;
    }
    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LanguagesModel dataModel = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(layoutModel, parent, false);
            viewHolder.title = (TextView) convertView.findViewById(layoutTextViewId);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        lastPosition = position;
        viewHolder.title.setText(dataModel.getLanguage());
        return convertView;
    }
}