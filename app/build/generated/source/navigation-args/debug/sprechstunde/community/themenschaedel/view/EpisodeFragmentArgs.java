package sprechstunde.community.themenschaedel.view;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class EpisodeFragmentArgs implements NavArgs {
  private final HashMap arguments = new HashMap();

  private EpisodeFragmentArgs() {
  }

  @SuppressWarnings("unchecked")
  private EpisodeFragmentArgs(HashMap argumentsMap) {
    this.arguments.putAll(argumentsMap);
  }

  @NonNull
  @SuppressWarnings("unchecked")
  public static EpisodeFragmentArgs fromBundle(@NonNull Bundle bundle) {
    EpisodeFragmentArgs __result = new EpisodeFragmentArgs();
    bundle.setClassLoader(EpisodeFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("episodeId")) {
      int episodeId;
      episodeId = bundle.getInt("episodeId");
      __result.arguments.put("episodeId", episodeId);
    } else {
      __result.arguments.put("episodeId", 0);
    }
    return __result;
  }

  @SuppressWarnings("unchecked")
  public int getEpisodeId() {
    return (int) arguments.get("episodeId");
  }

  @SuppressWarnings("unchecked")
  @NonNull
  public Bundle toBundle() {
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
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    EpisodeFragmentArgs that = (EpisodeFragmentArgs) object;
    if (arguments.containsKey("episodeId") != that.arguments.containsKey("episodeId")) {
      return false;
    }
    if (getEpisodeId() != that.getEpisodeId()) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = 1;
    result = 31 * result + getEpisodeId();
    return result;
  }

  @Override
  public String toString() {
    return "EpisodeFragmentArgs{"
        + "episodeId=" + getEpisodeId()
        + "}";
  }

  public static class Builder {
    private final HashMap arguments = new HashMap();

    @SuppressWarnings("unchecked")
    public Builder(EpisodeFragmentArgs original) {
      this.arguments.putAll(original.arguments);
    }

    public Builder() {
    }

    @NonNull
    public EpisodeFragmentArgs build() {
      EpisodeFragmentArgs result = new EpisodeFragmentArgs(arguments);
      return result;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public Builder setEpisodeId(int episodeId) {
      this.arguments.put("episodeId", episodeId);
      return this;
    }

    @SuppressWarnings("unchecked")
    public int getEpisodeId() {
      return (int) arguments.get("episodeId");
    }
  }
}
