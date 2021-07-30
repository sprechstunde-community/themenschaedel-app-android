package sprechstunde.community.themenschaedel.view.topic;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.databinding.FragmentListBinding;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCardBinding;

public class ListFragment extends Fragment {

    FragmentListBinding mBinding;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = FragmentListBinding.inflate(inflater, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}