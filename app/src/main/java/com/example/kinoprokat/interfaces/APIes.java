package com.example.kinoprokat.interfaces;

import com.example.kinoprokat.models.Contract;
import com.example.kinoprokat.models.Login;
import com.example.kinoprokat.models.Movie;
import com.example.kinoprokat.models.Role;
import com.example.kinoprokat.models.Theater;
import com.example.kinoprokat.models.TheaterReport;
import com.example.kinoprokat.models.Token;
import com.example.kinoprokat.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIes {

    // user APIs ////////////////////////////////////
    @POST("user/login")
    public Call<Token> login(@Body Login login);

    @GET("user/{id}")
    public Call<User> getUserById(@Path("id") String id);

    // role APIs ////////////////////////////////////
    @GET("role")
    public Call<List<Role>> getRoles();

    @GET("role/{id}")
    public Call<Role> getRoleById(@Path("id") String id);

    // theater report APIs //////////////////////////
    @GET("theater-report/by-theater/{id}")
    public Call<List<TheaterReport>> getThReportsByThId(@Path("id") String id);

    @GET("theater-report/{id}")
    public Call<TheaterReport> getThReportsById(@Path("id") String id);

    // theater APIs ////////////////////////////////////
    @GET("theater/{id}")
    public Call<Theater> getTheaterById(@Path("id") String id);

    // movies APIs ////////////////////////////////////
    @GET("movie")
    public Call<List<Movie>> getAllMovies();

    // contract APIs ////////////////////////////////////
    @GET("contract")
    public Call<List<Contract>> getAllContracts();
}
