package sprechstunde.community.themenschaedel.view.topic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import sprechstunde.community.themenschaedel.databinding.FragmentBottomSheetDialogFilterBinding;

public class BottomSheetDialogFilterFragment extends BottomSheetDialogFragment {

    FragmentBottomSheetDialogFilterBinding mBinding;

    public BottomSheetDialogFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentBottomSheetDialogFilterBinding.inflate(inflater, container, false);
        mBinding.filterBoys.setChecked(true);
        mBinding.filterCommunity.setChecked(true);
        return mBinding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}