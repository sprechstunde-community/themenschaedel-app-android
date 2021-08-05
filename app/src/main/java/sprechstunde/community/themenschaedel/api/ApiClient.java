package sprechstunde.community.themenschaedel.api;

import android.util.Log;

import java.lang.reflect.Field;

import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sprechstunde.community.themenschaedel.model.Episode;

public class ApiClient {

    private static ApiClient instance = null;
    private Api myApi;

    private ApiClient() {

        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        myApi = retrofit.create(Api.class);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public Api getMyApi() {
        return myApi;
    }

}
