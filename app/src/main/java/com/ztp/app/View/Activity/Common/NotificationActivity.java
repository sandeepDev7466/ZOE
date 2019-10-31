package com.ztp.app.View.Activity.Common;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.GetAllNotificationRequest;
import com.ztp.app.Data.Remote.Model.Response.GetAllNotificationResponse;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.Viewmodel.GetAllNotificationViewModel;

import java.util.List;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    ImageView back;
    ListView listView;
    MyTextView noData;
    SharedPref sharedPref;
    GetAllNotificationViewModel getAllNotificationViewModel;
    MyToast myToast;
    MyProgressDialog myProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        context = this;
        Utility.makeStatusBarTransparent(context);
        myToast = new MyToast(context);
        myProgressDialog = new MyProgressDialog(context);
        getAllNotificationViewModel = ViewModelProviders.of((FragmentActivity) context).get(GetAllNotificationViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        Utility.makeStatusBarTransparent(context);
        back =findViewById(R.id.back);
        listView = findViewById(R.id.listView);
        noData = findViewById(R.id.noData);
        back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref.setUnreadPushNotif("0");
        getAllNotification();
    }

    private  void getAllNotification()
    {
        if(Utility.isNetworkAvailable(context))
        {
            myProgressDialog.show(getString(R.string.please_wait));
            getAllNotificationViewModel.getAllNotificationResponse(new GetAllNotificationRequest(sharedPref.getUserId())).observe((LifecycleOwner) context, getAllNotificationResponse -> {
                if(getAllNotificationResponse!=null)
                {
                    if(getAllNotificationResponse.getResStatus().equalsIgnoreCase("200"))
                    {
                        listView.setVisibility(View.VISIBLE);
                        noData.setVisibility(View.GONE);

                        NotificationAdapter notificationAdapter = new NotificationAdapter(context,getAllNotificationResponse.getResData());
                        listView.setAdapter(notificationAdapter);
                    }
                    else if(getAllNotificationResponse.getResStatus().equalsIgnoreCase("401"))
                    {
                        listView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                       // myToast.show(getString(R.string.errr_no_notif), Toast.LENGTH_SHORT,false);
                    }
                    else
                    {
                        listView.setVisibility(View.GONE);
                        noData.setVisibility(View.VISIBLE);
                        myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT,false);
                    }
                }
                else
                {
                    listView.setVisibility(View.GONE);
                    noData.setVisibility(View.VISIBLE);
                    myToast.show(getString(R.string.err_server), Toast.LENGTH_SHORT,false);
                }

                myProgressDialog.dismiss();
            });
        }
        else
        {
            listView.setVisibility(View.GONE);
            noData.setVisibility(View.VISIBLE);
            myToast.show(getString(R.string.no_internet_connection), Toast.LENGTH_SHORT,false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    private class NotificationAdapter extends BaseAdapter {
        Context context;
        List<GetAllNotificationResponse.ResData> resData;

        public NotificationAdapter(Context context, List<GetAllNotificationResponse.ResData> resData) {
            this.context = context;
            this.resData = resData;
        }

        @Override
        public int getCount() {
            return resData.size();
        }

        @Override
        public GetAllNotificationResponse.ResData getItem(int position) {
            return resData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Holder holder = new Holder();
            GetAllNotificationResponse.ResData data = getItem(position);
            if(view == null)
            {
                view = LayoutInflater.from(context).inflate(R.layout.notification_layout,null);
                holder.title = view.findViewById(R.id.title);
                holder.description = view.findViewById(R.id.description);
                holder.time = view.findViewById(R.id.time);
                view.setTag(holder);
            }
            else
            {
                holder = (Holder) view.getTag();
            }

            holder.title.setText(data.getNotificationTitle());
            holder.description.setText(data.getNotificationMsg());

            String date = data.getNotifiationAddDate().split("\\s")[0];
            String time = data.getNotifiationAddDate().split("\\s")[1];
            holder.time.setText(date+" , "+Utility.getTimeAmPm(time));

            return view;
        }
    }
    private class Holder
    {
        TextView title,description,time;
    }
}
