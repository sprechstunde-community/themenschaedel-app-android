package sprechstunde.community.themenschaedel.model.episode;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Host;

public class HostWithEpisodes {

    @Embedded
    private Host mHost;

    @Relation(
            parentColumn = "hostName",
            entityColumn = "episodeNumber",
            associateBy = @Junction(EpisodeHostCrossRef.class)
    )
    private List<Episode> mEpisodes;

    public Host getHost() {
        return mHost;
    }

    public void setHost(Host host) {
        mHost = host;
    }

    public List<Episode> getEpisodes() {
        return mEpisodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        mEpisodes = episodes;
    }
}
