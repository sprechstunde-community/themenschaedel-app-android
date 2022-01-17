package sprechstunde.community.themenschaedel.api;

import android.util.Log;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.UsedSharedPreferences;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.ResponseEpisode;
import sprechstunde.community.themenschaedel.model.ResponseTopic;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class ApiClient {

    private static ApiClient instance = null;
    private final Api myApi;
    private final MainActivity mMainActivity;

    private ApiClient(MainActivity mainActivity) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(Api.class);
        mMainActivity = mainActivity;
    }

    public static synchronized ApiClient getInstance(MainActivity mainActivity) {
        if (instance == null) {
            instance = new ApiClient(mainActivity);
        }
        return instance;
    }

    public Api getMyApi() {
        return myApi;
    }

    public void saveTopicsToDB(TopicViewModel viewModel) {
        Call<ResponseTopic> call = getMyApi().getTopicsByPage(1);
        Callback<ResponseTopic> callback = new retrofit2.Callback<ResponseTopic>() {
            @Override
            public void onResponse(@NonNull Call<ResponseTopic> call, @NonNull Response<ResponseTopic> response) {
                ResponseTopic results = response.body();
                int currentPage = viewModel.getCurrentPage();

                for (int i = 0; i < Objects.requireNonNull(results).getData().size(); i++) {
                    Topic topic = results.getData().get(i);

                    String name = topic.getName().replace("&amp;", "&");
                    topic.setName(name);


                    if (topic.getSubtopics() != null && topic.getSubtopics().size() > 0) {
                        saveSubTopicsToDB(topic.getSubtopics(), viewModel);
                        topic.setHasSubtopics(true);
                    } else {
                        topic.setHasSubtopics(false);
                    }

                    viewModel.insert(topic);
                }

                // true: first start & not last page
                // OR    total episodes in DB =/= total episodes in API & not last page
                if (currentPage < results.getMeta().getLastPage() && (UsedSharedPreferences.getInstance(mMainActivity).getFirstStartFromSharedPreferences() == 1 || results.getMeta().getTotal() != UsedSharedPreferences.getInstance(mMainActivity).getEpisodeCountFromSharedPreferences())) {
                    viewModel.setCurrentPage(currentPage + 1);
                    call = getMyApi().getTopicsByPage(currentPage);
                    call.enqueue(this);
                    UsedSharedPreferences.getInstance(mMainActivity).saveEpisodeCountToSharedPreferences(results.getMeta().getTotal());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseTopic> call, @NonNull Throwable t) {
                Log.e("ERROR--", t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    private void saveSubTopicsToDB(List<Subtopic> subtopics, TopicViewModel viewModel) {
        for (int i = 0; i < subtopics.size(); i++) {
            Subtopic subtopic = subtopics.get(i);

            String name = subtopic.getName().replace("&amp;", "&");
            subtopic.setName(name);
            viewModel.insert(subtopic);
        }
    }

    public void saveEpisodesToDB(EpisodeViewModel viewModel) {
        Call<ResponseEpisode> call = getMyApi().getEpisodesByPage(1);
        Callback<ResponseEpisode> callback = new retrofit2.Callback<ResponseEpisode>() {
            @Override
            public void onResponse(@NonNull Call<ResponseEpisode> call, @NonNull Response<ResponseEpisode> response) {
                ResponseEpisode results = response.body();
                int currentPage = viewModel.getCurrentPage();

                for (int i = 0; i < Objects.requireNonNull(results).getData().size(); i++) {
                    viewModel.insert(formatEpisodeFromApi(results.getData().get(i)));
                }
                // true: first start & not last page
                // OR    total episodes in DB =/= total episodes in API & not last page
                if (currentPage < results.getMeta().getLastPage() && (UsedSharedPreferences.getInstance(mMainActivity).getFirstStartFromSharedPreferences() == 1 || results.getMeta().getTotal() != UsedSharedPreferences.getInstance(mMainActivity).getEpisodeCountFromSharedPreferences())) {
                    viewModel.setCurrentPage(currentPage + 1);
                    call = getMyApi().getEpisodesByPage(currentPage);
                    call.enqueue(this);
                    UsedSharedPreferences.getInstance(mMainActivity).saveEpisodeCountToSharedPreferences(results.getMeta().getTotal());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseEpisode> call, @NonNull Throwable t) {
                Log.e("ERROR--", t.getMessage());
            }
        };
        call.enqueue(callback);
    }


    private Episode formatEpisodeFromApi(Episode episode) {
        try {
            String date = episode.getDate();
            SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            SimpleDateFormat myFormat = new SimpleDateFormat("d. MMMM yyyy", Locale.getDefault());
            String reformattedDate = myFormat.format(Objects.requireNonNull(fromUser.parse(date)));

            int duration = Integer.parseInt(episode.getDuration());
            int hours = duration / 3600;
            int minutes = (duration % 3600) / 60;
            String reformattedDuration = String.format(Locale.getDefault(), "%01dh %01dmin", hours, minutes);

            String title = episode.getTitle().replace("&amp;", "&");
            String description = episode.getDescription().replace("&amp;", "&");

            episode.setDate(reformattedDate);
            episode.setDuration(reformattedDuration);
            episode.setTitle(title);
            episode.setDescription(description);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return episode;
    }
}