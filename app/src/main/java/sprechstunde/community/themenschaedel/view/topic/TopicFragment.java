package sprechstunde.community.themenschaedel.view.topic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.TopicTabAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentTopicBinding;

public class TopicFragment extends Fragment {

    private FragmentTopicBinding mBinding;
    private TopicTabAdapter mTopicTabAdapter;

    public TopicFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentTopicBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        mTopicTabAdapter = new TopicTabAdapter(this);

        // Set up the ViewPager with the sections adapter.
        ViewPager2 viewPager = mBinding.fragmentTopicViewpager;
        viewPager.setAdapter(mTopicTabAdapter);

        new TabLayoutMediator(mBinding.fragmentTopicTabLayout, viewPager, (tab, position) -> {
            if(position == 0)
                tab.setText(R.string.list);
            else
                tab.setText(R.string.draw);
        }).attach();

        setHasOptionsMenu(true);
        Toolbar toolbar = requireActivity().findViewById(R.id.activity_main_toolbar);
        toolbar.setBackgroundColor(requireActivity().getColor(R.color.primaryColor));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}