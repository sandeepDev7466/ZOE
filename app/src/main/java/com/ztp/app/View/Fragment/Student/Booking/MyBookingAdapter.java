package com.ztp.app.View.Fragment.Student.Booking;

import android.app.Dialog;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

import java.util.Date;
import java.util.List;
import java.util.Map;

class MyBookingAdapter extends BaseAdapter {
    private Context context;
    List<VolunteerAllResponse.ResData> dataList;
    MyToast myToast;

    public MyBookingAdapter(Context context, List<VolunteerAllResponse.ResData> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public VolunteerAllResponse.ResData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        VolunteerAllResponse.ResData data = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_booking_list, null);
            holder.day = view.findViewById(R.id.day);
            holder.title = view.findViewById(R.id.title);
            holder.description = view.findViewById(R.id.description);
            holder.time = view.findViewById(R.id.time);
            holder.month = view.findViewById(R.id.month);
            holder.event = view.findViewById(R.id.event);
            view.setTag(holder);

        } else {
            holder = (Holder) view.getTag();
        }

        holder.title.setText(data.getEventHeading());
        holder.description.setText(data.getShiftTask());
        holder.time.setText(data.getShiftStartTime()+" - "+data.getShiftEndTime());


        Date date = Utility.convertStringToDateWithoutTime(data.getShiftDate());

        String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
        String day          = (String) DateFormat.format("dd",   date); // 20
        String monthString  = (String) DateFormat.format("MMM",  date); // Jun
        String monthNumber  = (String) DateFormat.format("MM",   date); // 06
        String year         = (String) DateFormat.format("yyyy", date); // 2013

        holder.month.setText(monthString.toUpperCase());
        holder.day.setText(day);

        view.setOnClickListener(v -> openDialog("Delete booking for",data.getEventHeading(),data.getShiftStartTime()+" - "+data.getShiftEndTime()));

        return view;
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
        });

        dialog.show();

    }

    private class Holder {
        MyTextView day, time,description;
        MyBoldTextView month ,title;
        ImageView event;
    }
}
