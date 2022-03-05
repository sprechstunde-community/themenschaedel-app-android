package sprechstunde.community.themenschaedel.model.episode;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Host;

public class EpisodeWithHosts {

    @Embedded
    private Episode mEpisode;

    @Relation(
            parentColumn = "episodeNumber",
            entityColumn = "hostName",
            associateBy = @Junction(EpisodeHostCrossRef.class)
    )
    private List<Host> mHosts;

    public Episode getEpisode() {
        return mEpisode;
    }

    public void setEpisode(Episode episode) {
        mEpisode = episode;
    }

    public List<Host> getHosts() {
        return mHosts;
    }

    public void setHosts(List<Host> hosts) {
        mHosts = hosts;
    }
}
