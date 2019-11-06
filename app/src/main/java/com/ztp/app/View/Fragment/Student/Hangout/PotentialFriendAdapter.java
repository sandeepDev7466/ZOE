package com.ztp.app.View.Fragment.Student.Hangout;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.VolunteerConnectRequest;
import com.ztp.app.Data.Remote.Model.Response.PotentialFriendsResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.VolunteerConnectViewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PotentialFriendAdapter extends BaseAdapter {
    Context context;
    List<PotentialFriendsResponse.ResDatum> potentialFriendsList;
    VolunteerConnectViewModel volunteerConnectViewModel;
    MyToast myToast;
    SharedPref sharedPref;
    MyProgressDialog myProgressDialog;

    public PotentialFriendAdapter(Context context, List<PotentialFriendsResponse.ResDatum> potentialFriendsList) {
        this.context = context;
        this.potentialFriendsList = potentialFriendsList;
        volunteerConnectViewModel = ViewModelProviders.of((FragmentActivity) context).get(VolunteerConnectViewModel.class);
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
        myProgressDialog = new MyProgressDialog(context);
    }

    @Override
    public int getCount() {
        return potentialFriendsList.size();
    }

    @Override
    public PotentialFriendsResponse.ResDatum getItem(int position) {
        return potentialFriendsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        PotentialFriendsResponse.ResDatum potentialData = getItem(position);

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.potential_friend_layout, null);
            holder.tv_name = view.findViewById(R.id.tv_name);
            holder.tv_email = view.findViewById(R.id.tv_email);
            holder.tv_phone = view.findViewById(R.id.tv_phone);
            holder.image = view.findViewById(R.id.image);
            holder.connect = view.findViewById(R.id.connect);
            view.setTag(holder);
        } else {
            holder = (Holder) view.getTag();
        }

        holder.tv_name.setText(potentialData.getUserFName() + " " + potentialData.getUserLName());
        holder.tv_email.setText(potentialData.getUserEmail());
        holder.tv_phone.setText(potentialData.getUserPhone());

        if (potentialData.getUserProfilePic().isEmpty()) {
            holder.image.setImageResource(R.drawable.user);
        } else{
            Picasso.with(context).load(potentialData.getUserProfilePic()).placeholder(R.drawable.user).error(R.drawable.user).into(holder.image);
        }

        if(potentialData.getMapStatus() != null && potentialData.getMapStatus().equalsIgnoreCase("20")) {
            holder.connect.setColorFilter(context.getResources().getColor(R.color.dark_green));
            holder.connect.setId(position);
            holder.connect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utility.isNetworkAvailable(context)) {
                        myProgressDialog.show(context.getString(R.string.please_wait));
                        VolunteerConnectRequest volunteerConnectRequest = new VolunteerConnectRequest();
                        volunteerConnectRequest.setUserId(sharedPref.getUserId());
                        volunteerConnectRequest.setUserType(sharedPref.getUserType());
                        volunteerConnectRequest.setUserDevice(Utility.getDeviceId(context));
                        volunteerConnectRequest.setMapStatus("20");
                        volunteerConnectRequest.setVolId(potentialFriendsList.get(v.getId()).getUserId());
                        volunteerConnectViewModel.getVolunteerConnectResponse(volunteerConnectRequest).observe((LifecycleOwner) context, volunteerConnectResponse -> {
                            if (volunteerConnectResponse != null) {
                                if (volunteerConnectResponse.getResStatus().equalsIgnoreCase("200")) {
                                    potentialFriendsList.remove(v.getId());
                                    notifyDataSetChanged();
                                    myToast.show(context.getString(R.string.req_send_success), Toast.LENGTH_SHORT, true);

                                    context.startActivity(new Intent(context, SendReceivedRequestActivity.class));

                                } else if (volunteerConnectResponse.getResStatus().equalsIgnoreCase("401")) {
                                    myToast.show(context.getString(R.string.req_failed), Toast.LENGTH_SHORT, false);
                                }
                            } else {
                                myToast.show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                            }
                            myProgressDialog.dismiss();
                        });
                    } else {
                        myToast.show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                    }
                }
            });
        }
        else
        {
            holder.connect.setColorFilter(context.getResources().getColor(R.color.background_3));
            context.startActivity(new Intent(context, SendReceivedRequestActivity.class));
        }
        return view;
    }

    private class Holder {
        CircleImageView image;
        MyTextView tv_name, tv_email, tv_phone;
        ImageView connect;
    }
}
