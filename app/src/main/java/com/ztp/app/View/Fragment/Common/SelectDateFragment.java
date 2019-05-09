package com.ztp.app.View.Fragment.Common;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import com.ztp.app.Utils.Constants;
import com.ztp.app.View.Fragment.CSO.Event.TabNewEventFragment;

import java.util.Calendar;

/**
 * Created by hash on 30-04-2018.
 */

public class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String mm = "";
        try {
            if (month == 0)
                mm = "Jan";
            else if (month == 1)
                mm = "Feb";
            else if (month == 2)
                mm = "Mar";
            else if (month == 3)
                mm = "Apr";
            else if (month == 4)
                mm = "May";
            else if (month == 5)
                mm = "Jun";
            else if (month == 6)
                mm = "Jul";
            else if (month == 7)
                mm = "Aug";
            else if (month == 8)
                mm = "Sep";
            else if (month == 9)
                mm = "Oct";
            else if (month == 10)
                mm = "Nov";
            else if (month == 11)
                mm = "Dec";
            String date = mm + "-" + dayOfMonth + "-" + year;

            if (Constants.cal == 1 || Constants.cal == 2) {
                if (month + 1 >= 10) {
                    date = (month + 1) + "-" + dayOfMonth + "-" + year;
                } else {
                    date = (month + 1) + "-" + dayOfMonth + "-" + year;
                }
                TabNewEventFragment.setDate(date);
            } else
                TabNewEventFragment.setDate(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar cal = Calendar.getInstance();
        int yy = cal.get(Calendar.YEAR);
        int mm = cal.get(Calendar.MONTH);
        int dd = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, yy, mm, dd);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        return datePickerDialog;
    }
}
