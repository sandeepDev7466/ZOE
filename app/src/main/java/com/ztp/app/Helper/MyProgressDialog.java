package com.ztp.app.Helper;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;

public class MyProgressDialog {

    Context context;
    Dialog dialog;
    NumberProgressBar numberProgressBar;

    public MyProgressDialog(Context context) {
        this.context = context;
        dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
    }

    public void show(String message) {
        View view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.transparent)));
        ((MyTextView) dialog.findViewById(R.id.message)).setText(message);
        dialog.show();
    }

    public void dismiss() {
        if (dialog.isShowing())
            dialog.dismiss();
    }

    public void setProgress(int progress) {
        numberProgressBar.setVisibility(View.VISIBLE);
        ((NumberProgressBar) numberProgressBar.findViewById(R.id.number_progress_bar)).setProgress(progress);

    }

    public boolean isShowing() {
        if (dialog.isShowing())
            return true;
        else
            return false;
    }

    public NumberProgressBar getNumberProgressBar() {
        return numberProgressBar;
    }
}
