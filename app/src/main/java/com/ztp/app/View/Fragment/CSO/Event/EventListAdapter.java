package com.ztp.app.View.Fragment.CSO.Event;

import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ztp.app.Data.Local.SharedPrefrence.SharedPref;
import com.ztp.app.Data.Remote.Model.Request.DeleteEventRequest;
import com.ztp.app.Data.Remote.Model.Request.DuplicateEventRequest;
import com.ztp.app.Data.Remote.Model.Request.PublishRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Helper.MyBoldTextView;
import com.ztp.app.Helper.MyHeadingTextView;
import com.ztp.app.Helper.MyProgressDialog;
import com.ztp.app.Helper.MyTextView;
import com.ztp.app.Helper.MyToast;
import com.ztp.app.R;
import com.ztp.app.Utils.Utility;
import com.ztp.app.View.Fragment.Common.EventDetailFragment;
import com.ztp.app.Viewmodel.DuplicateEventViewModel;
import com.ztp.app.Viewmodel.EventDeleteViewModel;
import com.ztp.app.Viewmodel.PublishViewModel;

import java.util.Date;
import java.util.List;

public class EventListAdapter extends BaseAdapter {
    private Context context;
    private List<GetEventsResponse.Event> eventDataList;
    private MyProgressDialog myProgressDialog;
    private EventDeleteViewModel eventDeleteViewModel;
    SharedPref sharedPref;
    PublishViewModel publishViewModel;
    private DuplicateEventViewModel duplicateEventViewModel;
    TabMyEventFragment fragment;

    public EventListAdapter(Context context, List<GetEventsResponse.Event> eventDataList, TabMyEventFragment fragment) {
        this.context = context;
        this.eventDataList = eventDataList;
        myProgressDialog = new MyProgressDialog(context);
        eventDeleteViewModel = ViewModelProviders.of((FragmentActivity) context).get(EventDeleteViewModel.class);
        publishViewModel = ViewModelProviders.of((FragmentActivity) context).get(PublishViewModel.class);
        duplicateEventViewModel = ViewModelProviders.of((FragmentActivity) context).get(DuplicateEventViewModel.class);
        sharedPref = SharedPref.getInstance(context);
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return eventDataList.size();
    }

    @Override
    public GetEventsResponse.Event getItem(int position) {
        return eventDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Holder holder = new Holder();
        try {
            GetEventsResponse.Event eventData = getItem(position);
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.event_list_item, null);
                holder.title = view.findViewById(R.id.title);
                holder.description = view.findViewById(R.id.description);
                holder.date = view.findViewById(R.id.date);
                holder.month = view.findViewById(R.id.month);
                holder.day = view.findViewById(R.id.day);
                holder.imv_publish = view.findViewById(R.id.imv_publish);
                holder.time = view.findViewById(R.id.time);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }

                if (eventData.getEventStatus().equalsIgnoreCase("10")) {
                    holder.imv_publish.setImageResource(R.drawable.tick);
                } else if (eventData.getEventStatus().equalsIgnoreCase("20")) {
                    holder.imv_publish.setImageResource(R.drawable.close);
                }

                if(!eventData.getEventStartTime().isEmpty() && !eventData.getEventEndTime().isEmpty())
                    holder.time.setText(context.getString(R.string.event_time)+" : "+Utility.getTimeAmPm(eventData.getEventStartTime()) + " - " + Utility.getTimeAmPm(eventData.getEventEndTime()));
                else
                    holder.time.setText(context.getString(R.string.event_time)+" : "+"N/A");

            holder.title.setText(eventData.getEventHeading());
            holder.description.setText(eventData.getEventDetails());
            Date date = Utility.convertStringToDateWithoutTime(eventData.getEventRegisterStartDate());

            String dayOfTheWeek = (String) DateFormat.format("EE", date); // Thursday
            String day = (String) DateFormat.format("dd", date); // 20
            String monthString = (String) DateFormat.format("MMM", date); // Jun
            String monthNumber = (String) DateFormat.format("MM", date); // 06
            String year = (String) DateFormat.format("yyyy", date); // 2013

            holder.date.setText(day);
            holder.month.setText(monthString);
            holder.day.setText(dayOfTheWeek);


