package com.ztp.app.View.Fragment.Student.Hangout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Response.MyFriendsResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyFriendAdapter extends BaseAdapter {
    Context context;
    List<MyFriendsResponse.ResDatum> myFriendsList;
    MyToast myToast;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;

    public MyFriendAdapter(Context context, List<MyFriendsResponse.ResDatum> myFriendsList) {
        this.context = context;
        this.myFriendsList = myFriendsList;
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
    }

    @Override
    public int getCount() {
        return myFriendsList.size();
    }

    @Override
    public MyFriendsResponse.ResDatum getItem(int position) {
        return myFriendsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        MyFriendsResponse.ResDatum myData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.my_friend_layout, null);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_email = view.findViewById(R.id.tv_email);
            holder.tv_phone = view.findViewById(R.id.tv_phone);
            holder.image = view.findViewById(R.id.image);
           // holder.message = view.findViewById(R.id.connect);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.tv_name.setText(myData.getUserFName() + " " + myData.getUserLName());
        holder.tv_email.setText(myData.getUserEmail());
        holder.tv_phone.setText(myData.getUserPhone());

        if (myData.getUserProfilePic().isEmpty()) {
            holder.image.setImageResource(R.drawable.user);
        } else{
            Picasso.with(context).load(myData.getUserProfilePic()).placeholder(R.drawable.user).error(R.drawable.user).into(holder.image);
        }
        view.setId(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

    private class Holder {
        CircleImageView image;
        MyTextView tv_name, tv_email, tv_phone;
        ImageView message;
    }
}
