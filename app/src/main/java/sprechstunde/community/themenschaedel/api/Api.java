package sprechstunde.community.themenschaedel.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sprechstunde.community.themenschaedel.model.ResponseEpisode;
import sprechstunde.community.themenschaedel.model.ResponseTopic;

public interface Api {

    String BASE_URL = "https://api.schaedel.rocks/";

    @GET("episodes")
    Call<ResponseEpisode> getEpisodesByPage(@Query("page") int number);

    @GET("topics")
    Call<ResponseTopic> getTopicsByPage(@Query("page") int number);

    @FormUrlEncoded
    @POST("auth/login")
    Call<ResponseBody> login(@Field("username") String username, @Field("password") String password, @Field("application_name") String name);
}