           /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date addDate = sdf.parse(eventData.getEventAddDate());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(addDate.getTime());


            Date d = Utility.convertStringToDateWithoutTime(eventData.getEventAddDate());
            String upToNCharacters = Utility.formatDateFull(d).substring(0, Math.min(Utility.formatDateFull(d).length(), 2));

            holder.date.setText(upToNCharacters);

            holder.month.setText(month[cal.get(Calendar.MONTH)]);
            //holder.day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));
            holder.time.setText(String.valueOf(cal.get(Calendar.HOUR) + ":" + cal.get(Calendar.MINUTE)));*/


            view.setOnClickListener(v -> {

                Dialog dialog = new Dialog(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.event_action_layout, null);
                dialog.setContentView(view1);
                dialog.show();

                LinearLayout edit_event = view1.findViewById(R.id.edit_event);
                LinearLayout delete_event = view1.findViewById(R.id.delete_event);
                LinearLayout add_shift = view1.findViewById(R.id.add_shift);
                LinearLayout view_shift = view1.findViewById(R.id.view_shift);
                LinearLayout view_event = view1.findViewById(R.id.view_event);
                LinearLayout duplicate_event = view1.findViewById(R.id.duplicate_event);
                //ImageView img_close=view1.findViewById(R.id.img_close);


                /*img_close.setOnClickListener(vs -> {
                    dialog.dismiss();

                });*/
                duplicate_event.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                        Dialog dialog1 = new Dialog(context);
                        View vw = LayoutInflater.from(context).inflate(R.layout.duplicate_dialog, null);
                        dialog1.setContentView(vw);
                        dialog1.setCancelable(false);

                        LinearLayout yes = vw.findViewById(R.id.yes);
                        LinearLayout no = vw.findViewById(R.id.no);

                        yes.setOnClickListener(v12 -> {
                            dialog1.dismiss();

                            DuplicateEventRequest duplicateEventRequest = new DuplicateEventRequest();
                            duplicateEventRequest.setEvent_id(eventData.getEventId());
                            duplicateEventRequest.setUser_device(Utility.getDeviceId(context));
                            duplicateEventRequest.setUser_id(sharedPref.getUserId());
                            duplicateEventRequest.setUser_type(sharedPref.getUserType());

                            if (Utility.isNetworkAvailable(context)) {
                                myProgressDialog.show(context.getString(R.string.please_wait));
                                duplicateEventViewModel.getDupEventResponse(duplicateEventRequest).observe((LifecycleOwner) context, duplicateEventResponse -> {

                                    if (duplicateEventResponse != null) {
                                        if (duplicateEventResponse.getResStatus().equalsIgnoreCase("200")) {

                                            ((TabMyEventFragment) fragment).getData();
                                            dialog1.dismiss();
                                            new MyToast(context).show(context.getString(R.string.success_duplicate), Toast.LENGTH_SHORT, true);
                                        } else if (duplicateEventResponse.getResStatus().equalsIgnoreCase("401")) {

                                            new MyToast(context).show(context.getString(R.string.failed), Toast.LENGTH_SHORT, false);

                                        }
                                    } else {
                                        new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                                    }

                                    myProgressDialog.dismiss();
                                });
                            } else {
                                new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                            }

                        });

                        no.setOnClickListener(v12 -> {
                            dialog1.dismiss();
                        });

                        dialog1.show();

                    }
                });
                edit_event.setOnClickListener(vs -> {
                    if (eventData.getEventStatus().equalsIgnoreCase("10")) {
                        new MyToast(context).show(context.getString(R.string.toast_unpublish_first), Toast.LENGTH_SHORT, false);
                    } else {
                        dialog.dismiss();

                        //Date current = Utility.convertStringToDateWithoutTime(Utility.getCurrentDate());

                        TabNewEventFragment tabNewEventFragment = new TabNewEventFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("model", eventData);
                        bundle.putString("action", "update");
                        tabNewEventFragment.setArguments(bundle);
                        Utility.replaceFragment(context, tabNewEventFragment, "TabNewEventFragment");


                    }
                });

                delete_event.setOnClickListener(vs -> {
                    if (eventData.getEventStatus().equalsIgnoreCase("10")) {
                        new MyToast(context).show(context.getString(R.string.toast_unpublish_first), Toast.LENGTH_SHORT, false);
                    } else {
                        dialog.dismiss();

                        Dialog dialog1 = new Dialog(context);
                        View vw = LayoutInflater.from(context).inflate(R.layout.delete_dialog, null);
                        dialog1.setContentView(vw);
                        dialog1.setCancelable(false);

                        LinearLayout yes = vw.findViewById(R.id.yes);
                        LinearLayout no = vw.findViewById(R.id.no);

                        yes.setOnClickListener(v12 -> {
                            dialog1.dismiss();

                            DeleteEventRequest deleteEventRequest = new DeleteEventRequest();
                            deleteEventRequest.setEvent_id(eventData.getEventId());

                            if (Utility.isNetworkAvailable(context)) {
                                myProgressDialog.show(context.getString(R.string.please_wait));
                                eventDeleteViewModel.getDeleteEventResponse(deleteEventRequest).observe((LifecycleOwner) context, deleteEventResponse -> {

                                    if (deleteEventResponse != null) {
                                        if (deleteEventResponse.getResStatus().equalsIgnoreCase("200")) {

                                            new MyToast(context).show(context.getString(R.string.event_deleted_successfully), Toast.LENGTH_SHORT, true);

                                            eventDataList.remove(eventData);

                                            notifyDataSetChanged();

                                        } else if (deleteEventResponse.getResStatus().equalsIgnoreCase("401")) {

                                            new MyToast(context).show(context.getString(R.string.failed), Toast.LENGTH_SHORT, false);

                                        }
                                    } else {
                                        new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                                    }

                                    myProgressDialog.dismiss();
                                });
                            } else {
                                new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
                            }

                        });

                        no.setOnClickListener(v12 -> {
                            dialog1.dismiss();
                        });

                        dialog1.show();
                    }
                });

                add_shift.setOnClickListener(vs -> {
                    if (eventData.getEventStatus().equalsIgnoreCase("10")) {
                        new MyToast(context).show(context.getString(R.string.toast_unpublish_first), Toast.LENGTH_SHORT, false);
                    } else {
                        dialog.dismiss();

                        AddNewShiftFragment addNewShiftFragment = new AddNewShiftFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event_id", eventData.getEventId());
                        bundle.putString("event_start_date", eventData.getEventRegisterStartDate());
                        bundle.putString("event_end_date", eventData.getEventRegisterEndDate());
                        bundle.putString("status", "add");
                        addNewShiftFragment.setArguments(bundle);
                        Utility.replaceFragment(context, addNewShiftFragment, "AddNewShiftFragment");
                    }
                });

                view_shift.setOnClickListener(vs -> {
                    dialog.dismiss();
                    CSOShiftListFragment shiftListFragment = new CSOShiftListFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("event_id", eventData.getEventId());
                    bundle.putString("event_start_date", eventData.getEventRegisterStartDate());
                    bundle.putString("event_end_date", eventData.getEventRegisterEndDate());
                    bundle.putString("event_status", eventData.getEventStatus());
                    shiftListFragment.setArguments(bundle);
                    Utility.replaceFragment(context, shiftListFragment, "ShiftListFragment");
                });
                view_event.setOnClickListener(vs -> {
                    dialog.dismiss();

                  /*  EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("model", eventData);
                    bundle.putString("event_id", eventData.getEventId());
                    if (eventData.getEventStatus().equalsIgnoreCase("10"))
                        bundle.putString("status", "p");
                    else if(eventData.getEventStatus().equalsIgnoreCase("20"))
                        bundle.putString("status", "u");
                    eventDescriptionFragment.setArguments(bundle);
                    Utility.replaceFragment(context, eventDescriptionFragment, "EventDescriptionFragment");*/


                    EventDetailFragment eventDetailFragment = new EventDetailFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("model", eventData);
                    bundle.putString("event_id", eventData.getEventId());
                    if (eventData.getEventStatus().equalsIgnoreCase("10"))
                        bundle.putString("status", "p");
                    else if (eventData.getEventStatus().equalsIgnoreCase("20"))
                        bundle.putString("status", "u");
                    eventDetailFragment.setArguments(bundle);
                    Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");

                });
            });
            holder.imv_publish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    openDialog(eventData.getEventId(), eventData.getEventStatus(), position);

                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }

    public class Holder {
        MyBoldTextView title, date, month, day;
        ImageView imv_publish;
        MyTextView description ,time;
    }

    public void publish(String event_id, String status, int position) {
        PublishRequest publishRequest = new PublishRequest();
        publishRequest.setUser_id(sharedPref.getUserId());
        publishRequest.setUser_type(sharedPref.getUserType());
        publishRequest.setUser_device(Utility.getDeviceId(context));
        publishRequest.setAction_type(status);
        publishRequest.setEvent_id(event_id);
        publishRequest.setCurrentDate(Utility.getCurrentDate_2());
        Log.i("REQUEST", "" + new Gson().toJson(publishRequest));


        if (Utility.isNetworkAvailable(context)) {
            myProgressDialog.show(context.getString(R.string.please_wait));
            publishViewModel.getResponse(publishRequest).observe((FragmentActivity) context, publishResponse -> {
                if (publishResponse != null) {
                    Log.i("RESPONSE", "" + new Gson().toJson(publishResponse));
                    if (publishResponse.getResStatus().equalsIgnoreCase("200")) {
                        if (status.equalsIgnoreCase("p")) {
                            new MyToast(context).show(context.getString(R.string.toast_publish_success), Toast.LENGTH_SHORT, true);
                            refresh(position, "10");
                        } else {
                            new MyToast(context).show(context.getString(R.string.toast_unpublish_success), Toast.LENGTH_SHORT, true);
                            refresh(position, "20");
                        }


                        EventDetailFragment eventDetailFragment = new EventDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event_id", event_id);
                        bundle.putString("status", status);
                        eventDetailFragment.setArguments(bundle);
                        Utility.replaceFragment(context, eventDetailFragment, "EventDetailFragment");

                      /*  EventDescriptionFragment eventDescriptionFragment = new EventDescriptionFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("event_id", event_id);
                        bundle.putString("status", status);
                        eventDescriptionFragment.setArguments(bundle);
                        Utility.replaceFragment(context, eventDescriptionFragment, "EventDescriptionFragment");*/


                    } else if (publishResponse.getResStatus().equalsIgnoreCase("401")) {
                        /*if (status.equalsIgnoreCase("p"))
                            new MyToast(context).show(context.getString(R.string.toast_publish_failed), Toast.LENGTH_SHORT, false);
                        else
                            new MyToast(context).show(context.getString(R.string.toast_unpublish_failed), Toast.LENGTH_SHORT, false);*/

                        new MyToast(context).show(publishResponse.getResMessage(), Toast.LENGTH_SHORT, false);
                    }
                } else {
                    new MyToast(context).show(context.getString(R.string.err_server), Toast.LENGTH_SHORT, false);
                }
                myProgressDialog.dismiss();
            });
        } else {
            new MyToast(context).show(context.getString(R.string.no_internet_connection), Toast.LENGTH_SHORT, false);
        }
    }

    public void refresh(int position, String status) {
        eventDataList.get(position).setEventStatus(status);
        System.out.println(eventDataList);
        notifyDataSetChanged();
    }

    private void openDialog(String event_id, String status, int position) {

        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(R.layout.publish_dialog, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);

        LinearLayout yes = view.findViewById(R.id.yes);
        LinearLayout no = view.findViewById(R.id.no);
        MyTextView tv_desc = view.findViewById(R.id.tv_desc);
        MyHeadingTextView tv_heading = view.findViewById(R.id.tv_heading);

        if (status.equalsIgnoreCase("10")) {
            tv_heading.setText(R.string.confirm_unpublish);
            tv_desc.setText(R.string.are_you_sure_you_want_to_unpublish);
        } else if (status.equalsIgnoreCase("20")) {
            tv_heading.setText(R.string.confirm_publish);
            tv_desc.setText(R.string.are_you_sure_you_want_to_publish);
        }

        yes.setOnClickListener(v -> {
            dialog.dismiss();
            if (status.equalsIgnoreCase("10"))
                publish(event_id, "u", position);
            else if (status.equalsIgnoreCase("20"))
                publish(event_id, "p", position);
        });

        no.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }
}