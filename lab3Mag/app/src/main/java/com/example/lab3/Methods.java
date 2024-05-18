package com.example.lab3;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;


public interface Methods {
    @GET("api/questions")
    Call<List<Model>> getAllData();
}

