package com.mareyn.group06project02.AgifyAPI;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface AgifyApiService {
  @GET("/")
  Call<Agify> getAgeFromName(@Query("name") String name);
}
