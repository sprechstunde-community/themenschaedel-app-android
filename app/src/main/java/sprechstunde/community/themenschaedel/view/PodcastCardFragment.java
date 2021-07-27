package sprechstunde.community.themenschaedel.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import sprechstunde.community.themenschaedel.adapter.PodcastCardAdapter;
import sprechstunde.community.themenschaedel.data.Podcast;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastCardBinding;

public class PodcastCardFragment extends Fragment {

    private FragmentPodcastCardBinding mBinding;

    public PodcastCardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = FragmentPodcastCardBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();
        Podcast[] podcasts = {
                new Podcast("Biodesintegrierender Rollrasen des Todes","9. Mai 2021",220,"1h 5min"),
                new Podcast("Ich bin gekommen um zu kommen - Und das ziehen wir jetzt auch durch!", "5. Mai 2021", 219, "1h 1min"),
                new Podcast("Straße oder OnlyFans Account? Das ist hier die Frage!", "2. Mai 2021", 218, "1h 3min"),
                new Podcast("Einfach mal den Opa beim Zahnarzt zerstört!", "28. April 2021", 217, "59min"),
                new Podcast("Wir haben einfach zu wenig Sex miteinander!", "25. April 2021", 216, "58min"),
                new Podcast("Trocken rein drücken und hart werden lassen!", "21. April 2021", 215, "1h 14min")};

        PodcastCardAdapter adapter = new PodcastCardAdapter(getContext(), podcasts);
        Objects.requireNonNull(mBinding.fragmentCardGridview).setAdapter(adapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}