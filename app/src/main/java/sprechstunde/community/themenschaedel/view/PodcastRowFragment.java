package sprechstunde.community.themenschaedel.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import sprechstunde.community.themenschaedel.adapter.PodcastRowAdapter;
import sprechstunde.community.themenschaedel.data.Podcast;
import sprechstunde.community.themenschaedel.databinding.FragmentPodcastRowBinding;

public class PodcastRowFragment extends Fragment {

    FragmentPodcastRowBinding mBinding;

    public PodcastRowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       mBinding = FragmentPodcastRowBinding.inflate(inflater, container, false);
        View view = mBinding.getRoot();

        Podcast[] podcasts = {
                new Podcast("Biodesintegrierender Rollrasen des Todes","9. Mai 2021",220,"1h 5min"),
                new Podcast("Ich bin gekommen um zu kommen - Und das ziehen wir jetzt auch durch!", "5. Mai 2021", 219, "1h 1min"),
                new Podcast("Straße oder OnlyFans Account? Das ist hier die Frage!", "2. Mai 2021", 218, "1h 3min"),
                new Podcast("Einfach mal den Opa beim Zahnarzt zerstört!", "28. April 2021", 217, "59min"),
                new Podcast("Wir haben einfach zu wenig Sex miteinander!", "25. April 2021", 216, "58min"),
                new Podcast("Trocken rein drücken und hart werden lassen!", "21. April 2021", 215, "1h 14min")};

        PodcastRowAdapter adapter = new PodcastRowAdapter(podcasts);
        Objects.requireNonNull(mBinding.fragmentRowRecyclerview).setAdapter(adapter);
        mBinding.fragmentRowRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mBinding = null;
    }
}