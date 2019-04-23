package com.ztp.app.Viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.BlogSearchRequest;
import com.ztp.app.Data.Remote.Model.Response.BlogSearchResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogSearchViewModel extends ViewModel {
    private MutableLiveData<BlogSearchResponse> blogSearchResponseMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private BlogSearchRequest blogSearchRequest;

    public LiveData<BlogSearchResponse> getBlogSearchResponse(BlogSearchRequest blogSearchRequest) {

        blogSearchResponseMutableLiveData = new MutableLiveData<>();
        this.blogSearchRequest = blogSearchRequest;
        loadResponse();
        return blogSearchResponseMutableLiveData;
    }

    private void loadResponse() {

        Call<BlogSearchResponse> call = apiInterface.getBlogSearch(blogSearchRequest);

        call.enqueue(new Callback<BlogSearchResponse>() {
            @Override
            public void onResponse(Call<BlogSearchResponse> call, Response<BlogSearchResponse> response) {
                if (response.body() != null) {
                    blogSearchResponseMutableLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<BlogSearchResponse> call, Throwable t) {
                t.printStackTrace();
                blogSearchResponseMutableLiveData.postValue(null);
            }
        });
    }
}
