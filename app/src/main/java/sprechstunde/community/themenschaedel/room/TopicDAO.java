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
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.model.TopicWithSubtopic;

@Dao
public interface TopicDAO {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Topic topic);

    @Update
    void update(Topic topic);

    @Delete
    void delete(Topic topic);

    @Query("DELETE FROM topic_table")
    void deleteAllTopics();

    @Query("SELECT * FROM topic_table WHERE id = :topicId")
    LiveData<Topic> getTopicById(int topicId);

    @Query("SELECT * FROM topic_table ORDER BY name ASC")
    LiveData<List<Topic>> getAllTopics();

    @Query("SELECT * FROM topic_table ORDER BY name ASC")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopics();

    @Query("SELECT * FROM topic_table WHERE episode_id = :episode")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsFrom(int episode);

    @Query("SELECT * FROM topic_table WHERE episode_id = :episode")
    LiveData<List<Topic>> getAllTopicsFrom(int episode);

    @Query("SELECT * FROM topic_table WHERE topic_table.name LIKE '%' || :query || '%'")
    LiveData<List<Topic>> search(String query);

}
