package com.mareyn.group06project02.AgifyAPI;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class AgifyApiClient {
  private static final String BASE_URL = "https://api.agify.io/";
  private static Retrofit retrofit;

  public static AgifyApiService getAgifyAPiService() {
    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(JacksonConverterFactory.create())
        .build();
    }
    return retrofit.create(AgifyApiService.class);
  }
}
