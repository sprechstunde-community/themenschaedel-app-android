package sprechstunde.community.themenschaedel.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import sprechstunde.community.themenschaedel.model.ResponseData;

public interface Api {

    String BASE_URL = "https://api.schaedel.rocks/";

    @GET("episodes")
    Call<ResponseData> getEpisodesByPage(@Query("page") int number);
}
