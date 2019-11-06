package com.ztp.app.View.Fragment.CSO.Hangout;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.Member;
import com.sendbird.android.SendBirdException;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoHangoutVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoHangoutVolunteerResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.Model.InviteUser;
import com.ztp.app.R;
import com.ztp.app.SendBird.groupchannel.GroupChannelListFragment;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Volunteer.Event.TabMyBookingFragment;
import com.ztp.app.Viewmodel.CsoHangoutVolunteerViewModel;

import java.util.ArrayList;
import java.util.List;

public class InviteMemberFragment extends Fragment {
    private LinearLayoutManager mLayoutManager;
    Context context;
    private RecyclerView mRecyclerView;
    private InviteUserListAdapter mListAdapter;
    MyToast myToast;
    MyProgressDialog myProgressDialog;
    MyTextView noData;
    SharedPref sharedPref;
    CsoHangoutVolunteerViewModel csoHangoutVolunteerViewModel;
    List<InviteUser> membersList = new ArrayList<InviteUser>();
    List<CsoHangoutVolunteerResponse.ResData> volunteerList = new ArrayList<>();
    GroupChannel channel;

    public InviteMemberFragment() {
        // Required empty public constructor
    }

    private UsersSelectedListener mListener;

    // To pass selected user IDs to the parent Activity.
    interface UsersSelectedListener {
        void onUserSelected(boolean selected, String userId);
    }

    private String mChannelUrl;

    private FloatingActionButton fab_invite;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_channel, container, false);
        fab_invite = view.findViewById(R.id.fab_create_channel);
        mRecyclerView = view.findViewById(R.id.recycler_select_user);

        sharedPref = SharedPref.getInstance(context);
        csoHangoutVolunteerViewModel = ViewModelProviders.of(this).get(CsoHangoutVolunteerViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        noData = view.findViewById(R.id.noData);
        myToast = new MyToast(context);
        mListAdapter = new InviteUserListAdapter(getActivity(), false, true, new ArrayList<>());
        fab_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (InviteUserListAdapter.mSelectedUserIds.size() > 0) {

                    channel.inviteWithUserIds(InviteUserListAdapter.mSelectedUserIds, new GroupChannel.GroupChannelInviteHandler() {
                        @Override
                        public void onResult(SendBirdException e) {
                            if (e != null) {
                                e.printStackTrace();
                                myToast.show("User not found", Toast.LENGTH_LONG, false);// Error.
                            } else {
                                myToast.show(getString(R.string.invitation_sent), Toast.LENGTH_LONG, true);
                                Constants.backFromChat = false;
                                GroupChannelListFragment fragment = new GroupChannelListFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString(TabMyBookingFragment.EXTRA_NEW_CHANNEL_URL, channel.getUrl());
                                bundle.putString(TabMyBookingFragment.EXTRA_Name, channel.getName());
                                fragment.setArguments(bundle);
                                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.body, fragment, "GroupChannelListFragment");
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                            }
                        }
                    });

                } else {
                    myToast.show(getString(R.string.no_user_selected), Toast.LENGTH_LONG, false);
                }
            }
        });
        setUpRecyclerView();
        Bundle b = getArguments();
        if (b != null) {
            if (b.get(GroupChannelListFragment.EXTRA_GROUP_CHANNEL_URL) != null) {
                if (b.getString(GroupChannelListFragment.EXTRA_GROUP_CHANNEL_URL) != null)
                    mChannelUrl = b.getString(GroupChannelListFragment.EXTRA_GROUP_CHANNEL_URL);
            }
        }
        return view;
    }

    private void filterMembers() {
        GroupChannel.getChannel(mChannelUrl, (groupChannel, e) -> {

            if (e != null) {
                e.printStackTrace();
                return;
            }
            channel = groupChannel;
            List<Member> memberList = groupChannel.getMembers();
            List<Member> newList = new ArrayList<>(memberList);
            List<CsoHangoutVolunteerResponse.ResData> newVolList = new ArrayList<>(volunteerList);
            for (CsoHangoutVolunteerResponse.ResData data : volunteerList) {
                for (int i = 0; i < memberList.size(); i++) {
                    Member member = memberList.get(i);

                    if (member.getUserId().equalsIgnoreCase(data.getVol_email())) {
                        newList.remove(member);
                        newVolList.remove(data);
                    }
                }
            }
            if (newVolList.size() == 0) {
                noData.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            } else {
                membersList = new ArrayList<>();
                for (CsoHangoutVolunteerResponse.ResData data : newVolList) {
                    InviteUser user = new InviteUser();
                    user.setVol_email(data.getVol_email());
                    user.setVol_f_name(data.getVol_f_name());
                    user.setVol_l_name(data.getVol_l_name());
                    membersList.add(user);
                }

                noData.setVisibility(View.INVISIBLE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mListAdapter = new InviteUserListAdapter(getActivity(), false, true, membersList);
                mRecyclerView.setAdapter(mListAdapter);
            }
            myProgressDialog.dismiss();
        });
    }

    private void getVolunteers() {

        if (Utility.isNetworkAvailable(context)) {
            volunteerList = new ArrayList<>();
            myProgressDialog.show(getString(R.string.please_wait));
            csoHangoutVolunteerViewModel.getCsoVolunteers(new CsoHangoutVolunteerRequest(sharedPref.getUserId())).observe(this, csoHangoutVolunteerResponse -> {
                if (csoHangoutVolunteerResponse != null) {
                    if (csoHangoutVolunteerResponse.getRes_status().equalsIgnoreCase("200")) {
                        if (csoHangoutVolunteerResponse.getResData() != null && csoHangoutVolunteerResponse.getResData().size() > 0) {
                            volunteerList.addAll(csoHangoutVolunteerResponse.getResData());
                            filterMembers();

                        } else {
                            noData.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.INVISIBLE);
                            myProgressDialog.dismiss();
                        }
                    } else if (csoHangoutVolunteerResponse.getRes_status().equalsIgnoreCase("401")) {
                        noData.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.INVISIBLE);
//                        myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                        myProgressDialog.dismiss();
                    }
                } else {
                    noData.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                    myProgressDialog.dismiss();
                }
            });
        } else {
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    private void setUpRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (mLayoutManager.findLastVisibleItemPosition() == mListAdapter.getItemCount() - 1) {

                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        super.onResume();
        getVolunteers();
    }
}
