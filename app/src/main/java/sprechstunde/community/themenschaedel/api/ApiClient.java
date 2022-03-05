package sprechstunde.community.themenschaedel.api;

import android.util.ArrayMap;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.SessionManagement;
import sprechstunde.community.themenschaedel.UsedSharedPreferences;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.ResponseLogin;
import sprechstunde.community.themenschaedel.model.SessionData;
import sprechstunde.community.themenschaedel.model.User;
import sprechstunde.community.themenschaedel.model.episode.EpisodeHostCrossRef;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.HostViewModel;
import sprechstunde.community.themenschaedel.viewmodel.SessionViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;
import sprechstunde.community.themenschaedel.viewmodel.UserViewModel;

public class ApiClient {

    private static final String API = "API_CLIENT";
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

    private void loggedInSuccessful () {
        NavHostFragment navHostFragment = (NavHostFragment) mMainActivity.getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
        nvController.navigate(R.id.nav_podcast);
    }

    private void manageResponseCodes(int code, boolean logIn) {
        switch (code) {
            case 200: {
                if(logIn) {
                    Toast.makeText(mMainActivity, mMainActivity.getString(R.string.successful_login), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mMainActivity, mMainActivity.getString(R.string.successful_register), Toast.LENGTH_SHORT).show();
                }
                loggedInSuccessful();
            } break;
            case 400: {
                Toast.makeText(mMainActivity, mMainActivity.getString(R.string.authentification_error), Toast.LENGTH_LONG).show();
            } break;
        }
    }

    public void logIn(SessionViewModel viewModel, UserViewModel userViewModel, String username, String password) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", username);
        jsonParams.put("password", password);

        RequestBody body = RequestBody.create((new JSONObject(jsonParams)).toString(), okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Call<ResponseLogin> call = getMyApi().login(body);
        Callback<ResponseLogin> callback = new Callback<ResponseLogin>() {

            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, Response<ResponseLogin> response) {
                int code = response.code();
                ResponseLogin results = response.body();
                manageResponseCodes(code, true);
                if(results != null) {
                    SessionData data = new SessionData();
                    data.setAccessToken(results.getAccessToken());
                    data.setRefreshToken(results.getRefreshToken());
                    viewModel.insert(data);
                    saveMyselfToDB(viewModel, userViewModel, results.getAccessToken());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
            }
        };
        call.enqueue(callback);
    }

