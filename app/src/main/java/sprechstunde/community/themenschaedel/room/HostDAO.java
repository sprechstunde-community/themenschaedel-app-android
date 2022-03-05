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

import sprechstunde.community.themenschaedel.model.Host;

@Dao
public interface HostDAO {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(Host host);

    @Update
    void update(Host host);

    @Delete
    void delete(Host host);

    @Query("DELETE FROM host_table")
    void deleteAllHosts();

    @Query("SELECT * FROM host_table ORDER BY hostName ASC")
    LiveData<List<Host>> getAllHosts();
}
