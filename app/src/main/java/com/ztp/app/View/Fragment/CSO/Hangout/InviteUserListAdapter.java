package com.ztp.app.View.Fragment.CSO.Hangout;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.ztp.app.Data.Remote.Model.Response.CsoHangoutVolunteerResponse;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Model.InviteUser;
import com.ztp.app.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Populates a RecyclerView with a list of users, each with a checkbox.
 */

public class InviteUserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<InviteUser> mUsers;
    private Context mContext;
    public static List<String> mSelectedUserIds;
    private boolean mIsBlockedList;
    private boolean mShowCheckBox;

    private SelectableUserHolder mSelectableUserHolder;

    // For the adapter to track which users have been selected
    private OnItemCheckedChangeListener mCheckedChangeListener;

    public interface OnItemCheckedChangeListener {
        void OnItemChecked(CsoHangoutVolunteerResponse.ResData user, boolean checked);
    }

    public InviteUserListAdapter(Context context, boolean isBlockedList, boolean showCheckBox, List<InviteUser> dataList) {
        mContext = context;
        setUserList(dataList);
        mSelectedUserIds = new ArrayList<>();
        mIsBlockedList = isBlockedList;
        mShowCheckBox = showCheckBox;
    }

    public void setItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        mCheckedChangeListener = listener;
    }

    public void setUserList(List<InviteUser> users) {
        mUsers = users;
        notifyDataSetChanged();
    }

    public void setShowCheckBox(boolean showCheckBox) {
        mShowCheckBox = showCheckBox;
        if (mSelectableUserHolder != null) {
            mSelectableUserHolder.setShowCheckBox(showCheckBox);
        }
        notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_selectable_user, parent, false);
        mSelectableUserHolder = new SelectableUserHolder(view, mIsBlockedList, mShowCheckBox);
        return mSelectableUserHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SelectableUserHolder) holder).bind(
                mContext,
                mUsers.get(position),
                isSelected(mUsers.get(position)),
                mCheckedChangeListener);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public boolean isSelected(InviteUser user) {
        return mSelectedUserIds.contains(user);
    }

    public void addLast(InviteUser user) {
        mUsers.add(user);
        notifyDataSetChanged();
    }

    private class SelectableUserHolder extends RecyclerView.ViewHolder {
        private MyTextView nameText;
        private CircleImageView profileImage;
        private ImageView blockedImage;
        private CheckBox checkbox;

        private boolean mIsBlockedList;
        private boolean mShowCheckBox;

        public SelectableUserHolder(View itemView, boolean isBlockedList, boolean hideCheckBox) {
            super(itemView);

            this.setIsRecyclable(false);
            mIsBlockedList = isBlockedList;
            mShowCheckBox = hideCheckBox;

            nameText = (MyTextView) itemView.findViewById(R.id.text_selectable_user_list_nickname);
            profileImage = (CircleImageView) itemView.findViewById(R.id.image_selectable_user_list_profile);
            blockedImage = (ImageView) itemView.findViewById(R.id.image_user_list_blocked);
            checkbox = (CheckBox) itemView.findViewById(R.id.checkbox_selectable_user_list);
        }

        public void setShowCheckBox(boolean showCheckBox) {
            mShowCheckBox = showCheckBox;
        }

        private void bind(final Context context, final InviteUser user, boolean isSelected, final OnItemCheckedChangeListener listener) {
            nameText.setText(user.getVol_f_name()+" "+user.getVol_l_name());
//            ImageUtils.displayRoundImageFromUrl(context, user.getProfileUrl(), profileImage);

            if (mIsBlockedList) {
                blockedImage.setVisibility(View.VISIBLE);
            } else {
                blockedImage.setVisibility(View.GONE);
            }

            if (mShowCheckBox) {
                checkbox.setVisibility(View.VISIBLE);
            } else {
                checkbox.setVisibility(View.GONE);
            }

            if (isSelected) {
                checkbox.setChecked(true);
            } else {
                checkbox.setChecked(false);
            }

            if (mShowCheckBox) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mShowCheckBox) {
                            checkbox.setChecked(!checkbox.isChecked());
                        }
                    }
                });
            }

            checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    listener.OnItemChecked(user, isChecked);

                    if (isChecked) {
                        mSelectedUserIds.add(user.getVol_email());
                    } else {
                        mSelectedUserIds.remove(user.getVol_email());
                    }
                }
            });
        }
    }
}
