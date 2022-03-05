package sprechstunde.community.themenschaedel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import java.util.Objects;

import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.databinding.ActivityMainBinding;
import sprechstunde.community.themenschaedel.listener.ActivityToFragmentListener;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.HostViewModel;
import sprechstunde.community.themenschaedel.viewmodel.SessionViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ActivityToFragmentListener {

    private ActivityMainBinding mBinding;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;
    private CustomPopupWindow mPopupWindow;
    private SessionViewModel mSessionViewModel;

    private static final String CLIENT_ID = "18aeb0670f3e4097a91bc63ce3f77e11";
    private static final String REDIRECT_URI = "http://sprechstunde.community.themenschaedel/callback";
    private SpotifyAppRemote mSpotifyAppRemote;
    private ConnectionParams mConnectionParams;

    @Override
    protected void onStart() {
        super.onStart();
        mConnectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();

        EpisodeViewModel episodeViewModel =new ViewModelProvider(this).get(EpisodeViewModel.class);
        TopicViewModel topicViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
        HostViewModel hostViewModel = new ViewModelProvider(this).get(HostViewModel.class);
        mSessionViewModel = new ViewModelProvider(this).get(SessionViewModel.class);
        UsedSharedPreferences.getInstance(this).getUserRoleToSharedPreferences();
        UsedSharedPreferences.getInstance(this).saveFirstStartToSharedPreferences(UsedSharedPreferences.getInstance(this).getFirstStartFromSharedPreferences() + 1);
        episodeViewModel.setCurrentPage(1);
        topicViewModel.setCurrentPage(1);

        setContentView(view);

        new Thread(() -> {
            try {
                ApiClient.getInstance(this).saveEpisodesToDB(episodeViewModel, topicViewModel, hostViewModel, false, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        setUpNavigation();
        setUpToolbar();
        setUpDrawer();
    }

    private void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_podcast, R.id.nav_topic, R.id.nav_wiki, R.id.nav_login, R.id.nav_profile).setOpenableLayout(mBinding.drawerLayout).build();
        mNavController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void setUpToolbar() {
        Toolbar toolbar = mBinding.activityMainToolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    private void setUpDrawer() {
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.navView, mNavController);
        mBinding.navView.setNavigationItemSelectedListener(this);

        if (SessionManagement.getInstance().getCurrentRole() == SessionManagement.ROLE.ANONYM) {
            SessionManagement.getInstance().setUIWhenAnonym(mBinding.navView, this);
        } else {
            mSessionViewModel.getMyself().observe(this, myself -> SessionManagement.getInstance().setUIWhenLoggedIn(mBinding.navView, this, myself));
        }

        setTextColorForMenuItem();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, mNavController) || super.onOptionsItemSelected(item);
    }

    private void setTextColorForMenuItem() {
        Menu menuNav = mBinding.navView.getMenu();
        MenuItem menuItem = menuNav.findItem(R.id.nav_hint);
        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.onSurfaceSecondary)), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
        menuItem.setTitle(spanString);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.nav_podcast) {
            mBinding.activityMainToolbar.setBackgroundColor(getColor(R.color.primaryColor));
            mNavController.navigate(R.id.nav_podcast);
        } else if (item.getItemId() == R.id.nav_topic) {
            mBinding.activityMainToolbar.setBackgroundColor(getColor(R.color.primaryColor));
            mNavController.navigate(R.id.nav_topic);
        } else if (item.getItemId() == R.id.nav_login) {
            mBinding.activityMainToolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient_register, getTheme()));
            mNavController.navigate(R.id.nav_login);
        }

        if (item.getItemId() == R.id.nav_hint) {
            mPopupWindow = new CustomPopupWindow();
            if(SessionManagement.getInstance().getCurrentRole() == SessionManagement.ROLE.ANONYM) {
                mPopupWindow.showSortPopup(R.id.dialog_host_layout, R.layout.dialog_info_anonym, this);
                mPopupWindow.getContentView().findViewById(R.id.dialog_info_anonym_button).setOnClickListener(this);
            } else {
                mPopupWindow.showSortPopup(R.id.dialog_info_user_layout, R.layout.dialog_info_user, this);
                mPopupWindow.getContentView().findViewById(R.id.dialog_info_user_button).setOnClickListener(this);
            }
        } else {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mBinding.navView.getHeaderView(0)) {
            mBinding.activityMainToolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient, getTheme()));
            mNavController.navigate(R.id.nav_profile);
        } else if (v.getId() == R.id.dialog_info_anonym_button) {
            mBinding.activityMainToolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient, getTheme()));
            mNavController.navigate(R.id.nav_register);
            mPopupWindow.dismiss();
        }

        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
    }

    @Override
    public void startPlayingEpisodeAt(String uri, long offset) {
        SpotifyAppRemote.connect(this, mConnectionParams,
                new Connector.ConnectionListener() {

                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!"+ offset);

                        // Now you can start interacting with App Remote
                        mSpotifyAppRemote.getPlayerApi().pause();
                        mSpotifyAppRemote.getPlayerApi().play("spotify:episode:" + uri).setResultCallback(data -> mSpotifyAppRemote.getPlayerApi().seekTo(offset));

                        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.spotify.music");
                        if (launchIntent != null) {
                            startActivity(launchIntent);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                        // Something went wrong when attempting to connect! Handle errors here
                    }
                });
    }
}