package sprechstunde.community.themenschaedel.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

@Dao
public interface SubtopicDAO {

    @Insert
    void insert(Subtopic topic);

    @Update
    void update(Subtopic topic);

    @Delete
    void delete(Subtopic topic);

    @Query("DELETE FROM subtopic_table")
    void deleteAllSubtopics();

    @Query("SELECT * FROM subtopic_table ORDER BY name ASC")
    LiveData<List<Subtopic>> getAllSubtopics();

    @Query("SELECT * FROM subtopic_table WHERE topic_id = :topic")
    LiveData<List<Subtopic>> getAllSubtopicsFrom(int topic);
}
