package sprechstunde.community.themenschaedel.adapter.podcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.view.podcast.PodcastFragmentDirections;

public class PodcastCellAdapter extends RecyclerView.Adapter<PodcastCellAdapter.ViewHolder> {

    private final Context context;
    private List<Episode> mEpisodes;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView number;
        private final TextView title;
        private final ImageView image;

        public ViewHolder(View view) {
            super(view);
            number = view.findViewById(R.id.cell_number);
            title = view.findViewById(R.id.cell_title);
            image = view.findViewById(R.id.cell_image);
        }

        public TextView getNumber() {
            return number;
        }

        public TextView getTitle() {
            return title;
        }

        public ImageView getImage() {
            return image;
        }
    }

    public PodcastCellAdapter(Context context, List<Episode> episodes) {
        this.context = context;
        mEpisodes = episodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_podcast_cell, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String episode = context.getResources().getString(R.string.podcast_episode) + " " + mEpisodes.get(position).getNumber();
        viewHolder.getNumber().setText(episode);
        viewHolder.getTitle().setText(mEpisodes.get(position).getTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context).asBitmap()
                .load(mEpisodes.get(position).getImage())
                .apply(requestOptions)
                .transition(BitmapTransitionOptions.withCrossFade())
                .error(R.drawable.podcast_defauft_image)
                .into(viewHolder.getImage());
        viewHolder.itemView.setOnClickListener(v -> {
            PodcastFragmentDirections.ActionPodcastToEpisode action = PodcastFragmentDirections.actionPodcastToEpisode();
            action.setEpisodeId(mEpisodes.get(position).getId());
            Navigation.findNavController(v).navigate(action);
        });
    }

    public List<Episode> getEpisodes() {
        return mEpisodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        mEpisodes = episodes;
    }

    @Override
    public int getItemCount() {
        return mEpisodes.size();
    }

}
