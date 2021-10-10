package sprechstunde.community.themenschaedel.view.topic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayoutMediator;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.TopicTabAdapter;
import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.databinding.FragmentTopicBinding;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class TopicFragment extends Fragment implements SearchView.OnQueryTextListener {

    private FragmentTopicBinding mBinding;
    private TopicTabAdapter mTopicTabAdapter;
    private TopicViewModel mViewModel;

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
        mViewModel = new ViewModelProvider(this).get(TopicViewModel.class);
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
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        requireActivity().getMenuInflater().inflate(R.menu.menu_search_info, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View arg0) {
                mViewModel.getAllTopics().observe(getViewLifecycleOwner(), topics -> {
                    mTopicTabAdapter.getListFragment().getAdapter().setTopics(topics);
                    mTopicTabAdapter.getListFragment().getAdapter().notifyDataSetChanged();
                });
            }

            @Override
            public void onViewAttachedToWindow(View arg0) {
                // search was opened
            }
        });
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        onSearch(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        onSearch(query);
        return false;
    }

    private void onSearch(String query) {
        if(!query.equals("")) {
            mViewModel.searchForTopics(query).observe(this, topics -> {
                mTopicTabAdapter.getListFragment().getAdapter().setTopics(topics);
                mTopicTabAdapter.getListFragment().getAdapter().notifyDataSetChanged();
            });

          /*  mViewModel.searchForSubtopics(query).observe(this, subtopics -> {

                for(Subtopic subtopic : subtopics){
                    mViewModel.getTopicById(subtopic.getTopicId());
                }

                mTopicTabAdapter.getListFragment().getAdapter().setTopics(subtopics);
                mTopicTabAdapter.getListFragment().getAdapter().notifyDataSetChanged();
            });*/
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}