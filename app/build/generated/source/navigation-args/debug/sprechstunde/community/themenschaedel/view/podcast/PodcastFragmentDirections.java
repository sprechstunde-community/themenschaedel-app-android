package sprechstunde.community.themenschaedel.view.podcast;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import sprechstunde.community.themenschaedel.R;

public class PodcastFragmentDirections {
  private PodcastFragmentDirections() {
  }

  @NonNull
  public static ActionPodcastToEpisode actionPodcastToEpisode() {
    return new ActionPodcastToEpisode();
  }

  public static class ActionPodcastToEpisode implements NavDirections {
    private final HashMap arguments = new HashMap();

    private ActionPodcastToEpisode() {
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public ActionPodcastToEpisode setEpisodeId(int episodeId) {
      this.arguments.put("episodeId", episodeId);
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle getArguments() {
      Bundle __result = new Bundle();
      if (arguments.containsKey("episodeId")) {
        int episodeId = (int) arguments.get("episodeId");
        __result.putInt("episodeId", episodeId);
      } else {
        __result.putInt("episodeId", 0);
      }
      return __result;
    }

    @Override
    public int getActionId() {
      return R.id.action_podcast_to_episode;
    }

    @SuppressWarnings("unchecked")
    public int getEpisodeId() {
      return (int) arguments.get("episodeId");
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionPodcastToEpisode that = (ActionPodcastToEpisode) object;
      if (arguments.containsKey("episodeId") != that.arguments.containsKey("episodeId")) {
        return false;
      }
      if (getEpisodeId() != that.getEpisodeId()) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = 1;
      result = 31 * result + getEpisodeId();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionPodcastToEpisode(actionId=" + getActionId() + "){"
          + "episodeId=" + getEpisodeId()
          + "}";
    }
  }
}