    public void register(SessionViewModel viewModel,UserViewModel userViewModel, String username, String email, String password) {
        Map<String, Object> jsonParams = new ArrayMap<>();
        jsonParams.put("username", username);
        jsonParams.put("password", password);
        jsonParams.put("email", email);

        RequestBody body = RequestBody.create((new JSONObject(jsonParams)).toString(), okhttp3.MediaType.parse("application/json; charset=utf-8"));
        Call<ResponseLogin> call = getMyApi().register(body);
        Callback<ResponseLogin> callback = new Callback<ResponseLogin>() {
            @Override
            public void onResponse(@NonNull Call<ResponseLogin> call, Response<ResponseLogin> response) {
                int code = response.code();
                ResponseLogin result = response.body();
                manageResponseCodes(code, true);
                if(result != null) {
                    SessionData data = new SessionData();
                    data.setAccessToken(result.getAccessToken());
                    data.setRefreshToken(result.getRefreshToken());
                    viewModel.insert(data);
                    saveMyselfToDB(viewModel, userViewModel, result.getAccessToken());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseLogin> call, @NonNull Throwable t) {
            }
        };
        call.enqueue(callback);
    }

    public void logout() {
        SessionViewModel sessionViewModel = new ViewModelProvider(mMainActivity).get(SessionViewModel.class);
        sessionViewModel.getSessionData().observe(mMainActivity, sessionData -> {
            Call<ResponseBody> call = getMyApi().logout("Bearer "+ sessionData.getAccessToken());
            Callback<ResponseBody> callback = new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    ResponseBody result = response.body();
                    sessionViewModel.deleteToken();
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.e("ERROR--", t.getMessage());
                }
            };
            call.enqueue(callback);
        });
    }

    public void saveMyselfToDB(SessionViewModel viewModel, UserViewModel userViewModel, String accessToken) {
        Call<User> call = getMyApi().getMyself("Bearer "+ accessToken);
        Callback<User> callback = new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                User result = response.body();
                userViewModel.insert(result);
                viewModel.insertMySelf(result);

                if(result == null) {
                    return;
                }
                SessionManagement.ROLE role = SessionManagement.getInstance().getEnumFromInt(result.getRoleId());
                UsedSharedPreferences.getInstance(mMainActivity).saveUserRoleToSharedPreferences(role);
                NavigationView navigationView = mMainActivity.findViewById(R.id.nav_view);
                SessionManagement.getInstance().setUIWhenLoggedIn(navigationView, mMainActivity, result);
            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                Log.e("ERROR--", t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    public void saveTopicsToDB(TopicViewModel viewModel, List<Topic> topics) {

        for (int i = 0; i < topics.size(); i++) {
            Topic topic =  topics.get(i);
            if(topic.getName() == null) {
                return;
            }
            String name = topic.getName().replace("&amp;", "&");
            topic.setName(name);

            if (topic.getSubtopics() != null && topic.getSubtopics().size() > 0) {
                saveSubTopicsToDB(topic.getSubtopics(), viewModel);
            }
            viewModel.insert(topic); }
    }

    private void saveSubTopicsToDB(List<Subtopic> subtopics, TopicViewModel viewModel) {
        for (int i = 0; i < subtopics.size(); i++) {
            Subtopic subtopic = subtopics.get(i);

            String name = subtopic.getName().replace("&amp;", "&");
            subtopic.setName(name);
            viewModel.insert(subtopic);
        }
    }

    public void saveEpisodesToDB(EpisodeViewModel episodeViewModel, TopicViewModel topicViewModel, HostViewModel hostViewModel, boolean wasRefreshed, SwipeRefreshLayout fragmentPodcastSwipeUp) {
        Call<ArrayList<Episode>> call = getMyApi().getAllEpisodes();
        Callback<ArrayList<Episode>> callback = new Callback<ArrayList<Episode>>() {
            @Override
            public void onResponse(@NonNull Call<ArrayList<Episode>> call, @NonNull Response<ArrayList<Episode>> response) {
                ArrayList<Episode> results = response.body();

                for (int i = 0; i < Objects.requireNonNull(results).size(); i++) {
                    Episode episode = results.get(i);
                    episodeViewModel.insert(formatEpisodeFromApi(episode));

                    if(episode.getTopics() != null)
                        saveTopicsToDB(topicViewModel, episode.getTopics());

                    if(episode.getHosts() != null)
                        saveHostsToDB(episodeViewModel, hostViewModel, episode.getHosts(), episode);
                }

                if(wasRefreshed)  {
                    Toast.makeText(mMainActivity, R.string.refresh_finished, Toast.LENGTH_SHORT).show();
                }

                if(fragmentPodcastSwipeUp != null){
                    fragmentPodcastSwipeUp.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ArrayList<Episode>> call, @NonNull Throwable t) {
                Log.e("ERROR--", t.getMessage());
            }
        };
        call.enqueue(callback);
    }

    public void saveHostsToDB(EpisodeViewModel episodeViewModel, HostViewModel hostViewModel, List<Host> hosts, Episode episode) {
        for (int i = 0; i < hosts.size(); i++) {
            hostViewModel.insert(hosts.get(i));
            EpisodeHostCrossRef crossRef = new EpisodeHostCrossRef(episode.getEpisodeNumber(), hosts.get(i).getName());
            episodeViewModel.insertEpisodeHostCrossRef(crossRef);
        }
    }

    private Episode formatEpisodeFromApi(Episode episode) {
        try {
            String date = episode.getDate();
            date = date.replace("T", " ");
            SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            SimpleDateFormat myFormat = new SimpleDateFormat("d. MMMM yyyy", Locale.getDefault());
            String reformattedDate = myFormat.format(Objects.requireNonNull(fromUser.parse(date)));

            int duration = Integer.parseInt(episode.getDuration());
            int hours = duration / 3600;
            int minutes = (duration % 3600) / 60;
            String reformattedDuration = String.format(Locale.getDefault(), "%01dh %01dmin", hours, minutes);

            String title = episode.getTitle().replace("&amp;", "&");

            episode.setDate(reformattedDate);
            episode.setDuration(reformattedDuration);
            episode.setTitle(title);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return episode;
    }
}