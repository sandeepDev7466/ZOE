package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveSchool;
import com.ztp.app.Data.Remote.Model.Response.SchoolResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchoolViewModel extends ViewModel {
    private MutableLiveData<SchoolResponse> schoolResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    @SuppressLint("StaticFieldLeak")
    Context context;

    public LiveData<SchoolResponse> getSchoolResponse(Context context) {

        schoolResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        schoolResponse();
        return schoolResponseMutableLiveData;
    }

    private void schoolResponse() {
        Call<SchoolResponse> call = apiInterface.getSchool();

        call.enqueue(new Callback<SchoolResponse>() {
            @Override
            public void onResponse(Call<SchoolResponse> call, Response<SchoolResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        schoolResponseMutableLiveData.postValue(response.body());
                        if (response.body().getSchoolData() != null && response.body().getSchoolData().size() > 0)
                            new DBSaveSchool(context, response.body().getSchoolData()).execute();
                    }
                }
                else
                {
                    schoolResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SchoolResponse> call, Throwable t) {
                t.printStackTrace();
                schoolResponseMutableLiveData.postValue(null);
            }
        });
    }
}
