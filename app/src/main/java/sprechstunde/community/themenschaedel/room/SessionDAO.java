package sprechstunde.community.themenschaedel.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import sprechstunde.community.themenschaedel.model.SessionData;
import sprechstunde.community.themenschaedel.model.User;

@Dao
public interface SessionDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(SessionData sessionData);

    @Update
    void update(SessionData sessionData);

    @Delete
    void delete(SessionData sessionData);

    @Query("UPDATE session_table SET id = :id, username = :username, email = :email, role_id = :role_id")
    void insertUser(int id, String username, String email, int role_id);

    @Query("SELECT session_table.id, session_table.username, session_table.email, session_table.role_id FROM session_table")
    LiveData<User> getMyself();

    @Query("SELECT * FROM session_table")
    LiveData<SessionData> getSessionData();

    @Query("UPDATE session_table SET refresh_token= '', access_token = '', id = '', username = '', email = '', role_id = 0")
    void deleteToken();
}
