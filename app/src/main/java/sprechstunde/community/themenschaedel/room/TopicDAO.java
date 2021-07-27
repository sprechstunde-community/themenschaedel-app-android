package sprechstunde.community.themenschaedel.room;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Topic;

public interface TopicDAO {

    @Insert
    void insert(Topic topic);

    @Update
    void update(Topic topic);

    @Delete
    void delete(Topic topic);

    @Query("DELETE FROM topic_table")
    void deleteAllTopics();

    @Query("SELECT * FROM topic_table ORDER BY name ASC")
    LiveData<List<Topic>> getAllTopics();

    @Query("SELECT * FROM topic_table WHERE episode = :episode")
    LiveData<List<Topic>> getAllTopicsFrom(int episode);
}
