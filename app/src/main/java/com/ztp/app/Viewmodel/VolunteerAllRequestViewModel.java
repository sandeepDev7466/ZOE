package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveVolunteerAllResponse;
import com.ztp.app.Data.Remote.Model.Request.VolunteerAllRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerAllResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VolunteerAllRequestViewModel extends ViewModel {
    private MutableLiveData<VolunteerAllResponse> volunteerAllResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private VolunteerAllRequest volunteerAllRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;


    public LiveData<VolunteerAllResponse> getVolunteerAllRequestResponse(Context context, VolunteerAllRequest volunteerAllRequest) {
        volunteerAllResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.volunteerAllRequest = volunteerAllRequest;
        getResponse();
        return volunteerAllResponseMutableLiveData;
    }

    public void getResponse() {
        Call<VolunteerAllResponse> call = apiInterface.getVolunteerAllRequest(volunteerAllRequest);

        call.enqueue(new Callback<VolunteerAllResponse>() {
            @Override
            public void onResponse(Call<VolunteerAllResponse> call, Response<VolunteerAllResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        volunteerAllResponseMutableLiveData.postValue(response.body());
                        if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveVolunteerAllResponse(context, response.body().getResData()).execute();
                    }
                }
                else
                {
                    volunteerAllResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<VolunteerAllResponse> call, Throwable t) {
                t.printStackTrace();
                volunteerAllResponseMutableLiveData.postValue(null);
            }
        });
    }
}
