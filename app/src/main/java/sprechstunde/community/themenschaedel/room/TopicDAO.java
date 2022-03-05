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

import sprechstunde.community.themenschaedel.model.topic.Topic;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;

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

    @Transaction
    @Query("SELECT * FROM topic_table ORDER BY name ASC")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopics();

    @Transaction
    @Query("SELECT * FROM topic_table WHERE episode_id = :episode")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsFrom(int episode);

    @Query("SELECT * FROM topic_table WHERE episode_id = :episode")
    LiveData<List<Topic>> getAllTopicsFrom(int episode);

    @Query("SELECT * FROM topic_table WHERE topic_table.name LIKE '%' || :query || '%'")
    LiveData<List<Topic>> search(String query);

    @Transaction
    @Query("SELECT * FROM topic_table WHERE topic_table.ad = 0")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsAllWithoutAds();

    @Transaction
    @Query("SELECT * FROM topic_table WHERE topic_table.community_contribution = :community AND topic_table.ad = :ad")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsCommunityAndAds(int community, int ad);

    @Transaction
    @Query("SELECT * FROM topic_table WHERE topic_table.community_contribution = :community")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsCommunity(int community);

    @Transaction
    @Query("SELECT * FROM topic_table WHERE topic_table.ad = 1")
    LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsOnlyAds();
}
