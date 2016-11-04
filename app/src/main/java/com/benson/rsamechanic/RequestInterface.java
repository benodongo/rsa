package com.benson.rsamechanic;

import com.benson.rsamechanic.models.ServerRequest;
import com.benson.rsamechanic.models.ServerResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RequestInterface {

    @POST("project/mechanic/")
    Call<ServerResponse> operation(@Body ServerRequest request);
    @POST("project/mechanic/")
    Call<ServerResponse> receive(@Body ServerRequest request);

}
