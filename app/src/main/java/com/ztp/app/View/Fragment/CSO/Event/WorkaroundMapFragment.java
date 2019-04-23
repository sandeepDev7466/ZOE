package com.ztp.app.View.Fragment.CSO.Event;

import android.content.Context;
import android.os.Bundle;
import android.widget.FrameLayout;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

public class WorkaroundMapFragment extends SupportMapFragment {
    private OnTouchListener mListener;

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstance) {
        View layout = super.onCreateView(layoutInflater, viewGroup, savedInstance);

        TouchableWrapper frameLayout = new TouchableWrapper(getActivity());

        frameLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        ((ViewGroup) layout).addView(frameLayout,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        return layout;
    }

    public void setListener(OnTouchListener listener) {
        mListener = listener;
    }

    public interface OnTouchListener {
        public abstract void onTouch();
    }

    public class TouchableWrapper extends FrameLayout {

        public TouchableWrapper(Context context) {
            super(context);
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    try {
                        mListener.onTouch();
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    catch (Exception e)
                    {
                        System.out.print(e);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    try {
                        mListener.onTouch();
                        this.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                    catch (Exception e)
                    {
                        System.out.print(e);
                    }

                    break;

                case MotionEvent.ACTION_MOVE:
                    try {
                        this.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                    catch (Exception e)
                    {
                        System.out.print(e);
                    }
                    break;
            }
            return super.dispatchTouchEvent(event);
        }
    }


}