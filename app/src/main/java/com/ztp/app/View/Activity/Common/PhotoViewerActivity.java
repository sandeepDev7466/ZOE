package com.ztp.app.View.Activity.Common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ztp.app.R;

public class PhotoViewerActivity extends AppCompatActivity {

    String url, type;
    ImageView imageView;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_viewer);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        imageView = findViewById(R.id.imageView);
        progress = findViewById(R.id.progress);

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
}
