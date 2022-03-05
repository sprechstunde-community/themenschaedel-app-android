package sprechstunde.community.themenschaedel.api;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.model.ResponseLogin;
import sprechstunde.community.themenschaedel.model.ResponseTopic;
import sprechstunde.community.themenschaedel.model.User;

public interface Api {

    String BASE_URL = "https://api.alyra.dev/api/";

    @GET("auth/me")
    Call<User> getMyself(@Header("Authorization") String authHeader);

    @GET("episodes/all")
    Call<ArrayList<Episode>> getAllEpisodes();

    @GET("topics/all")
    Call<ResponseTopic> getAllTopics();

    @POST("auth/logout")
    Call<ResponseBody> logout(@Header("Authorization") String authHeader);

    @POST("auth/login")
    Call<ResponseLogin> login(@Body RequestBody params);

    @POST("auth/register")
    Call<ResponseLogin> register(@Body RequestBody params);
}
