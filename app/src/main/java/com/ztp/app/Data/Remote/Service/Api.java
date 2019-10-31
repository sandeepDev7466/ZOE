package com.ztp.app.Data.Remote.Service;

import com.google.gson.GsonBuilder;
import com.ztp.app.BuildConfig;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {

    public static ApiInterface getClient() {

       /* final String BASE_URL = "https://www.zoeblueprint.com/api/";  // production
        //final String BASE_URL = "https://blueprint.hashtaglabs.in/api/";  // blueprint
        //final String BASE_URL = "https://ztp.hashtaglabs.in/api/";   // thumbprint*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                .build();

        return retrofit.create(ApiInterface.class);
    }
}
