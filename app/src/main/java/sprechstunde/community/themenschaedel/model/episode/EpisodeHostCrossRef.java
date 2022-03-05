package sprechstunde.community.themenschaedel.model.episode;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"episodeNumber", "hostName"})
public class EpisodeHostCrossRef {

    @ColumnInfo(name = "episodeNumber")
    public int mEpisodeNumber;

    @ColumnInfo(name = "hostName")
    @NonNull
    public String mHostName;

    public EpisodeHostCrossRef() {

    }

    @Ignore
    public EpisodeHostCrossRef(int episodeNumber, @NonNull String hostName) {
        mEpisodeNumber = episodeNumber;
        mHostName = hostName;
    }
}
