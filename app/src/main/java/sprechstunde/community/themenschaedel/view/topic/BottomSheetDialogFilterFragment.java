package sprechstunde.community.themenschaedel.view.topic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import sprechstunde.community.themenschaedel.adapter.list.TopicAdapter;
import sprechstunde.community.themenschaedel.databinding.BottomSheetFilterTopicsBinding;

public class BottomSheetDialogFilterFragment extends BottomSheetDialogFragment implements CompoundButton.OnCheckedChangeListener {

    private BottomSheetFilterTopicsBinding mBinding;
    private TopicAdapter mTopicAdapter;
    private ProcessFilter mCallback;

    public interface ProcessFilter {
        void onProcessFilter(boolean showDetails);
    }

    public BottomSheetDialogFilterFragment() {
        // Required empty public constructor
    }

    public BottomSheetDialogFilterFragment(ProcessFilter callback, TopicAdapter adapter) {
        mCallback = callback;
        mTopicAdapter = adapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = BottomSheetFilterTopicsBinding.inflate(inflater, container, false);
        mBinding.filterBoys.setChecked(true);
        mBinding.filterCommunity.setChecked(true);
        mBinding.fragmentFilterSwitch.setOnCheckedChangeListener(this);

        if(mTopicAdapter != null)
        mBinding.fragmentFilterSwitch.setChecked(mTopicAdapter.isShowDetails());
        return mBinding.getRoot();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mCallback.onProcessFilter(isChecked);
        mTopicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}