package com.ztp.app.Helper;

import android.content.Context;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dell on 03-10-2017.
 */

public class MyTabLayout extends TabLayout {
    private Typeface mTypeface;

    public MyTabLayout(Context context) {

        super(context);
        init();
    }

    public MyTabLayout(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public MyTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {

        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        mTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/LeagueGothic-Regular.otf");
    }

    @Override
    public void addTab(Tab tab) {

        super.addTab(tab);
        ViewGroup mainView = (ViewGroup) getChildAt(0);
        ViewGroup tabView = (ViewGroup) mainView.getChildAt(tab.getPosition());
        int tabChildCount = tabView.getChildCount();
        for (int i = 0; i < tabChildCount; i++) {
            View tabViewChild = tabView.getChildAt(i);
            if (tabViewChild instanceof TextView) {
                ((TextView) tabViewChild).setTypeface(mTypeface, Typeface.NORMAL);

            }
        }
    }
}
