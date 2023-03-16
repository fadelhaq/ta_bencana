package com.example.v.activites.laporan;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LaporanService {
    @POST("insert")
    Call<LaporanResponse> sendLaporan(@Body Laporan laporan);
}
