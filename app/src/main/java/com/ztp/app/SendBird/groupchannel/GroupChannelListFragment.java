package com.ztp.app.SendBird.groupchannel;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sendbird.android.GroupChannel;
import com.sendbird.android.GroupChannelListQuery;
import com.sendbird.android.SendBird;
import com.sendbird.android.SendBirdException;
import com.sendbird.syncmanager.ChannelCollection;
import com.sendbird.syncmanager.ChannelEventAction;
import com.sendbird.syncmanager.SendBirdSyncManager;
import com.sendbird.syncmanager.handler.ChannelCollectionHandler;
import com.sendbird.syncmanager.handler.CompletionHandler;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Response.DeleteChannelResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.MyApp.MyZTPApplication;
import com.ztp.app.R;
import com.ztp.app.SendBird.Manager.ConnectionManager;
import com.ztp.app.SendBird.utils.TextUtils;
import com.ztp.app.Utils.Constants;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.CSO.Hangout.CreateChannelFragment;
import com.ztp.app.View.Fragment.Student.Hangout.Stu_ConnectFragment;
import com.ztp.app.View.Fragment.Volunteer.Event.TabMyBookingFragment;
import com.ztp.app.View.Fragment.Volunteer.Hangout.ConnectFragment;
import com.ztp.app.View.Fragment.Volunteer.Hangout.Vol_ConnectFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class GroupChannelListFragment extends Fragment {

    public static final String EXTRA_GROUP_CHANNEL_URL = "GROUP_CHANNEL_URL";
    public static final String EXTRA_NAME = "GROUP_CHANNEL_NAME";
    private static final int INTENT_REQUEST_NEW_GROUP_CHANNEL = 302;

    private static final int CHANNEL_LIST_LIMIT = 50;
    private static final String CONNECTION_HANDLER_ID = "CONNECTION_HANDLER_GROUP_CHANNEL_LIST";
    private static final String CHANNEL_HANDLER_ID = "CHANNEL_HANDLER_GROUP_CHANNEL_LIST";

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    public GroupChannelListAdapter mChannelListAdapter;
    private FloatingActionButton fab_connect;
    private SwipeRefreshLayout mSwipeRefresh;
    private ChannelCollection mChannelCollection;
    SharedPref sharedPref;
    Context context;
    MyTextView noData;

    public static GroupChannelListFragment newInstance() {
        GroupChannelListFragment fragment = new GroupChannelListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.d("LIFECYCLE", "GroupChannelListFragment onCreateView()");

        View rootView = inflater.inflate(R.layout.fragment_group_channel_list, container, false);

        setRetainInstance(true);

        sharedPref = SharedPref.getInstance(context);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_group_channel_list);
        fab_connect = (FloatingActionButton) rootView.findViewById(R.id.fab_connect);
        mSwipeRefresh = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_layout_group_channel_list);
        noData = rootView.findViewById(R.id.noData);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefresh.setRefreshing(true);
                refresh();
            }
        });

        fab_connect.setOnClickListener(v -> {

            if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso) || sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_csa))
                Utility.replaceFragment(context, new CreateChannelFragment(), "CreateChannelFragment");
            else if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_volunteer))
                Utility.replaceFragment(context, new Vol_ConnectFragment(), "Vol_ConnectFragment");
            else
                Utility.replaceFragment(context, new Stu_ConnectFragment(), "Stu_ConnectFragment");

        });

        mChannelListAdapter = new GroupChannelListAdapter(getActivity());
        String newChannelUrl = null, name = null;
        if (!Constants.backFromChat) {
            Bundle b = getArguments();
            if (b != null) {
                if (b.get(TabMyBookingFragment.EXTRA_NEW_CHANNEL_URL) != null) {
                    if (b.getString(TabMyBookingFragment.EXTRA_NEW_CHANNEL_URL) != null)
                        newChannelUrl = b.getString(TabMyBookingFragment.EXTRA_NEW_CHANNEL_URL);
                }
                if (b.get(TabMyBookingFragment.EXTRA_Name) != null) {
                    if (b.getString(TabMyBookingFragment.EXTRA_Name) != null)
                        name = b.getString(TabMyBookingFragment.EXTRA_Name);
                }
            }
            if (newChannelUrl != null) {
                enterGroupChannel(newChannelUrl, name);
            }
        }
        setUpRecyclerView();
        setUpChannelListAdapter();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onResume() {
        String userId = sharedPref.getUserId();
        SendBirdSyncManager.setup(getActivity(), userId, new CompletionHandler() {
            @Override
            public void onCompleted(SendBirdException e) {
                if (getActivity() == null) {
                    return;
                }

                ((MyZTPApplication) getActivity().getApplication()).setSyncManagerSetup(true);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (SendBird.getConnectionState() != SendBird.ConnectionState.OPEN) {
                            refresh();
                        }

                        ConnectionManager.addConnectionManagementHandler(CONNECTION_HANDLER_ID, new ConnectionManager.ConnectionManagementHandler() {
                            @Override
                            public void onConnected(boolean reconnect) {
                                refresh();
                            }
                        });
                    }
                });
            }
        });

        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LIFECYCLE", "GroupChannelListFragment onPause()");
        if (SendBird.getConnectionState() != SendBird.ConnectionState.OPEN) {

        }

        ConnectionManager.removeConnectionManagementHandler(CONNECTION_HANDLER_ID);
        if (mChannelCollection != null) {
            mChannelCollection.setCollectionHandler(null);
            mChannelCollection.remove();
        }

        super.onPause();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == INTENT_REQUEST_NEW_GROUP_CHANNEL) {
            if (resultCode == RESULT_OK) {
                // Channel successfully created
                // Enter the newly created channel.
                String newChannelUrl = data.getStringExtra(TabMyBookingFragment.EXTRA_NEW_CHANNEL_URL);
                String name = data.getStringExtra(TabMyBookingFragment.EXTRA_Name);
                if (newChannelUrl == null) {
                    return;
                }
                if (name == null) {
                    name = "";
                }
                enterGroupChannel(newChannelUrl, name);
            } else {
                Log.d("GrChLIST", "resultCode not STATUS_OK");
            }
        }
    }

    // Sets up recycler view
    private void setUpRecyclerView() {

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mChannelListAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // If user scrolls to bottom of the list, loads more channels.
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLayoutManager.findLastVisibleItemPosition() == mChannelListAdapter.getItemCount() - 1) {
                        if (mChannelCollection != null) {
                            mChannelCollection.fetch(new CompletionHandler() {
                                @Override
                                public void onCompleted(SendBirdException e) {
                                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (mSwipeRefresh.isRefreshing()) {
                                                mSwipeRefresh.setRefreshing(false);
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                }
            }
        });

        /*if(mChannelListAdapter.getItemCount()>0) {
            noData.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            mLayoutManager = new LinearLayoutManager(getContext());
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(mChannelListAdapter);
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

            // If user scrolls to bottom of the list, loads more channels.
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (mLayoutManager.findLastVisibleItemPosition() == mChannelListAdapter.getItemCount() - 1) {
                            if (mChannelCollection != null) {
                                mChannelCollection.fetch(new CompletionHandler() {
                                    @Override
                                    public void onCompleted(SendBirdException e) {
                                        if (mSwipeRefresh.isRefreshing()) {
                                            mSwipeRefresh.setRefreshing(false);
                                        }
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
        else
        {
            noData.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
        }*/
    }

    private void setUpChannelListAdapter() {
        mChannelListAdapter.setOnItemClickListener(new GroupChannelListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GroupChannel channel) {
                if (channel.getMemberCount() > 2)
                    Constants.group = true;
                else
                    Constants.group = false;
                enterGroupChannel(channel);
            }
        });

        mChannelListAdapter.setOnItemLongClickListener(new GroupChannelListAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(final GroupChannel channel) {

                if (channel.getCustomType().equalsIgnoreCase(Constants.SENDBIRD_CHANNEL)) {

                    Dialog dialog1 = new Dialog(context);
                    View vw = LayoutInflater.from(context).inflate(R.layout.group_action_dialog, null);
                    dialog1.setContentView(vw);

                    MyHeadingTextView title = vw.findViewById(R.id.title);
                    MyTextView leaveChannel = vw.findViewById(R.id.leaveChannel);
                    MyTextView deleteChannel = vw.findViewById(R.id.deleteChannel);
                    View view = vw.findViewById(R.id.view);
                    if(channel.getMemberCount()>2)
                        title.setText(TextUtils.getGroupChannelTitle(channel));
                    else
                        title.setText(channel.getName());

                    if (sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_volunteer)) {
                        deleteChannel.setVisibility(View.GONE);
                        view.setVisibility(View.GONE);
                    }
                    else if(sharedPref.getUserType().equalsIgnoreCase(Constants.user_type_cso))
                    {
                        leaveChannel.setVisibility(View.GONE);
                        view.setVisibility(View.GONE);
                    }

                    leaveChannel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            leaveChannelDialog(channel);
                        }
                    });

                    deleteChannel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog1.dismiss();
                            deleteChannelDialog(channel, Constants.SENDBIRD_CHANNEL);
                        }
                    });

                    dialog1.show();
                } else {
                    deleteChannelDialog(channel, Constants.SENDBIRD_INDIVIDUAL);
                }
            }
        });
    }

    private void deleteChannelDialog(final GroupChannel channel, String type) {

        Dialog dialog1 = new Dialog(context);
        View vw = LayoutInflater.from(context).inflate(R.layout.delete_group_dialog, null);
        dialog1.setContentView(vw);
        dialog1.setCancelable(false);

        LinearLayout yes = vw.findViewById(R.id.yes);
        LinearLayout no = vw.findViewById(R.id.no);
        MyHeadingTextView title = vw.findViewById(R.id.title);
        MyTextView message = vw.findViewById(R.id.message);

        if (type.equalsIgnoreCase(Constants.SENDBIRD_CHANNEL)) {
            title.setText(getString(R.string.del_channel));
            if(channel.getMemberCount()>2)
                message.setText(getString(R.string.del_channel) + " " + TextUtils.getGroupChannelTitle(channel) + " ?");
            else
                message.setText(getString(R.string.del_channel) + " " + channel.getName() + " ?");
        } else {
            title.setText(getString(R.string.del_conversation));
            message.setText(getString(R.string.del_conversation) + " " + getString(R.string.with) + " " + TextUtils.getGroupChannelTitle(channel) + " ?");
        }

        yes.setOnClickListener(v1 -> {
            dialog1.dismiss();
            if (Utility.isNetworkAvailable(context))
                deleteChannel(channel);
            else
                new MyToast(context).show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        });

        no.setOnClickListener(v12 -> dialog1.dismiss());

        dialog1.show();
    }

    private void leaveChannelDialog(final GroupChannel channel) {

        Dialog dialog1 = new Dialog(context);
        View vw = LayoutInflater.from(context).inflate(R.layout.leave_group_dialog, null);
        dialog1.setContentView(vw);
        dialog1.setCancelable(false);

        LinearLayout yes = vw.findViewById(R.id.yes);
        LinearLayout no = vw.findViewById(R.id.no);
        MyHeadingTextView title = vw.findViewById(R.id.title);
        MyTextView message = vw.findViewById(R.id.message);

        title.setText(getString(R.string.leave_channel));
        message.setText(getString(R.string.leave_channel) + " " + TextUtils.getGroupChannelTitle(channel) + " ?");

        yes.setOnClickListener(v1 -> {
            dialog1.dismiss();
            leaveChannel(channel);
        });

        no.setOnClickListener(v12 -> dialog1.dismiss());

        dialog1.show();
    }

    /**
     * Turns push notifications on or off for a selected channel.
     *
     * @param channel The channel for which push preferences should be changed.
     * @param on      Whether to set push notifications on or off.
     */
    private void setChannelPushPreferences(final GroupChannel channel, final boolean on) {
        // Change push preferences.
        channel.setPushPreference(on, new GroupChannel.GroupChannelSetPushPreferenceHandler() {
            @Override
            public void onResult(SendBirdException e) {
                if (e != null) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT)
                            .show();
                    return;
                }

                String toast = on
                        ? "Push notifications have been turned ON"
                        : "Push notifications have been turned OFF";

                Toast.makeText(getActivity(), toast, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    /**
     * Enters a Group Channel. Upon entering, a GroupChatFragment will be inflated
     * to display messages within the channel.
     *
     * @param channel The Group Channel to enter.
     */
    void enterGroupChannel(GroupChannel channel) {
        final String channelUrl = channel.getUrl();
        enterGroupChannel(channelUrl, channel.getName());

        /*if (channel.getMembers().size() > 2) {
            enterGroupChannel(channelUrl, channel.getName());
        } else {
            List<Member> members = channel.getMembers();
            for (Member nick : members) {
                if (!sharedPref.getEmail().equalsIgnoreCase(nick.getUserId())) {
                    enterGroupChannel(channelUrl, nick.getNickname());
                    break;
                }
            }
        }*/

    }

    /**
     * Enters a Group Channel with a URL.
     *
     * @param channelUrl The URL of the channel to enter.
     */
    void enterGroupChannel(String channelUrl, String name) {
        GroupChatFragment fragment = GroupChatFragment.newInstance(channelUrl, name);
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.body, fragment, "GroupChatFragment");
        fragmentTransaction.addToBackStack("GroupChatFragment");
        fragmentTransaction.commit();
    }

    private void refresh() {
        refreshChannelList(CHANNEL_LIST_LIMIT);
    }

    /**
     * Creates a new query to get the list of the user's Group Channels,
     * then replaces the existing dataset.
     *
     * @param numChannels The number of channels to load.
     */
    private void refreshChannelList(int numChannels) {
        if (mChannelCollection != null) {
            mChannelCollection.remove();
        }

        mChannelListAdapter.clearMap();
        mChannelListAdapter.clearChannelList();
        GroupChannelListQuery query = GroupChannel.createMyGroupChannelListQuery();
        query.setIncludeEmpty(true);
        query.setLimit(numChannels);
        mChannelCollection = new ChannelCollection(query);
        mChannelCollection.setCollectionHandler(mChannelCollectionHandler);
        mChannelCollection.fetch(new CompletionHandler() {
            @Override
            public void onCompleted(SendBirdException e) {
                if (mSwipeRefresh.isRefreshing()) {
                    mSwipeRefresh.setRefreshing(false);
                }
            }
        });
    }

    /**
     * Leaves a Group Channel.
     *
     * @param channel The channel to leave.
     */
    private void leaveChannel(final GroupChannel channel) {

        channel.leave(new GroupChannel.GroupChannelLeaveHandler() {
            @Override
            public void onResult(SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }

                // Re-query message list
                refresh();
            }
        });
    }

    private void deleteChannel(final GroupChannel channel) {

        Call<DeleteChannelResponse> call = Api.getClient().deleteChannel(channel.getUrl());

        call.enqueue(new Callback<DeleteChannelResponse>() {
            @Override
            public void onResponse(Call<DeleteChannelResponse> call, Response<DeleteChannelResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null && response.body().getError() == null) {
                        refresh();
                        if (channel.getCustomType().equalsIgnoreCase(Constants.SENDBIRD_CHANNEL))
                            new MyToast(context).show(getString(R.string.channel_deleted_Success), Toast.LENGTH_SHORT, true);
                        else
                            new MyToast(context).show(getString(R.string.conversation_deleted), Toast.LENGTH_SHORT, true);
                    } else {
                        if (channel.getCustomType().equalsIgnoreCase(Constants.SENDBIRD_CHANNEL))
                            new MyToast(context).show(getString(R.string.channel_deletion_failed), Toast.LENGTH_SHORT, false);
                        else
                            new MyToast(context).show(getString(R.string.conversation_deletion_failed), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    new MyToast(context).show(getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
            }

            @Override
            public void onFailure(Call<DeleteChannelResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });

       /* channel.leave(new GroupChannel.GroupChannelLeaveHandler() {
            @Override
            public void onResult(SendBirdException e) {
                if (e != null) {
                    // Error!
                    return;
                }

                // Re-query message list
                refresh();
            }
        });*/


    }

    ChannelCollectionHandler mChannelCollectionHandler = new ChannelCollectionHandler() {
        @Override
        public void onChannelEvent(final ChannelCollection channelCollection, final List<GroupChannel> list, final ChannelEventAction channelEventAction) {
            Log.d("SyncManager", "onChannelEvent: size = " + list.size() + ", action = " + channelEventAction);
            if (getActivity() == null) {
                return;
            }

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mSwipeRefresh.isRefreshing()) {
                        mSwipeRefresh.setRefreshing(false);
                    }

                    switch (channelEventAction) {
                        case INSERT:
                            mChannelListAdapter.clearMap();
                            mChannelListAdapter.insertChannels(list, channelCollection.getQuery().getOrder());
                            break;

                        case UPDATE:
                            mChannelListAdapter.clearMap();
                            mChannelListAdapter.updateChannels(list);
                            break;

                        case MOVE:
                            mChannelListAdapter.clearMap();
                            mChannelListAdapter.moveChannels(list, channelCollection.getQuery().getOrder());
                            break;

                        case REMOVE:
                            mChannelListAdapter.clearMap();
                            mChannelListAdapter.removeChannels(list);
                            break;

                        case CLEAR:
                            mChannelListAdapter.clearMap();
                            mChannelListAdapter.clearChannelList();
                            break;
                    }
                }
            });
        }
    };
}
