package sprechstunde.community.themenschaedel.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.databinding.ActivityTopicBinding;

public class TopicActivity extends AppCompatActivity {

    ActivityTopicBinding mBinding;
    private NavController mNavController;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityTopicBinding.inflate(getLayoutInflater());
        View view = mBinding.getRoot();
        setContentView(view);

        setUpNavigation();
        setUpToolbar();
        setUpDrawer();

    }

    protected void setUpNavigation() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_topic);
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_podcast, R.id.nav_topic, R.id.nav_wiki, R.id.nav_login, R.id.nav_list, R.id.nav_draw).setOpenableLayout(mBinding.drawerLayout).build();
        mNavController = Objects.requireNonNull(navHostFragment).getNavController();
    }

    private void setUpToolbar() {
        Toolbar toolbar = mBinding.activityTopicToolbar;
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
    }

    private void setUpDrawer() {
        NavigationUI.setupActionBarWithNavController(this, mNavController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(mBinding.navView, mNavController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(mNavController, mAppBarConfiguration);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_search_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, mNavController) || super.onOptionsItemSelected(item);
    }
}