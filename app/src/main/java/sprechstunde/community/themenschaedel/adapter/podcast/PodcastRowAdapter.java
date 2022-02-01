package sprechstunde.community.themenschaedel.adapter.podcast;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.view.podcast.PodcastFragmentDirections;

public class PodcastRowAdapter extends RecyclerView.Adapter<PodcastRowAdapter.ViewHolder> {

  private List<Episode> mEpisodes;

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

  public PodcastRowAdapter(List<Episode> episodes) {
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
    viewHolder.getTitle().setText(mEpisodes.get(position).getTitle());
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
