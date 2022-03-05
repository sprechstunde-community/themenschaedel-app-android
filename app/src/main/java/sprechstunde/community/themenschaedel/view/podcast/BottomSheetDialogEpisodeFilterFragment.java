package sprechstunde.community.themenschaedel.view.podcast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import sprechstunde.community.themenschaedel.adapter.podcast.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.databinding.BottomSheetFilterEpisodeBinding;

public class BottomSheetDialogEpisodeFilterFragment extends BottomSheetDialogFragment implements CompoundButton.OnCheckedChangeListener {

    private BottomSheetFilterEpisodeBinding mBinding;
    private PodcastCardAdapter mCardAdapter;
    private ProcessFilter mCallback;

    public interface ProcessFilter {
        void onProcessFilter(boolean showDetails);
    }

    public BottomSheetDialogEpisodeFilterFragment() {
        // Required empty public constructor
    }

    public BottomSheetDialogEpisodeFilterFragment(ProcessFilter callback) {
        mCallback = callback;
       // mCardAdapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = BottomSheetFilterEpisodeBinding.inflate(inflater, container, false);
        mBinding.fragmentFilterEpisodeSwitch.setOnCheckedChangeListener(this);

        //  mBinding.fragmentFilterEpisodeSwitch.setChecked(mCardAdapter.isShowState());
        return mBinding.getRoot();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mCallback.onProcessFilter(isChecked);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}