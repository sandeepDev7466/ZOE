package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.UpdateVolunteerHoursRequest;
import com.ztp.app.Data.Remote.Model.Response.UpdateVolunteerHoursResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateVolunteerHoursViewModel extends ViewModel {
    private MutableLiveData<UpdateVolunteerHoursResponse> updateVolunteerHoursResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private UpdateVolunteerHoursRequest updateVolunteerHoursRequest;

    public LiveData<UpdateVolunteerHoursResponse> updateVolunteerHoursResponse(UpdateVolunteerHoursRequest updateVolunteerHoursRequest) {
        updateVolunteerHoursResponseMutableLiveData = new MutableLiveData<>();
        this.updateVolunteerHoursRequest = updateVolunteerHoursRequest;
        getResponse();
        return updateVolunteerHoursResponseMutableLiveData;
    }

    public void getResponse() {
        Call<UpdateVolunteerHoursResponse> call = apiInterface.updateVolunteerHours(updateVolunteerHoursRequest);

        call.enqueue(new Callback<UpdateVolunteerHoursResponse>() {
            @Override
            public void onResponse(Call<UpdateVolunteerHoursResponse> call, Response<UpdateVolunteerHoursResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        updateVolunteerHoursResponseMutableLiveData.postValue(response.body());
                    }
                }
                else
                {
                    updateVolunteerHoursResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<UpdateVolunteerHoursResponse> call, Throwable t) {
                t.printStackTrace();
                updateVolunteerHoursResponseMutableLiveData.postValue(null);
            }
        });
    }
}
