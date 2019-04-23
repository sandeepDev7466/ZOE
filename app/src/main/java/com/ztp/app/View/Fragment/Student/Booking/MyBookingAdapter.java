package com.ztp.app.View.Fragment.Student.Booking;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

import java.util.List;
import java.util.Map;

class MyBookingAdapter extends BaseAdapter implements View.OnClickListener {
    private Context context;
    private List<Map<String, String>> dataList;
    MyToast myToast;

    public MyBookingAdapter(Context context, List<Map<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Map<String, String> getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        Map<String, String> map = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_booking_list, null);
            holder.day = view.findViewById(R.id.day);
            holder.text = view.findViewById(R.id.text);
            holder.time = view.findViewById(R.id.time);
            holder.month = view.findViewById(R.id.month);
            holder.event = view.findViewById(R.id.event);
            holder.mainLayout = view.findViewById(R.id.mainLayout);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        holder.day.setText(map.get("date"));
        holder.text.setText(map.get("text"));
        holder.time.setText(map.get("time"));
        holder.month.setText(map.get("month"));

        holder.mainLayout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.mainLayout:
             //   v.setBackgroundColor(context.getResources().getColor(R.color.blue_light));
               openDialog("Delete booking for","Atlanta Mission Food Drive","4PM-8PM 2016-10-12");
                break;
        }
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
            myToast.show("Booking deleted successfully", Toast.LENGTH_SHORT,true);
        });

        no.setOnClickListener(v -> {
            dialog.dismiss();
            myToast.show("Cancelled", Toast.LENGTH_SHORT,false);
        });

        dialog.show();

    }

    private class Holder {
        MyTextView day, time;
        MyBoldTextView month ,text;
        ImageView event;
        LinearLayout mainLayout;
    }
}
