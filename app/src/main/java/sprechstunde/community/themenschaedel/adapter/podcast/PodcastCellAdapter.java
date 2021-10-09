package sprechstunde.community.themenschaedel.adapter.podcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
        viewHolder.getDate().setText(mEpisodes.get(position).getDate());
        viewHolder.getTitle().setText(mEpisodes.get(position).getTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));

        Glide.with(context)
                .load(mEpisodes.get(position).getImage())
                .apply(requestOptions)
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
