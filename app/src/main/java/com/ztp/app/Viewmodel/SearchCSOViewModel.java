package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.ztp.app.Data.Remote.Model.Request.SearchCSORequest;
import com.ztp.app.Data.Remote.Model.Response.SearchCSOResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchCSOViewModel extends ViewModel {

    private MutableLiveData<SearchCSOResponse> searchCSOResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private SearchCSORequest searchCSORequest;

    public LiveData<SearchCSOResponse> getSearchCSOResponse(SearchCSORequest searchCSORequest) {
        searchCSOResponseMutableLiveData = new MutableLiveData<>();
        this.searchCSORequest = searchCSORequest;
        getResponse();
        return searchCSOResponseMutableLiveData;
    }

    public void getResponse() {
        Call<SearchCSOResponse> call = apiInterface.getSearchCSO(searchCSORequest);

        call.enqueue(new Callback<SearchCSOResponse>() {
            @Override
            public void onResponse(Call<SearchCSOResponse> call, Response<SearchCSOResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        searchCSOResponseMutableLiveData.postValue(response.body());

                    }
                } else {
                    searchCSOResponseMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<SearchCSOResponse> call, Throwable t) {
                t.printStackTrace();
                searchCSOResponseMutableLiveData.postValue(null);
            }
        });
    }
}
