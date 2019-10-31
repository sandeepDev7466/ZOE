package com.ztp.app.Viewmodel;

import android.annotation.SuppressLint;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.os.AsyncTask;

import com.ztp.app.Data.Local.Room.Async.Save.DBSaveShiftDetail;
import com.ztp.app.Data.Local.Room.Database.RoomDB;
import com.ztp.app.Data.Remote.Model.Request.GetShiftDetailRequest;
import com.ztp.app.Data.Remote.Model.Response.GetShiftDetailResponse;
import com.ztp.app.Data.Remote.Service.Api;
import com.ztp.app.Data.Remote.Service.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetShiftDetailViewModel extends ViewModel {
    private MutableLiveData<GetShiftDetailResponse> getShiftDetailMutableLiveData;
    private ApiInterface apiInterface = Api.getClient();
    private GetShiftDetailRequest getShiftDetailRequest;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    RoomDB roomDB;

    public LiveData<GetShiftDetailResponse> getShiftDetailResponseLiveData(Context context, GetShiftDetailRequest getShiftDetailRequest) {
        getShiftDetailMutableLiveData = new MutableLiveData<>();
        this.context = context;
        this.getShiftDetailRequest = getShiftDetailRequest;
        getResponse();
        return getShiftDetailMutableLiveData;
    }

    private void getResponse() {
        Call<GetShiftDetailResponse> call = apiInterface.getShiftDetail(getShiftDetailRequest);
        roomDB = RoomDB.getInstance(context);

        call.enqueue(new Callback<GetShiftDetailResponse>() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onResponse(Call<GetShiftDetailResponse> call, Response<GetShiftDetailResponse> response) {
                if(response.isSuccessful()) {
                    if (response.body() != null) {
                        getShiftDetailMutableLiveData.postValue(response.body());

                        if (response.body().getResStatus().equalsIgnoreCase("200"))
                        {
                            new AsyncTask<Void, Void, GetShiftDetailResponse.ShiftDetail>() {
                                @Override
                                protected void onPostExecute(GetShiftDetailResponse.ShiftDetail shiftDetail) {
                                    if (shiftDetail == null) {
                                        GetShiftDetailResponse.ShiftDetail shift_detail = response.body().getShiftDetail();
                                        shift_detail.setShiftId(Integer.parseInt(getShiftDetailRequest.getShiftId()));
                                        new DBSaveShiftDetail(context, shift_detail).execute();
                                    }
                                }

                                @Override
                                protected GetShiftDetailResponse.ShiftDetail doInBackground(Void... voids) {
                                    return roomDB.getShiftDetailResponseDao().getShiftDetailFromId(getShiftDetailRequest.getShiftId());
                                }
                            }.execute();
                    }


                    /*GetShiftDetailResponse.ShiftDetail shiftDetail =  response.body().getShiftDetail();
                    shiftDetail.setShiftId(Integer.parseInt(getShiftDetailRequest.getShiftId()));
                    new DBSaveShiftDetail(context,shiftDetail);*/

                    }
                }
                else
                {
                    getShiftDetailMutableLiveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<GetShiftDetailResponse> call, Throwable t) {
                t.printStackTrace();
                getShiftDetailMutableLiveData.postValue(null);
            }
        });
    }
}
