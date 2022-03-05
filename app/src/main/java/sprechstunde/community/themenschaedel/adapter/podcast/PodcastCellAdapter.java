package sprechstunde.community.themenschaedel.adapter.podcast;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.view.podcast.PodcastFragmentDirections;

public class PodcastCellAdapter extends RecyclerView.Adapter<PodcastCellAdapter.ViewHolder> {

    private final Context mContext;
    private List<Episode> mEpisodes;
    private boolean mShowState;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView number;
        private final TextView title;
        private final ImageView image;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            number = view.findViewById(R.id.cell_number);
            title = view.findViewById(R.id.cell_title);
            image = view.findViewById(R.id.cell_image);
            icon = view.findViewById(R.id.cell_corner_icon);
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

        public ImageView getIcon() { return icon; }
    }

    public PodcastCellAdapter(Context context, List<Episode> episodes) {
        mContext = context;
        mEpisodes = episodes;
    }

    public boolean isShowState() {
        return mShowState;
    }

    public void setShowState(boolean showState) {
        mShowState = showState;
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
        String episode = mContext.getResources().getString(R.string.podcast_episode) + " " + mEpisodes.get(position).getEpisodeNumber();
        viewHolder.getNumber().setText(episode);
        viewHolder.getTitle().setText(mEpisodes.get(position).getTitle());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        setUpState(viewHolder, position);

        Glide.with(mContext).asBitmap()
                .load(mEpisodes.get(position).getImage())
                .apply(requestOptions)
                .transition(BitmapTransitionOptions.withCrossFade())
                .error(R.drawable.podcast_default_image)
                .into(viewHolder.getImage());
        viewHolder.itemView.setOnClickListener(v -> {
            PodcastFragmentDirections.ActionPodcastToEpisode action = PodcastFragmentDirections.actionPodcastToEpisode();
            action.setEpisodeId(mEpisodes.get(position).getEpisodeNumber());
            Navigation.findNavController(v).navigate(action);
        });
    }

    private void setUpState(ViewHolder viewHolder,int position)  {

        ImageView icon = viewHolder.getIcon();

        Episode episode = mEpisodes.get(position);
        icon.setVisibility(View.GONE);
        if(isShowState()) {
            icon.setVisibility(View.VISIBLE);
            Drawable stateDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_open);
            if(episode.getVerified()) {
                icon.setVisibility(View.GONE);
            } else if (episode.isClaimed()) {
                stateDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_claimed);
                icon.setBackground(stateDrawable);
            } else if (!episode.getVerified() && episode.isClaimed()) {
                stateDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_unverified);
                icon.setBackground(stateDrawable);
            }
            icon.setBackground(stateDrawable);
        }
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
