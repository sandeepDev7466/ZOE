package com.ztp.app.SendBird.groupchannel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sendbird.android.Member;
import com.squareup.picasso.Picasso;
import com.ztp.app.R;
import com.ztp.app.SendBird.utils.DateUtils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MembersAdapter extends BaseAdapter {
    Context context;
    List<Member> memberList;

    public MembersAdapter(Context context, List<Member> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public int getCount() {
        return memberList.size();
    }

    @Override
    public Member getItem(int position) {
        return memberList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        Member member = getItem(position);
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.member_row_layout,null);
            holder.name = view.findViewById(R.id.name);
            holder.email = view.findViewById(R.id.email);
            holder.time = view.findViewById(R.id.time);
            holder.image = view.findViewById(R.id.image);
            view.setTag(holder);
        }
        else
        {
            holder = (Holder) view.getTag();
        }

        holder.name.setText(member.getNickname());
        holder.email.setText(member.getUserId());
        holder.time.setText(DateUtils.formatDateTime(member.getLastSeenAt()));
        if(!member.getProfileUrl().isEmpty())
            Picasso.with(context).load(member.getProfileUrl()).placeholder(R.drawable.user).into(holder.image);
        else
            Picasso.with(context).load(R.drawable.user).into(holder.image);
        return view;
    }
    class Holder
    {
        TextView name,email,time;
        CircleImageView image;
    }
}
