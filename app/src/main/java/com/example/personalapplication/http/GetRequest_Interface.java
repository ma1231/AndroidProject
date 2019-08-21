package com.example.personalapplication.http;

import android.widget.ImageView;

import com.example.personalapplication.model.BingPic;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetRequest_Interface {

    @GET("HPImageArchive.aspx?format=js&idx=0&n=1")
    Call<BingPic> getImage();

}
