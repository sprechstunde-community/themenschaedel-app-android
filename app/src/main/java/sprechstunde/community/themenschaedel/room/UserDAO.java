package sprechstunde.community.themenschaedel.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.User;

@Dao
public interface UserDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("DELETE FROM user_table")
    void deleteAllHosts();

    @Query("SELECT * FROM host_table ORDER BY name ASC")
    LiveData<List<Host>> getAllHosts();
}
