package com.ztp.app.View.Activity.Common;

import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ztp.app.Helper.DetectSwipeGestureListener;
import com.ztp.app.R;

public class PhotoViewerActivity extends AppCompatActivity {

    String url, type;
    ImageView imageView;
    ProgressBar progress;
    private GestureDetectorCompat gestureDetectorCompat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView);
        progress = findViewById(R.id.progress);

        DetectSwipeGestureListener gestureListener = new DetectSwipeGestureListener();
        gestureListener.setActivity(this);
        gestureDetectorCompat = new GestureDetectorCompat(this, gestureListener);

        if (getIntent() != null) {
            url = getIntent().getStringExtra("url");
            type = getIntent().getStringExtra("type");
            Picasso.with(this).load(url).error(R.drawable.no_image).into(imageView, new Callback() {
                @Override
                public void onSuccess() {
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onError() {
                    progress.setVisibility(View.GONE);
                }
            });
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetectorCompat.onTouchEvent(event);
        return true;
    }

    public void closeImage(boolean status)
    {
        if(status)
        {
            imageView.setAnimation(AnimationUtils.loadAnimation(this,R.anim.zoom_in));
            finish();
        }
    }
}
