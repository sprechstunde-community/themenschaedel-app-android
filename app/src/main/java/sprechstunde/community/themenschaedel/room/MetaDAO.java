package sprechstunde.community.themenschaedel.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import sprechstunde.community.themenschaedel.model.ResponseMeta;

@Dao
public interface MetaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<ResponseMeta> remoteKey);

    @Query("SELECT * FROM data_remote_keys WHERE mCurrentPage = :currentPage")
    ResponseMeta remoteKeyBy(int currentPage);

    @Query("DELETE FROM data_remote_keys")
    void clearRemoteKeys();

}
