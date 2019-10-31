package com.ztp.app.View.Fragment.Volunteer.Event;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class FindCsoAdapter extends BaseAdapter implements View.OnClickListener {
    Context context;
    List<Map<String, Object>> data;
    String[] dataStr;
    View v;
    List<String[]> dataList = new ArrayList<>();
    MyToast myToast;

    public FindCsoAdapter(Context context, List<Map<String, Object>> data) {
        this.context = context;
        this.data = data;
        myToast = new MyToast(context);
        for(int i=0;i<data.size();i++)
        {
            Map<String, Object> map = data.get(i);
            if (map.get("time") != null)
                dataStr = (String[]) map.get("time");
            dataList.add(dataStr);

        }
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
        Holder holder = new Holder();
        Map<String, Object> map = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.find_cso_row, null);
            v = LayoutInflater.from(context).inflate(R.layout.cso_time, null);
            holder.text = view.findViewById(R.id.text);
            holder.status = view.findViewById(R.id.status);
            holder.body = view.findViewById(R.id.body);
            holder.mainLayout=view.findViewById(R.id.mainLayout);

            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.text.setText(String.valueOf(map.get("text")));
        holder.status.setText(String.valueOf(map.get("status")));


        /*for(int x=0;x<dataList.size();x++)
        {
            MyTextView text = v.findViewById(R.id.text);
            ImageView edit = v.findViewById(R.id.edit);
            text.setText(dataList.get(x)[position]);

            if(v.getParent() != null) {
                ((ViewGroup)v.getParent()).removeView(v);
            }
            holder.body.addView(v);
        }*/
        holder.mainLayout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.mainLayout:
             //   v.setBackgroundColor(context.getResources().getColor(R.color.blue_light));
                openDialog("Confirm booking for","Atlanta Mission Food Drive","4PM-8PM 2016-10-12");
                break;
        }
    }

    class Holder {
        LinearLayout body;
        MyBoldTextView text;
        MyTextView status;
        LinearLayout mainLayout;
    }

    private void openDialog(String title_str,String message_str,String time_str) {

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.custom_yes_no_dialog,null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        LinearLayout yes = view.findViewById(R.id.yes);
        LinearLayout no = view.findViewById(R.id.no);

        MyBoldTextView title = view.findViewById(R.id.title);
        MyBoldTextView message = view.findViewById(R.id.message);
        MyTextView time = view.findViewById(R.id.time);

        title.setText(title_str);
        message.setText(message_str);
        time.setText(time_str);

        yes.setOnClickListener(v -> {
            dialog.dismiss();
            myToast.show("Booking added successfully", Toast.LENGTH_SHORT,true);
        });

        no.setOnClickListener(v -> {
            dialog.dismiss();
           
        });

        dialog.show();

    }

}
