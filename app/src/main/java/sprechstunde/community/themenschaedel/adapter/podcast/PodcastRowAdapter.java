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

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.view.podcast.PodcastFragmentDirections;

public class PodcastRowAdapter extends RecyclerView.Adapter<PodcastRowAdapter.ViewHolder> {

  private final Context mContext;
  private List<Episode> mEpisodes;
  private boolean mShowState;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    private final TextView title;
    private final ImageView icon;

    public ViewHolder(View view) {
      super(view);
      title = view.findViewById(R.id.row_title);
      icon = view.findViewById(R.id.row_icon);
    }

    public TextView getTitle() {
      return title;
    }
    public ImageView getIcon() { return icon; }

  }

  public PodcastRowAdapter(Context context, List<Episode> episodes) {
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
            .inflate(R.layout.list_item_podcast_row, viewGroup, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder viewHolder, final int position) {
    viewHolder.getTitle().setText(mEpisodes.get(position).getTitle());
    viewHolder.itemView.setOnClickListener(v -> {
      PodcastFragmentDirections.ActionPodcastToEpisode action = PodcastFragmentDirections.actionPodcastToEpisode();
      action.setEpisodeId(mEpisodes.get(position).getEpisodeNumber());
      Navigation.findNavController(v).navigate(action);
    });
    setUpState(viewHolder, position);
  }

  public List<Episode> getEpisodes() {
    return mEpisodes;
  }

  public void setEpisodes(List<Episode> episodes) {
    mEpisodes = episodes;
  }

  private void setUpState(ViewHolder viewHolder, int position)  {
    Episode episode = mEpisodes.get(position);
    ImageView icon = viewHolder.getIcon();
    icon.setVisibility(View.GONE);
    if(isShowState()) {
      icon.setVisibility(View.VISIBLE);
      Drawable stateDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_open);
      if(episode.getVerified()) {
        icon.setVisibility(View.GONE);
      } else if (!episode.getVerified() && episode.isClaimed()) {
        stateDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_unverified);
        icon.setBackground(stateDrawable);
      } else if (episode.isClaimed()) {
        stateDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_claimed);
        icon.setBackground(stateDrawable);
      }
      icon.setBackground(stateDrawable);
    }
  }

  @Override
  public int getItemCount() {
    return mEpisodes.size();
  }
}
