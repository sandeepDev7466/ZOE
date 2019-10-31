package com.ztp.app.View.Fragment.Student.Dashboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.R;

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
        TextView name,email;
        ImageView image;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            image = itemView.findViewById(R.id.image);
        }
    }

    @NonNull
    @Override
    public ApprovedCSOAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.approved_cso_row_layout, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApprovedCSOAdapter.ViewHolder viewHolder, int i) {
        viewHolder.name.setText(approvedCSOList.get(i).getCsoName());
        viewHolder.email.setText(approvedCSOList.get(i).getCsoEmail());
        Picasso.with(context).load(approvedCSOList.get(i).getCsoImage()).placeholder(R.drawable.no_image).into(viewHolder.image);

       /* viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("event_id", eventsByAreaList.get(i).getEventId());
                eventDescriptionFragment.setArguments(bundle);
                Utility.replaceFragment(context, eventDescriptionFragment, "EventDescriptionFragment");
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return approvedCSOList.size();
    }
}
