package com.ztp.app.Helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.util.AttributeSet;

public class MyTextInputLayout extends TextInputLayout {

    public MyTextInputLayout(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Amaranth-Regular.otf");
        this.setTypeface(face);
    }

    public MyTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Amaranth-Regular.otf");
        this.setTypeface(face);
    }

    public MyTextInputLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "fonts/Amaranth-Regular.otf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);
    }
}
