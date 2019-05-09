package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

class PotentialFriendAndPotentialCSOAdapter extends BaseAdapter {
    Context context;
    List<Map<String, Object>> dataList;
    MyToast myToast;

    public PotentialFriendAndPotentialCSOAdapter(Context context, List<Map<String, Object>> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Map<String, Object> getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        Map<String, Object> map = getItem(position);
        if(view==null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.potentialfriendpotentialcso_layout,null);
            holder.name = view.findViewById(R.id.name);
            holder.image = view.findViewById(R.id.image);
            holder.add = view.findViewById(R.id.add);
            view.setTag(holder);

        }
        else
        {
            holder = (Holder) view.getTag();
        }
        holder.name.setText(String.valueOf(map.get("name")));
        holder.image.setImageResource(Integer.parseInt(String.valueOf(map.get("userImage"))));
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // myToast.show("Add", Toast.LENGTH_SHORT,true);
            }
        });

        return view;
    }
    private class Holder
    {
        ImageView add;
        CircleImageView image;
        MyTextView name;
    }
}
