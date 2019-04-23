package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.GetEventsRequest;
import com.ztp.app.Data.Remote.Model.Response.GetEventsResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetEventsViewModel extends ViewModel {
    private MutableLiveData<GetEventsResponse> getEventsMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetEventsRequest getEventsRequest;

    public LiveData<GetEventsResponse> getEventsResponseLiveData(GetEventsRequest getEventsRequest) {
        getEventsMutableLiveData = new MutableLiveData<>();
        this.getEventsRequest = getEventsRequest;
        getResponse();
        return getEventsMutableLiveData;
    }

    private void getResponse() {
        Call<GetEventsResponse> call = apiInterface.getEventList(getEventsRequest);

        call.enqueue(new Callback<GetEventsResponse>() {
            @Override
            public void onResponse(Call<GetEventsResponse> call, Response<GetEventsResponse> response) {
                if (response.body() != null) {
                    getEventsMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<GetEventsResponse> call, Throwable t) {
                t.printStackTrace();
                getEventsMutableLiveData.postValue(null);
            }
        });
    }
}
