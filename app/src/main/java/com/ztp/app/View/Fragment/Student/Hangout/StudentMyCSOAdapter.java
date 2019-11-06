package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;
import com.ztp.app.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentMyCSOAdapter extends BaseAdapter {
    Context context;
    List<ApprovedCSOResponse.ApprovedCSO> approvedCSOList;

    public StudentMyCSOAdapter(Context context, List<ApprovedCSOResponse.ApprovedCSO> approvedCSOList) {
        this.context = context;
        this.approvedCSOList = approvedCSOList;
    }

    @Override
    public int getCount() {
        return approvedCSOList.size();
    }

    @Override
    public ApprovedCSOResponse.ApprovedCSO getItem(int position) {
        return approvedCSOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        ApprovedCSOResponse.ApprovedCSO approvedCSO = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_cso_layout, null);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_email = view.findViewById(R.id.tv_email);
            holder.tv_phone = view.findViewById(R.id.tv_phone);
            holder.image = view.findViewById(R.id.image);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.tv_name.setText(approvedCSO.getCsoName());
        holder.tv_email.setText(approvedCSO.getCsoEmail());
        holder.tv_phone.setText(approvedCSO.getCsoPhone());

        if (approvedCSO.getCsoImage().isEmpty()) {
            holder.image.setImageResource(R.drawable.user);
        } else{
            Picasso.with(context).load(approvedCSO.getCsoImage()).placeholder(R.drawable.user).error(R.drawable.user).into(holder.image);
        }

        return view;
    }
    private class Holder
    {
        TextView tv_name,tv_email,tv_phone;
        CircleImageView image;
    }
}
