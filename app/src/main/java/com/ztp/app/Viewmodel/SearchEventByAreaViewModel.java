package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveSearchedEvent;
import com.ztp.app.Data.Remote.Model.Request.SearchEventRequest;
import com.ztp.app.Data.Remote.Model.Request.SearchEventbyAreaRequest;
import com.ztp.app.Data.Remote.Model.Response.SearchEventResponse;
import com.ztp.app.Data.Remote.Model.Response.SearchEventbyAreaResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchEventByAreaViewModel extends ViewModel {
    private MutableLiveData<SearchEventbyAreaResponse> searchEventResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private SearchEventbyAreaRequest searchEventRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;

    public LiveData<SearchEventbyAreaResponse> getSearchedEventsResponse(Context context, SearchEventbyAreaRequest searchEventRequest) {
        searchEventResponseMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.searchEventRequest = searchEventRequest;
        getResponse();
        return searchEventResponseMutableLiveData;
    }

    public void getResponse() {
        Call<SearchEventbyAreaResponse> call = apiInterface.searchByArea(searchEventRequest);

        call.enqueue(new Callback<SearchEventbyAreaResponse>() {
            @Override
            public void onResponse(Call<SearchEventbyAreaResponse> call, Response<SearchEventbyAreaResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        searchEventResponseMutableLiveData.postValue(response.body());
//                        if (response.body().getSearchedEvents() != null && response.body().getSearchedEvents().size() > 0)
//                            new DBSaveSearchedEvent(context, response.body().getSearchedEvents()).execute();
                    }
                }
                else
                {
                    searchEventResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchEventbyAreaResponse> call, Throwable t) {
                t.printStackTrace();
                searchEventResponseMutableLiveData.postValue(null);
            }
        });
    }
}
