package sprechstunde.community.themenschaedel.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.Objects;

import sprechstunde.community.themenschaedel.view.topic.DrawFragment;
import sprechstunde.community.themenschaedel.view.topic.ListFragment;

public class TopicTabAdapter extends FragmentStateAdapter {

    private final ListFragment mListFragment = new ListFragment();
    private final DrawFragment mDrawFragment = new DrawFragment();

    public TopicTabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    public ListFragment getListFragment() {
        return mListFragment;
    }

    public DrawFragment getDrawFragment() {
        return mDrawFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = mListFragment;
                break;
            case 1:
                fragment = mDrawFragment;
                break;
        }
        return Objects.requireNonNull(fragment);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
