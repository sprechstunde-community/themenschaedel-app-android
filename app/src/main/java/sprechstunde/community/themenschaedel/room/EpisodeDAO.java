package sprechstunde.community.themenschaedel.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.model.episode.EpisodeHostCrossRef;
import sprechstunde.community.themenschaedel.model.episode.EpisodeWithHosts;
import sprechstunde.community.themenschaedel.model.episode.HostWithEpisodes;

@Dao
public interface EpisodeDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Episode episode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertEpisodeHostCrossRef(EpisodeHostCrossRef crossRef);

    @Update
    void update(Episode episode);

    @Delete
    void delete(Episode episode);

    @Query("DELETE FROM episode_table")
    void deleteAllEpisodes();

    @Query("SELECT * FROM episode_table WHERE title = :title")
    LiveData<Episode> getEpisode(String title);

    @Query("SELECT * FROM episode_table WHERE episodeNumber = :number")
    LiveData<Episode> getEpisodeByNumber(int number);

    @Query("SELECT * FROM episode_table ORDER BY title ASC")
    LiveData<List<Episode>> getAllEpisodes();

   //@Query(" SELECT * FROM episode_table JOIN episode_fts_table ON episode_table.id = episode_fts_table.id WHERE episode_fts_table.title MATCH :query")
    @Query("SELECT * FROM episode_table WHERE (episode_table.title LIKE '%' || :query || '%') OR (episode_table.episodeNumber LIKE '%' || :query || '%') ORDER BY episode_table.episodeNumber DESC")
    LiveData<List<Episode>> search(String query);

    @Transaction
    @Query("SELECT * FROM host_table WHERE hostName = :hostName")
    LiveData<HostWithEpisodes> getHostsFromEpisode (String hostName);

    @Transaction
    @Query("SELECT * FROM episode_table WHERE episodeNumber = :number")
    LiveData<EpisodeWithHosts> getEpisodeWithHosts(int number);
}
