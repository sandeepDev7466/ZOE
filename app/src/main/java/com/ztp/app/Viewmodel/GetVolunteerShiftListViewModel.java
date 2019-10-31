package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Async.Save.DBVolunteerSaveShiftList;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Request.GetCSOShiftRequest;
import com.ztp.app.Data.Remote.Model.Request.GetSearchShiftListRequest;
import com.ztp.app.Data.Remote.Model.Request.GetShiftListRequest;
import com.ztp.app.Data.Remote.Model.Response.GetCSOShiftResponse;
import com.ztp.app.Data.Remote.Model.Response.GetVolunteerShiftListResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by htl-dev on 17-04-2019.
 */

@SuppressLint("StaticFieldLeak")
public class GetVolunteerShiftListViewModel extends ViewModel {

    private MutableLiveData<GetVolunteerShiftListResponse> getShiftListResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetSearchShiftListRequest getSearchShiftRequest;
    private Context context;
    List<GetVolunteerShiftListResponse.ShiftData> shiftData = new ArrayList<>();


    public LiveData<GetVolunteerShiftListResponse> getSearchShiftResponseLiveData(Context context, GetSearchShiftListRequest getSearchShiftRequest) {
        getShiftListResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.getSearchShiftRequest = getSearchShiftRequest;
        getSearchResponse();
        return getShiftListResponseMutableLiveData;
    }


    private void getSearchResponse() {
        Call<GetVolunteerShiftListResponse> call = apiInterface.getSearchShiftList(getSearchShiftRequest);

        call.enqueue(new Callback<GetVolunteerShiftListResponse>() {
            @Override
            public void onResponse(Call<GetVolunteerShiftListResponse> call, Response<GetVolunteerShiftListResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        getShiftListResponseMutableLiveData.postValue(response.body());
                        if(response.body().getShiftData()!=null) {
                            shiftData = response.body().getShiftData();
                            for (int i = 0; i < shiftData.size(); i++) {
                                shiftData.get(i).setEvent_id(getSearchShiftRequest.getEvent_id());
                            }
                            new DBVolunteerSaveShiftList(context, shiftData).execute();
                        }
                    }
                }
                else
                {
                    getShiftListResponseMutableLiveData.postValue(null);
                }
            }
            @Override
            public void onFailure(Call<GetVolunteerShiftListResponse> call, Throwable t) {
                t.printStackTrace();
                getShiftListResponseMutableLiveData.postValue(null);
            }
        });
    }


}
