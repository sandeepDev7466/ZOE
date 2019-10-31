package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ztp.app.Data.Remote.Model.Response.ApprovedCSOResponse;

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
       /* if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_friend_layout, null);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_email = view.findViewById(R.id.tv_email);
            holder.tv_phone = view.findViewById(R.id.tv_phone);
            holder.image = view.findViewById(R.id.image);
            holder.message = view.findViewById(R.id.connect);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }*/
        return view;
    }
    private class Holder
    {
        TextView name,email,phone;
        CircleImageView image;
    }
}
