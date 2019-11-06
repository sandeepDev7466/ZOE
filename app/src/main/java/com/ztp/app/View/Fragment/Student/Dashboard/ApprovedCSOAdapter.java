package com.ztp.app.View.Fragment.Student.Dashboard;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Common.EventDescriptionFragment;

import java.util.List;

public class ApprovedCSOAdapter extends RecyclerView.Adapter<ApprovedCSOAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    List<ApprovedCSOResponse.ApprovedCSO> approvedCSOList;

    public ApprovedCSOAdapter(Context context, List<ApprovedCSOResponse.ApprovedCSO> approvedCSOList) {
        this.context = context;
        this.approvedCSOList = approvedCSOList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        ImageView image;
        ProgressBar progress;

        ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            image = itemView.findViewById(R.id.image);
            progress = itemView.findViewById(R.id.progress);
        }
    }

    @NonNull
    @Override
    public ApprovedCSOAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recyclerview_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedCSOAdapter.ViewHolder viewHolder, int i) {
        viewHolder.text.setText(approvedCSOList.get(i).getCsoName());
        Picasso.with(context).load(approvedCSOList.get(i).getCsoImage()).error(R.drawable.no_image).into(viewHolder.image, new Callback() {
            @Override
            public void onSuccess() {
                viewHolder.progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                viewHolder.progress.setVisibility(View.GONE);
            }
        });
        viewHolder.image.setOnClickListener(v -> {
            EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("event_id", approvedCSOList.get(i).getEventId());
            eventDescriptionFragment.setArguments(bundle);
            Utility.replaceFragment(context, eventDescriptionFragment, "EventDescriptionFragment");
        });
    }

    @Override
    public int getItemCount() {
        return approvedCSOList.size();
    }
}
