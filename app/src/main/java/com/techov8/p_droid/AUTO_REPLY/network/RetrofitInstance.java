package com.techov8.p_droid.AUTO_REPLY.network;



//import androidx.databinding.library.BuildConfig;

import com.techov8.p_droid.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://api.github.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {

            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            if (BuildConfig.DEBUG) {
                // set your desired log level
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            }
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor) // <-- this is the important line!
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
