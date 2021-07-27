package sprechstunde.community.themenschaedel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Episode;

public class PodcastCellAdapter extends RecyclerView.Adapter<PodcastCellAdapter.ViewHolder> {

    private final Context context;
    private final Episode[] mEpisodes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView date;
        private final TextView title;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);
            date = view.findViewById(R.id.cell_date);
            title = view.findViewById(R.id.cell_title);
            image = view.findViewById(R.id.cell_image);
        }

        public TextView getDate() {
            return date;
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImage() {
            return image;
        }
    }

    public PodcastCellAdapter(Context context, Episode[] episodes) {
        this.context = context;
        mEpisodes = episodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.listitem_podcast_cell, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getDate().setText(mEpisodes[position].getDate());
        viewHolder.getTitle().setText(mEpisodes[position].getTitle());
        viewHolder.getImage().setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_view_cards));
    }

    @Override
    public int getItemCount() {
        return mEpisodes.length;
    }

}
