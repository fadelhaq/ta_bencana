package com.example.v.activites.bencana;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BencanaService {
    @GET("data/get_bencana")
    Call<BencanaResponse> getBencana();
}
