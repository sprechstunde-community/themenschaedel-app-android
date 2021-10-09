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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;
import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.databinding.ActivityMainBinding;
import sprechstunde.community.themenschaedel.model.ResponseData;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.ViewModel;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private MainActivity mActivity = this;
    private ActivityMainBinding mBinding;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;
    private CustomPopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);
        getEpisodes();

        setUpNavigation();
        setUpToolbar();
        setUpDrawer();
    }

    private void getEpisodes() {

        Call<ResponseData> call = ApiClient.getInstance().getMyApi().getEpisodesByPage(1);
        call.enqueue(new retrofit2.Callback<ResponseData>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData> call, @NonNull Response<ResponseData> response) {
                ResponseData results = response.body();

                ViewModel mViewModel = new ViewModelProvider(mActivity).get(ViewModel.class);

                Log.d("Message", "url..." + response.raw().request().url() + "code..." + response.code() + " message..." + response.message() + " body..." + response.body());
                for (int i = 0; i < Objects.requireNonNull(results).getData().size(); i++) {
                    mViewModel.insert(formatEpisodeFromApi(results.getData().get(i)));
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseData> call, @NonNull Throwable t) {
                Log.e("ERROR--", t.getMessage());
            }
        });
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

            episode.setDate(reformattedDate);
            episode.setDuration(reformattedDuration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return episode;
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
        mBinding.navView.getHeaderView(0).setOnClickListener(this);
        setTextColorForMenuItem();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search_settings, menu);
        return true;
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
            mBinding.activityMainToolbar.setBackgroundColor(getColor(R.color.background));
            mNavController.navigate(R.id.nav_podcast);
        } else if (item.getItemId() == R.id.nav_topic) {
            mBinding.activityMainToolbar.setBackgroundColor(getColor(R.color.background));
            mNavController.navigate(R.id.nav_topic);
        } else if (item.getItemId() == R.id.nav_wiki) {
            mBinding.activityMainToolbar.setBackgroundColor(getColor(R.color.background));
            mNavController.navigate(R.id.nav_wiki);
        } else if (item.getItemId() == R.id.nav_login) {
            mBinding.activityMainToolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient_register, getTheme()));
            mNavController.navigate(R.id.nav_login);
        }

        if (item.getItemId() == R.id.nav_hint) {
            mPopupWindow = new CustomPopupWindow();
            mPopupWindow.showSortPopup(R.id.dialog_info_anonym_layout, R.layout.dialog_info_anonym, this);
            mPopupWindow.getContentView().findViewById(R.id.dialog_info_anonym_button).setOnClickListener(this);
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
            mBinding.activityMainToolbar.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.toolbar_gradient_register, getTheme()));
            mNavController.navigate(R.id.nav_register);
            mPopupWindow.dismiss();
        }

        mBinding.drawerLayout.closeDrawer(GravityCompat.START);
    }
}