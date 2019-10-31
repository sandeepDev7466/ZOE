package com.ztp.app.View.Fragment.Volunteer.Hangout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelParams;
import com.sendbird.android.SendBirdException;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Response.MyCSOResponse_V;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.SendBird.groupchannel.GroupChannelListFragment;
import com.ztp.app.Utils.Constants;
import com.ztp.app.View.Activity.Volunteer.VolunteerDashboardActivity;
import com.ztp.app.View.Fragment.Volunteer.Event.TabMyBookingFragment;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

class MyCSOAdapter extends BaseAdapter {
    Context context;
    List<MyCSOResponse_V.ResData> dataList;
    MyToast myToast;
    public static final String EXTRA_NEW_CHANNEL_URL = "EXTRA_NEW_CHANNEL_URL";
    private List<String> mSelectedIds;
    SharedPref sharedPref;

    public MyCSOAdapter(Context context, List<MyCSOResponse_V.ResData> dataList) {
        this.context = context;
        this.dataList = dataList;
        myToast = new MyToast(context);
        sharedPref = SharedPref.getInstance(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public MyCSOResponse_V.ResData getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();

        try {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.myfriendmycso_layout, null);
                holder.tv_name = view.findViewById(R.id.tv_name);
                holder.tv_email = view.findViewById(R.id.tv_email);
                holder.tv_phone = view.findViewById(R.id.tv_phone);
                holder.image = view.findViewById(R.id.image);
                holder.reply = view.findViewById(R.id.reply);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

            holder.tv_name.setText(dataList.get(position).getUserFName() + " " + dataList.get(position).getUserLName());
            holder.tv_email.setText(dataList.get(position).getUserEmail());
            holder.tv_phone.setText(dataList.get(position).getUserPhone());
            holder.image.setImageResource(R.drawable.user);
            //view.setTag(dataList.get(position));
            view.setId(position);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //MyCSOResponse_V.TargetData dataModel = (MyCSOResponse_V.TargetData) v.getTag();

                    MyCSOResponse_V.ResData dataModel = dataList.get(v.getId());
                    mSelectedIds = new ArrayList<>();
                    mSelectedIds.add(dataModel.getUserEmail());
                    createGroupChannel(dataModel.getUserFName() + " " + dataModel.getUserLName(), mSelectedIds, true);
                    ((VolunteerDashboardActivity) context).setHangoutProps();
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return view;
    }

    private class Holder {
        CircleImageView image;
        MyTextView tv_name, tv_email, tv_phone;
        ImageView reply;
    }

    /**
     * Creates a new Group Channel.
     * <p>
     * Note that if you have not included empty channels in your GroupChannelListQuery,
     * the channel will not be shown in the user's channel list until at least one message
     * has been sent inside.
     *
     * @param userIds  The users to be members of the new channel.
     * @param distinct Whether the channel is unique for the selected members.
     *                 If you attempt to create another Distinct channel with the same members,
     *                 the existing channel instance will be returned.
     */
    private void createGroupChannel(String nick_name, List<String> userIds, boolean distinct) {

        GroupChannelParams params;

        if (userIds.size() != 1) {
            params = new GroupChannelParams()
                    .setPublic(false)
                    .setEphemeral(false)
                    .setDistinct(false)
                    .addUserIds(userIds)
                    //  .setOperatorIds(operatorIds)    // or .setOperators(List<User> operators)
                    .setName(nick_name)
//                 .setCoverImage(FILE)            // or .setCoverUrl(COVER_URL)
                    .setData(sharedPref.getUserId())
                    .setCustomType(Constants.SENDBIRD_CHANNEL);
        } else {
            params = new GroupChannelParams()
                    .setPublic(false)
                    .setEphemeral(false)
                    .setDistinct(false)
                    .addUserIds(userIds)
                    //  .setOperatorIds(operatorIds)    // or .setOperators(List<User> operators)
                    .setName(nick_name)
//                 .setCoverImage(FILE)            // or .setCoverUrl(COVER_URL)
                    .setData(sharedPref.getUserId())
                    .setCustomType(Constants.SENDBIRD_INDIVIDUAL);
        }

        GroupChannel.createChannel(params, new GroupChannel.GroupChannelCreateHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {    // Error.

                } else {
                    if (groupChannel.getMemberCount() > 1) {
                        Constants.backFromChat = false;
                        GroupChannelListFragment fragment = new GroupChannelListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(TabMyBookingFragment.EXTRA_NEW_CHANNEL_URL, groupChannel.getUrl());
                        bundle.putString(TabMyBookingFragment.EXTRA_Name, nick_name);
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.body, fragment, "GroupChannelListFragment");
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        myToast.show("User not available for chatting", Toast.LENGTH_SHORT, false);
                    }
                }
            }
        });

        /*GroupChannel.createChannelWithUserIds(userIds, distinct, new GroupChannel.GroupChannelCreateHandler() {
            @Override
            public void onResult(GroupChannel groupChannel, SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }

                Constants.backFromChat = false;
                GroupChannelListFragment fragment = new GroupChannelListFragment();
                Bundle bundle = new Bundle();
                bundle.putString(EXTRA_NEW_CHANNEL_URL, groupChannel.getUrl());
                bundle.putString(TabMyBookingFragment.EXTRA_Name, nick_name);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.body, fragment, "GroupChannelListFragment");
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });*/
    }
}
