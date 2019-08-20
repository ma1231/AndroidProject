package com.example.personalapplication.http;

import android.widget.ImageView;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest_Interface {

    @GET("")
    Call<String> getImage();

}
