package com.ztp.app.View.Fragment.Volunteer.Locker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ztp.app.R;

import java.util.List;
import java.util.Map;

class RatingAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> data;

    public RatingAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.my_rating_layout,null);
        }
        else
        {

        }
        return view;
    }
}
