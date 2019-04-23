package com.ztp.app.View.Fragment.CSO.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ztp.app.R;


import java.util.ArrayList;

public class MessageListAdapter extends ArrayAdapter<MessageModel> implements View.OnClickListener{

    private ArrayList<MessageModel> dataSet;
    Context mContext;
    private boolean mNotifyOnChange = true;


    // View lookup cache
    private static class ViewHolder {
        TextView textView_name;
        TextView textView_descrip;
        ImageView image;


    }

    public MessageListAdapter(ArrayList<MessageModel> data, Context context) {
        super(context, R.layout.message_list_item, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        MessageModel dataModel=(MessageModel)object;

//        switch (v.getId())
//        {
//            case R.id.item_info:
//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();
//                break;
//        }
    }

    @Override
    public int getCount() {
        return dataSet .size();
    }

    @Override
    public MessageModel getItem(int position) {
        return dataSet .get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageModel dataModel = getItem(position);
        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.message_list_item, parent, false);
            viewHolder.textView_name = (TextView) convertView.findViewById(R.id.textView_name);
            viewHolder.textView_descrip= (TextView) convertView.findViewById(R.id.textView_descrip);
            viewHolder.image= (ImageView) convertView.findViewById(R.id.img);


            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }


        viewHolder.textView_name.setText(dataModel.getName());
        viewHolder.textView_descrip.setText(dataModel.getDescrip());


        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        mNotifyOnChange = true;
    }

}