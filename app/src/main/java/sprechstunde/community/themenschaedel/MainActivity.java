package sprechstunde.community.themenschaedel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import sprechstunde.community.themenschaedel.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener, NavController.OnDestinationChangedListener {

    private ActivityMainBinding mBinding;
    private DISPLAY mCurrentDisplay;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        int id = destination.getId();

       if (id == R.id.nav_podcast) {
           Toast.makeText(this, "Podcast clicked", Toast.LENGTH_SHORT).show();
       } else if(id == R.id.nav_topic) {
           Toast.makeText(this, "Topic clicked", Toast.LENGTH_SHORT).show();
       } else if(id == R.id.nav_wiki) {
            Toast.makeText(this, "Wiki clicked", Toast.LENGTH_SHORT).show();
       } else if(id == R.id.nav_login) {
           Toast.makeText(this, "LogIn clicked", Toast.LENGTH_SHORT).show();
       }
    }

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
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_podcast);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_podcast, R.id.nav_topic, R.id.nav_wiki, R.id.nav_login, R.id.card, R.id.cell, R.id.row).setOpenableLayout(mBinding.drawerLayout).build();
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
        if (item.getItemId() == R.id.nav_podcast) {
            Toast.makeText(this, "Podcast clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if(item.getItemId() == R.id.nav_topic) {
            Toast.makeText(this, "Topic clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if(item.getItemId() == R.id.nav_wiki) {
            Toast.makeText(this, "Wiki clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if(item.getItemId() == R.id.nav_login) {
            Toast.makeText(this, "LogIn clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        if (mBinding.activityMainDisplay == v) {
            Drawable card = AppCompatResources.getDrawable(this, R.drawable.ic_view_cards);
            Drawable cell = AppCompatResources.getDrawable(this, R.drawable.ic_view_cells);
            Drawable row = AppCompatResources.getDrawable(this, R.drawable.ic_view_rows);

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
}