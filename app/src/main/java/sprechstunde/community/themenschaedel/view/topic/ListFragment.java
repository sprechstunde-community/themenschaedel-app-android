package sprechstunde.community.themenschaedel.view.topic;

import static androidx.recyclerview.widget.ItemTouchHelper.RIGHT;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.chip.Chip;

import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.list.TopicAdapter;
import sprechstunde.community.themenschaedel.databinding.FragmentListBinding;
import sprechstunde.community.themenschaedel.listener.ParentChildFragmentListener;
import sprechstunde.community.themenschaedel.model.TopicWithSubtopic;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;
import sprechstunde.community.themenschaedel.view.CustomPopupWindow;

public class  ListFragment extends Fragment implements View.OnClickListener, BottomSheetDialogFilterFragment.ProcessFilter, SearchView.OnQueryTextListener  {

    private FragmentListBinding mBinding;
    private SharedPreferences mSharedPref;
    private int mPreSelectedChipId;
    private TopicAdapter mAdapter;
    private TopicViewModel mTopicViewModel;

    public ListFragment() {
        // Required empty public constructor
    }

    public TopicAdapter getAdapter() {
        return mAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        DividerItemDecoration decoration = new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL);
        mBinding.fragmentListRecylerview.addItemDecoration(decoration);

        mTopicViewModel = new ViewModelProvider(requireActivity()).get(TopicViewModel.class);
        mTopicViewModel.getAllTopicsWithSubtopics().observe(getViewLifecycleOwner(), topics -> {
            Collections.sort(topics, (a, b) -> a.getTopic().getName().compareTo(b.getTopic().getName()));
            mAdapter = new TopicAdapter(topics, (MainActivity) requireActivity());
            Objects.requireNonNull(mBinding.fragmentListRecylerview).setAdapter(mAdapter);
            mBinding.fragmentListRecylerview.setLayoutManager(new LinearLayoutManager(getContext()));

            mSharedPref = requireActivity().getPreferences(Context.MODE_PRIVATE);
            getFilterTypeFromSharedPreferences();
        });

        mBinding.fragmentListDetails.setOnClickListener(this);
        mBinding.filterName.setOnClickListener(this);
        mBinding.filterNumber.setOnClickListener(this);
        mPreSelectedChipId = mBinding.filterNumber.getId();

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();

