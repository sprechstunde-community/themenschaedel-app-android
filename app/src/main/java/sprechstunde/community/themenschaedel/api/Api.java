package sprechstunde.community.themenschaedel.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import sprechstunde.community.themenschaedel.model.Episode;

public interface Api {

    String BASE_URL = "https://api.schaedel.rocks/episodes";
    @GET("episode")
    Call<List<Episode>> getEpisodes();
}
