package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ztp.app.Data.Remote.Model.Response.StudentPendingResponse;
import com.ztp.app.R;

import java.util.List;

public class PendingRequestAdapter extends BaseAdapter {
    List<StudentPendingResponse.ResDatum> list;
    Context context;

    public PendingRequestAdapter(Context context, List<StudentPendingResponse.ResDatum> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public StudentPendingResponse.ResDatum getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        StudentPendingResponse.ResDatum data = getItem(position);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.pending_request_row, null);
            holder.name = view.findViewById(R.id.name);
            holder.email = view.findViewById(R.id.email);
            holder.status = view.findViewById(R.id.status);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }
        holder.name.setText(data.getUserFName() + " " + data.getUserLName());
        holder.email.setText(data.getUserEmail());
        holder.status.setText("Pending");
        return view;
    }

    class Holder {
        TextView name, email, status;
    }
}
