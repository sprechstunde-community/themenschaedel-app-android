package sprechstunde.community.themenschaedel.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import sprechstunde.community.themenschaedel.model.ResponseEpisode;
import sprechstunde.community.themenschaedel.model.ResponseTopic;

public interface Api {

    String BASE_URL = "https://api.schaedel.rocks/";

    @GET("episodes")
    Call<ResponseEpisode> getEpisodesByPage(@Query("page") int number);

    @GET("topics")
    Call<ResponseTopic> getTopicsByPage(@Query("page") int number);
}
