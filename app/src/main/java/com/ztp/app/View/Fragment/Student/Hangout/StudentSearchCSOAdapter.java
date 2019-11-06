package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Remote.Model.Response.SearchCSOResponse;
import com.ztp.app.R;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

public class StudentSearchCSOAdapter extends BaseAdapter {

    Context context;
    List<SearchCSOResponse.ResData> searchCSOList;

    public StudentSearchCSOAdapter(Context context, List<SearchCSOResponse.ResData> searchCSOList) {
        this.context = context;
        this.searchCSOList = searchCSOList;
    }

    @Override
    public int getCount() {
        return searchCSOList.size();
    }

    @Override
    public SearchCSOResponse.ResData getItem(int position) {
        return searchCSOList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        SearchCSOResponse.ResData searchCSO = getItem(position);
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

        holder.tv_name.setText(searchCSO.getCsoName());
        holder.tv_email.setText(searchCSO.getCsoEmail());
        holder.tv_phone.setText(searchCSO.getCsoPhone());
        if (searchCSO.getCsoImage().isEmpty()) {
            holder.image.setImageResource(R.drawable.user);
        } else{
            Picasso.with(context).load(searchCSO.getCsoImage()).placeholder(R.drawable.user).error(R.drawable.user).into(holder.image);
        }

        return view;
    }

    private class Holder {
        TextView tv_name, tv_email, tv_phone;
        CircleImageView image;
    }
}
