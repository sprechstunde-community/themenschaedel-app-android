package sprechstunde.community.themenschaedel.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RewriteQueriesToDropUnusedColumns;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;

@Dao
public interface SubtopicDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Subtopic topic);

    @Update
    void update(Subtopic topic);

    @Delete
    void delete(Subtopic topic);

    @Query("DELETE FROM subtopic_table")
    void deleteAllSubtopics();

    @Query("SELECT * FROM subtopic_table ORDER BY name ASC")
    LiveData<List<Subtopic>> getAllSubtopics();

    @Query("SELECT * FROM subtopic_table WHERE id_topic = :topic")
    LiveData<List<Subtopic>> getAllSubtopicsFrom(int topic);

    @RewriteQueriesToDropUnusedColumns
    @Transaction
    @Query("SELECT DISTINCT topic_table.id, topic_table.name, topic_table.episode_id FROM topic_table LEFT JOIN subtopic_table on topic_table.id = subtopic_table.id_topic WHERE topic_table.name LIKE '%' || :query || '%' OR subtopic_table.name LIKE '%' || :query || '%'")
    LiveData<List<TopicWithSubtopic>> search(String query);
}
