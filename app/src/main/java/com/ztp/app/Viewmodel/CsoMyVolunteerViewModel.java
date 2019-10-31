package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveFollower;
import com.ztp.app.Data.Remote.Model.Request.CsoMyVolunteerRequest;
import com.ztp.app.Data.Remote.Model.Response.CsoMyFollowerResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CsoMyVolunteerViewModel extends ViewModel {

    private MutableLiveData<CsoMyFollowerResponse> csoMyVolunteerResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private CsoMyVolunteerRequest csoMyVolunteerRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<CsoMyFollowerResponse> getCsoMyVolunteerResponse(Context context, CsoMyVolunteerRequest csoMyVolunteerRequest) {
        csoMyVolunteerResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.csoMyVolunteerRequest = csoMyVolunteerRequest;
        getResponse();
        return csoMyVolunteerResponseMutableLiveData;
    }

    public void getResponse() {
        Call<CsoMyFollowerResponse> call = apiInterface.getCsoMyVolunteer(csoMyVolunteerRequest);

        call.enqueue(new Callback<CsoMyFollowerResponse>() {
            @Override
            public void onResponse(Call<CsoMyFollowerResponse> call, Response<CsoMyFollowerResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        csoMyVolunteerResponseMutableLiveData.postValue(response.body());
                        if (response.body().getResData() != null && response.body().getResData().size() > 0)
                            new DBSaveFollower(context, response.body().getResData()).execute();
                    }
                } else {
                    csoMyVolunteerResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<CsoMyFollowerResponse> call, Throwable t) {
                t.printStackTrace();
                csoMyVolunteerResponseMutableLiveData.postValue(null);
            }
        });
    }
}
