package com.example.kinoprokat.services;

import com.example.kinoprokat.interfaces.APIes;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;

    private String BASE_URL = "http://192.168.0.5:8080/api/";
    private Retrofit mRetrofit;
    private String token;
    private String token_key = "token";
    private String job_id_key = "job";
    private String jobId;

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken_key() {
        return token_key;
    }

    public String getJob_id_key() {
        return job_id_key;
    }

    public APIes getApies() {
        OkHttpClient client;

        if (token != null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder().header("Authorization", token);
                    Request finalRequest = requestBuilder.build();
                    return chain.proceed(finalRequest);
                }
            });
            client = httpClient.build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } else {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return mRetrofit.create(APIes.class);
    }
}
