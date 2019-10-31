package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveEventDetail;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Request.GetEventDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventDetailResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetEventDetailViewModel extends ViewModel {

    private MutableLiveData<GetEventDetailResponse> getEventDetailMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetEventDetailRequest getEventDetailRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private RoomDB roomDB;

    public LiveData<GetEventDetailResponse> getEventDetailResponseLiveData(Context context,GetEventDetailRequest getEventDetailRequest) {
        getEventDetailMutableLiveData = new MutableLiveData<>();
        this.getEventDetailRequest = getEventDetailRequest;
        this.context = context;
        getResponse();
        return getEventDetailMutableLiveData;
    }

    private void getResponse() {
        Call<GetEventDetailResponse> call = apiInterface.getEventDetail(getEventDetailRequest);
        roomDB = RoomDB.getInstance(context);

        call.enqueue(new Callback<GetEventDetailResponse>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<GetEventDetailResponse> call, Response<GetEventDetailResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        getEventDetailMutableLiveData.postValue(response.body());

                        new AsyncTask<Void, Void, GetEventDetailResponse.EventDetail>() {
                            @Override
                            protected void onPostExecute(GetEventDetailResponse.EventDetail eventDetail) {
                                if (eventDetail == null) {
                                    if (response.body().getResData() != null) {
                                        GetEventDetailResponse.EventDetail eventDet = response.body().getResData();
                                        eventDet.setEventId(getEventDetailRequest.getEventId());
                                        new DBSaveEventDetail(context,eventDet).execute();
                                    }
                                }
                            }

                            @Override
                            protected GetEventDetailResponse.EventDetail doInBackground(Void... voids) {
                                return roomDB.getEventDetailResponseDao().getEventDetailFromId(getEventDetailRequest.getEventId());
                            }
                        }.execute();
                    }
                }
                else
                {
                    getEventDetailMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetEventDetailResponse> call, Throwable t) {
                t.printStackTrace();
                getEventDetailMutableLiveData.postValue(null);
            }
        });
    }
}
