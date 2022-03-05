package sprechstunde.community.themenschaedel.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Objects;

import sprechstunde.community.themenschaedel.view.profile.ProfileFavoritesFragment;
import sprechstunde.community.themenschaedel.view.profile.ProfileRandomFragment;
import sprechstunde.community.themenschaedel.view.profile.ProfileSettingsFragment;
import sprechstunde.community.themenschaedel.view.topic.DrawFragment;

public class ProfileTabAdapter extends FragmentStateAdapter {

  private final ProfileSettingsFragment mSettingsFragment = new ProfileSettingsFragment();
  private final ProfileRandomFragment mRandomFragment = new ProfileRandomFragment();
  private final ProfileFavoritesFragment mFavoritesFragment = new ProfileFavoritesFragment();

  public ProfileTabAdapter(@NonNull Fragment fragment) {
    super(fragment);
  }

  public ProfileSettingsFragment getProfileSettingsFragment() {
    return mSettingsFragment;
  }

  public ProfileRandomFragment getProfileRandomFragment() {
    return mRandomFragment;
  }

  public ProfileFavoritesFragment getProfileFavoritesFragment() {
    return mFavoritesFragment;
  }


  @NonNull
  @Override
  public Fragment createFragment(int position) {
    Fragment fragment = null;
    switch (position) {
      case 0:
        fragment = mSettingsFragment;
        break;
      case 1:
        fragment = mFavoritesFragment;
        break;
      case 2:
        fragment = mRandomFragment;
        break;
    }
    return Objects.requireNonNull(fragment);
  }

  @Override
  public int getItemCount() {
    return 3;
  }
}