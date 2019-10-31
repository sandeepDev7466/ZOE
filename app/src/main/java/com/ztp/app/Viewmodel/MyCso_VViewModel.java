package com.ztp.app.Viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.ztp.app.Data.Remote.Model.Request.MyCSORequest_V;
import com.ztp.app.Data.Remote.Model.Response.CSOAllResponse;
import com.ztp.app.Data.Remote.Model.Response.MyCSOResponse_V;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyCso_VViewModel extends ViewModel {

    private MutableLiveData<MyCSOResponse_V> myCSOResponse_vMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private MyCSORequest_V myCSORequest_v;


    public MutableLiveData<MyCSOResponse_V> getMyCSO_V(MyCSORequest_V myCSORequest_v) {
        myCSOResponse_vMutableLiveData = new MutableLiveData<>();
        this.myCSORequest_v = myCSORequest_v;
        getResponse();
        return myCSOResponse_vMutableLiveData;
    }

    public void getResponse() {
        Call<MyCSOResponse_V> call = apiInterface.getMyCSO_V(myCSORequest_v);

        call.enqueue(new Callback<MyCSOResponse_V>() {
            @Override
            public void onResponse(Call<MyCSOResponse_V> call, Response<MyCSOResponse_V> response) {
                if (response.body() != null) {
                    myCSOResponse_vMutableLiveData.postValue(response.body());

                }
            }

            @Override
            public void onFailure(Call<MyCSOResponse_V> call, Throwable t) {
                t.printStackTrace();
                myCSOResponse_vMutableLiveData.postValue(null);
            }
        });
    }
}
