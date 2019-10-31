package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveTarget;
import com.ztp.app.Data.Remote.Model.Request.VolunteerTargetRequest;
import com.ztp.app.Data.Remote.Model.Response.VolunteerTargetResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint("StaticFieldLeak")
public class VolunteerTargetViewModel extends ViewModel {

    private MutableLiveData<VolunteerTargetResponse> volunteerTargetResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private VolunteerTargetRequest volunteerTargetRequest;
    private Context context;

    public MutableLiveData<VolunteerTargetResponse> getVolunteerTarget(Context context,VolunteerTargetRequest volunteerTargetRequest) {
        volunteerTargetResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.volunteerTargetRequest = volunteerTargetRequest;
        getResponse();
        return volunteerTargetResponseMutableLiveData;
    }

    public void getResponse() {
        Call<VolunteerTargetResponse> call = apiInterface.getVol_Targets(volunteerTargetRequest);

        call.enqueue(new Callback<VolunteerTargetResponse>() {
            @Override
            public void onResponse(Call<VolunteerTargetResponse> call, Response<VolunteerTargetResponse> response) {
                if (response.body() != null) {
                    volunteerTargetResponseMutableLiveData.postValue(response.body());
                    new DBSaveTarget(context, response.body().getTargetData()).execute();
                }
            }

            @Override
            public void onFailure(Call<VolunteerTargetResponse> call, Throwable t) {
                t.printStackTrace();
                volunteerTargetResponseMutableLiveData.postValue(null);
            }
        });
    }
}
