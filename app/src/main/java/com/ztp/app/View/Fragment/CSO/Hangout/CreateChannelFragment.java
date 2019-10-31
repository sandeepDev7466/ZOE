package com.ztp.app.View.Fragment.CSO.Hangout;

import android.app.Dialog;
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
import com.sendbird.android.GroupChannelParams;
import com.sendbird.android.SendBirdException;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.CsoHangoutVolunteerRequest;
import com.ztp.app.Helper.MyButton;
import com.ztp.app.Helper.MyEditText;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.SendBird.groupchannel.GroupChannelListFragment;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Volunteer.Event.TabMyBookingFragment;
import com.ztp.app.Viewmodel.CsoHangoutVolunteerViewModel;

import java.util.ArrayList;
import java.util.List;

public class CreateChannelFragment extends Fragment {
    private LinearLayoutManager mLayoutManager;
    Context context;
    private RecyclerView mRecyclerView;
    private SelectableUserListAdapter mListAdapter;
    MyToast myToast;

    MyProgressDialog myProgressDialog;
    MyTextView noData;
    SharedPref sharedPref;
    CsoHangoutVolunteerViewModel csoHangoutVolunteerViewModel;

    public CreateChannelFragment() {
        // Required empty public constructor
    }

    private UsersSelectedListener mListener;

    // To pass selected user IDs to the parent Activity.
    interface UsersSelectedListener {
        void onUserSelected(boolean selected, String userId);
    }

    private String mChannelUrl;

    private FloatingActionButton fab_create_channel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create_channel, container, false);
        fab_create_channel = view.findViewById(R.id.fab_create_channel);
        mRecyclerView = view.findViewById(R.id.recycler_select_user);

        sharedPref = SharedPref.getInstance(context);
        csoHangoutVolunteerViewModel = ViewModelProviders.of(this).get(CsoHangoutVolunteerViewModel.class);
        myProgressDialog = new MyProgressDialog(context);
        myToast = new MyToast(context);
        noData = view.findViewById(R.id.noData);


        myToast = new MyToast(context);
        mListAdapter = new SelectableUserListAdapter(getActivity(), false, true, new ArrayList<>());
        fab_create_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectableUserListAdapter.mSelectedUserIds.size() == 0) {
                    myToast.show(getString(R.string.no_user_selected), Toast.LENGTH_LONG, false);
                } else if (SelectableUserListAdapter.mSelectedUserIds.size() == 1) {
                    createGroupChannel(SelectableUserListAdapter.nameList.get(0), SelectableUserListAdapter.mSelectedUserIds, false);
                } else {

                    Dialog dialog = new Dialog(context);
                    View view = LayoutInflater.from(context).inflate(R.layout.enter_groupname_popup, null);
                    dialog.setContentView(view);
                    dialog.setCancelable(true);
                    dialog.setCanceledOnTouchOutside(true);
                    MyButton cancel = view.findViewById(R.id.cancelBtn);
                    MyButton done = view.findViewById(R.id.doneBtn);
                    MyEditText et_channel_name = view.findViewById(R.id.et_channel_name);
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    done.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!et_channel_name.getText().toString().trim().equalsIgnoreCase("")) {

                                createGroupChannel(et_channel_name.getText().toString().trim(), SelectableUserListAdapter.mSelectedUserIds, false);
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show();
                }
            }
        });
        setUpRecyclerView();
        getVolunteers();
        return view;
    }


    private void getVolunteers() {

        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(getString(R.string.please_wait));
            csoHangoutVolunteerViewModel.getCsoVolunteers(new CsoHangoutVolunteerRequest(sharedPref.getUserId())).observe(this, csoHangoutVolunteerResponse -> {
                if (csoHangoutVolunteerResponse != null) {
                    if (csoHangoutVolunteerResponse.getRes_status().equalsIgnoreCase("200")) {
                        if (csoHangoutVolunteerResponse.getResData() != null && csoHangoutVolunteerResponse.getResData().size() > 0) {
                            noData.setVisibility(View.INVISIBLE);
                            mRecyclerView.setVisibility(View.VISIBLE);
                            mListAdapter = new SelectableUserListAdapter(getActivity(), false, true, csoHangoutVolunteerResponse.getResData());
                            mRecyclerView.setAdapter(mListAdapter);
                        } else {
                            noData.setVisibility(View.VISIBLE);
                            mRecyclerView.setVisibility(View.INVISIBLE);
                        }
                    } else if (csoHangoutVolunteerResponse.getRes_status().equalsIgnoreCase("401")) {
                        noData.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.INVISIBLE);
                        // myToast.show(getString(R.string.err_no_data_found), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    noData.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.INVISIBLE);
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();

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
    }
}
