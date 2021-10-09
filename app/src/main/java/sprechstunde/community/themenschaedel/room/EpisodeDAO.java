package sprechstunde.community.themenschaedel.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Episode;

@Dao
public interface EpisodeDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Episode episode);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Episode> episodes);

    @Update
    void update(Episode episode);

    @Delete
    void delete(Episode episode);

    @Query("DELETE FROM episode_table")
    void deleteAllEpisodes();

    @Query("SELECT * FROM episode_table WHERE title = :title")
    LiveData<Episode> getEpisode(String title);

    @Query("SELECT * FROM episode_table WHERE id = :id")
    LiveData<Episode> getEpisode(int id);

    @Query("SELECT * FROM episode_table ORDER BY title ASC")
    LiveData<List<Episode>> getAllEpisodes();
}