                int episodeId = mAdapter.getTopics().get(position).getTopic().getEpisode();
                TopicFragmentDirections.ActionNavTopicToNavEpisode action = TopicFragmentDirections.actionNavTopicToNavEpisode();
                action.setEpisodeId(episodeId);
                Navigation.findNavController(viewHolder.itemView).navigate(action);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX,
                        dY, actionState, isCurrentlyActive);
                View itemView = viewHolder.itemView;
                int backgroundCornerOffset = 20;
                ColorDrawable background =  new ColorDrawable(getResources().getColor(R.color.primaryColor, requireContext().getTheme()));

                if (dX > 0) { // Swiping to the right
                    background.setBounds(itemView.getLeft(), itemView.getTop(),
                            itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                            itemView.getBottom());
                } else { // view is unSwiped
                    background.setBounds(0, 0, 0, 0);
                }
                background.draw(c);
                drawText(getString(R.string.dialog_to_episode_text), c, background);
            }

            private void drawText(String text, Canvas c, ColorDrawable background) {
                Paint p = new Paint();
                float textSize = 40;
                p.setColor(Color.WHITE);
                p.setAntiAlias(true);
                p.setTextSize(textSize);

                float textWidth = p.measureText(text);
                c.drawText(text, background.getBounds().centerX() - (textWidth / 2), background.getBounds().centerY() + (textSize / 2), p);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mBinding.fragmentListRecylerview);

        return view;
    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
        requireActivity().getMenuInflater().inflate(R.menu.menu_search_info, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);
        /* searchView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {

            @Override
            public void onViewDetachedFromWindow(View arg0) {
                mTopicViewModel.getAllTopicsWithSubtopics().observe(getViewLifecycleOwner(), topics -> {
                    mAdapter.setTopics(topics);
                    mAdapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onViewAttachedToWindow(View arg0) {
                // search was opened
            }
        }); */
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.menu_info) {
            CustomPopupWindow popupWindow = new CustomPopupWindow();
            popupWindow.showSortPopup(R.id.dialog_info_topic_layout, R.layout.dialog_info_episode_types, requireActivity());
        }

        NavHostFragment navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_main);
        NavController nvController = Objects.requireNonNull(navHostFragment).getNavController();
        return  NavigationUI.onNavDestinationSelected(item, nvController);
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
            mTopicViewModel.searchForSubtopics(query).observe(this, topicWithSubtopics -> {
                mAdapter.setTopics(topicWithSubtopics);
                mAdapter.notifyDataSetChanged();

            });
        }
    }

    private void fromASCToDESC(Chip chip, boolean wasAlreadySelected, ParentChildFragmentListener.SORTED_BY up, ParentChildFragmentListener.SORTED_BY down) {
        String tag = (String) chip.getTag();
        Drawable upIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_up);
        Drawable downIcon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_arrow_down);

        if (tag.equals("ASC") && wasAlreadySelected || (tag.equals("DESC") && !wasAlreadySelected)) { // if already selected -> make ASC to DESC or if was not selected and is DESC
            sortList(down);
            chip.setTag("DESC");
            chip.setChipIcon(downIcon);
            saveFilterTypeToSharedPreferences(down);
        } else if (tag.equals("DESC") || tag.equals("ASC"))  { // if already selected -> make DESC to ASC or if was not selected and is ASC
            sortList(up);
            chip.setTag("ASC");
            chip.setChipIcon(upIcon);
            saveFilterTypeToSharedPreferences(up);
        }
    }

    private void saveFilterTypeToSharedPreferences(ParentChildFragmentListener.SORTED_BY filter) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(getString(R.string.saved_filter_type_list), filter.ordinal());
        editor.apply();
    }

    private void getFilterTypeFromSharedPreferences() {
        int defaultValue = ParentChildFragmentListener.SORTED_BY.NUMBER_DOWN.ordinal();
        int displayType = mSharedPref.getInt(getString(R.string.saved_filter_type_list), defaultValue);
        sortList(ParentChildFragmentListener.SORTED_BY.values()[displayType]);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }

    @Override
    public void onClick(View v) {
        if(v == mBinding.fragmentListDetails) {
            final BottomSheetDialogFilterFragment bottomSheetDialog = new BottomSheetDialogFilterFragment(this, mAdapter);
            bottomSheetDialog.show(getChildFragmentManager(), "OpenFilterBottomSheet");
        } else if (mBinding.filterNumber == v) {
            fromASCToDESC(mBinding.filterNumber, v.getId() == mPreSelectedChipId, ParentChildFragmentListener.SORTED_BY.NUMBER_UP, ParentChildFragmentListener.SORTED_BY.NUMBER_DOWN);
            mPreSelectedChipId = mBinding.filterNumber.getId();
        } else if (mBinding.filterName == v) {
            fromASCToDESC(mBinding.filterName, v.getId() == mPreSelectedChipId, ParentChildFragmentListener.SORTED_BY.TITLE_UP, ParentChildFragmentListener.SORTED_BY.TITLE_DOWN);
            mPreSelectedChipId = mBinding.filterName.getId();
        }
    }

    private void sortList(ParentChildFragmentListener.SORTED_BY sortedBy) {
        TopicAdapter adapter = (TopicAdapter) mBinding.fragmentListRecylerview.getAdapter();
        List<TopicWithSubtopic> topics = Objects.requireNonNull(adapter).getTopics();

        switch (sortedBy) {
            case NUMBER_UP:
            default: {
                Collections.sort(topics, (a,b) -> Integer.compare(a.getTopic().getEpisode(), b.getTopic().getEpisode()));
            } break;
            case NUMBER_DOWN: {
                Collections.sort(topics, (a,b) -> Integer.compare(b.getTopic().getEpisode(), a.getTopic().getEpisode()));
            } break;
            case TITLE_UP: {
                Collections.sort(topics, (a,b) -> {
                    Collator germanCollator = Collator.getInstance(Locale.GERMAN);
                    germanCollator.setStrength(Collator.PRIMARY);
                    return germanCollator.compare(a.getTopic().getName(), b.getTopic().getName());
                });
            } break;
            case TITLE_DOWN: {
                Collections.sort(topics, (a,b) -> {
                    Collator germanCollator = Collator.getInstance(Locale.GERMAN);
                    germanCollator.setStrength(Collator.PRIMARY);
                    return germanCollator.compare(b.getTopic().getName(), a.getTopic().getName());
                });
            } break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onProcessFilter(boolean showDetails) {
        mAdapter.setShowDetails(showDetails);
        mAdapter.notifyDataSetChanged();
    }
}