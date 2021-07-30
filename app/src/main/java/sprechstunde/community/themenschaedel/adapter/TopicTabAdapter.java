package sprechstunde.community.themenschaedel.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import sprechstunde.community.themenschaedel.view.topic.DrawFragment;
import sprechstunde.community.themenschaedel.view.topic.ListFragment;

public class TopicTabAdapter extends FragmentStateAdapter {

    public TopicTabAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ListFragment();
                break;
            case 1:
                fragment = new DrawFragment();
                break;
        }
        return fragment;    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
