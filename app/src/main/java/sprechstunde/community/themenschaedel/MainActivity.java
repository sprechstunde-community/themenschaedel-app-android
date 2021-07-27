package sprechstunde.community.themenschaedel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.databinding.ActivityMainBinding;
import sprechstunde.community.themenschaedel.model.Episode;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding mBinding;
    private DISPLAY mCurrentDisplay;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;
    private enum DISPLAY {
        CARDS,
        CELLS,
        ROWS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        setUpNavigation();
        setUpToolbar();
        setUpDrawer();

        mCurrentDisplay = DISPLAY.CARDS;
        mBinding.activityMainDisplay.setOnClickListener(this);
    }

    private void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        mNavController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void setUpToolbar() {
        Toolbar toolbar = mBinding.activityMainToolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration);
    }

    private void setUpDrawer() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mBinding.drawerLayout, R.string.open, R.string.close);
        mBinding.drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        NavigationUI.setupWithNavController(mBinding.navView, mNavController);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.menu_podcast, R.id.menu_topic, R.id.menu_wiki, R.id.menu_login, R.id.podcastCardFragment, R.id.podcastCellFragment, R.id.podcastRowFragment).setOpenableLayout(mBinding.drawerLayout).build();
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        mBinding.navView.setNavigationItemSelectedListener(this);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (mBinding.activityMainDisplay == v) {
            Drawable card = AppCompatResources.getDrawable(this, R.drawable.ic_view_cards);
            Drawable cell = AppCompatResources.getDrawable(this, R.drawable.ic_view_cells);
            Drawable row = AppCompatResources.getDrawable(this, R.drawable.ic_view_rows);
            getEpisodes();
            if (mCurrentDisplay == DISPLAY.CARDS) {
                mCurrentDisplay = DISPLAY.CELLS;
                mBinding.activityMainDisplay.setBackground(cell);
                mNavController.navigate(R.id.action_card_to_cell);
            } else if (mCurrentDisplay == DISPLAY.CELLS) {
                mCurrentDisplay = DISPLAY.ROWS;
                mBinding.activityMainDisplay.setBackground(row);
                mNavController.navigate(R.id.action_cell_to_row);
            } else {
                mCurrentDisplay = DISPLAY.CARDS;
                mBinding.activityMainDisplay.setBackground(card);
                mNavController.navigate(R.id.action_row_to_card);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (mBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            mBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getEpisodes() {
        Call<List<Episode>> call = ApiClient.getInstance().getMyApi().getEpisodes();
        call.enqueue(new Callback<List<Episode>>() {
            @Override
            public void onResponse(Call<List<Episode>> call, Response<List<Episode>> response) {
                List<Episode> episodes = response.body();
                String[] episodeList = new String[episodes.size()];

                for (int i = 0; i < episodes.size(); i++) {
                    episodeList[i] = episodes.get(i).getTitle();
                    Log.i("HELLO", episodes.get(i).getTitle());
                }
            }

            @Override
            public void onFailure(Call<List<Episode>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }

        });
    }
}