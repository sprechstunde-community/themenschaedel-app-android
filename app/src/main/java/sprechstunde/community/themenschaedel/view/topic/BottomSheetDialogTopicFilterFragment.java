package sprechstunde.community.themenschaedel.view.topic;

import android.os.Bundle;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import sprechstunde.community.themenschaedel.adapter.list.TopicAdapter;
import sprechstunde.community.themenschaedel.databinding.BottomSheetFilterTopicsBinding;

public class BottomSheetDialogTopicFilterFragment extends BottomSheetDialogFragment implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private BottomSheetFilterTopicsBinding mBinding;
    private TopicAdapter mTopicAdapter;
    private ProcessFilter mCallback;

    private boolean mShowDetails;
    private boolean mFromCommunity;
    private boolean mFromBoys;
    private boolean mIsAd;


    public interface ProcessFilter {
        void onProcessFilter(boolean showDetails, boolean fromCommunity, boolean fromBoys, boolean ad);
    }

    public BottomSheetDialogTopicFilterFragment() {
        // Required empty public constructor
    }

    public BottomSheetDialogTopicFilterFragment(ProcessFilter callback, TopicAdapter adapter) {
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
        mBinding.filterAd.setOnClickListener(this);
        mBinding.filterCommunity.setOnClickListener(this);
        mBinding.filterBoys.setOnClickListener(this);

        if(mTopicAdapter != null)
        mBinding.fragmentFilterSwitch.setChecked(mTopicAdapter.isShowDetails());
        return mBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if(view == mBinding.filterCommunity || view == mBinding.filterBoys || view == mBinding.filterAd) {
            Chip communityChip = mBinding.filterCommunity;
            mFromCommunity = communityChip.isChecked();

            Chip boysChip = mBinding.filterBoys;
            mFromBoys = boysChip.isChecked();

            Chip adChip = mBinding.filterAd;
            mIsAd = adChip.isChecked();

            mCallback.onProcessFilter(mShowDetails, mFromCommunity, mFromBoys, mIsAd);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        mShowDetails = isChecked;
        mCallback.onProcessFilter(isChecked, mFromCommunity, mFromBoys, mIsAd);
        mTopicAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}