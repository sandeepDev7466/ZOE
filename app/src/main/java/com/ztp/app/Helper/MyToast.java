package com.ztp.app.Helper;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

public class MyToast {
    Toast toast;
    Context context;
    SharedPref sharedPref;

    public MyToast(Context context) {
        this.context = context;
        toast = new Toast(context);
        sharedPref = SharedPref.getInstance(context);
    }

    public void show(String message, int duration, boolean status) {

        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null);
        MyTextView text = view.findViewById(R.id.text);
        LinearLayout toastLayout = view.findViewById(R.id.toastLayout);
        text.setText(message);
        if (status)
            text.setTextColor(context.getResources().getColor(R.color.green));
        else {
            text.setTextColor(context.getResources().getColor(R.color.red));
            Utility.vibrate(context);
        }

        toastLayout.setBackgroundResource(R.drawable.toast_background_black);

        /*if(sharedPref.getTheme())
        {
            toastLayout.setBackgroundResource(R.drawable.toast_background_white);
        }
        else
        {
            toastLayout.setBackgroundResource(R.drawable.toast_background_black);
        }*/
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, 200);
        toast.setDuration(duration);
        toast.setView(view);
        toast.show();
    }
}
