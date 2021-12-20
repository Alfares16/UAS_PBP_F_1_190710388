package com.uas.hospital.api;

import com.uas.hospital.models.Pasien;
import com.uas.hospital.models.PasienResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {
    @Headers({"Accept: application/json"})
    @GET("pasien")
    Call<PasienResponse> getAllPasien();

    @Headers({"Accept: application/json"})
    @GET("pasien/{id}")
    Call<PasienResponse> getPasienById(@Path("id") long id);

    @Headers({"Accept: application/json"})
    @POST("pasien")
    Call<PasienResponse> createPasien(@Body Pasien pasien);

    @Headers({"Accept: application/json"})
    @PUT("pasien/{id}")
    Call<PasienResponse> updatePasien(@Path("id") long id,
                                      @Body Pasien pasien);
    @Headers({"Accept: application/json"})
    @DELETE("pasien/{id}")
    Call<PasienResponse> deletePasien(@Path("id") long id);
}