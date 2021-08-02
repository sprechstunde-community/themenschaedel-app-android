package sprechstunde.community.themenschaedel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Episode;

public class PodcastRowAdapter extends RecyclerView.Adapter<PodcastRowAdapter.ViewHolder> {

    private Episode[] mEpisodes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;

        public ViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.row_title);
        }

        public TextView getTitle() {
            return title;
        }
    }

    public PodcastRowAdapter(Episode[] episodes) {
        mEpisodes = episodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_podcast_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getTitle().setText(mEpisodes[position].getTitle());
    }

    @Override
    public int getItemCount() {
        return mEpisodes.length;
    }

}
