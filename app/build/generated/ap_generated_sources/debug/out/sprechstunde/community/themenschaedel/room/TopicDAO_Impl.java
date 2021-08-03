package sprechstunde.community.themenschaedel.room;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import sprechstunde.community.themenschaedel.model.Topic;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TopicDAO_Impl implements TopicDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Topic> __insertionAdapterOfTopic;

  private final EntityDeletionOrUpdateAdapter<Topic> __deletionAdapterOfTopic;

  private final EntityDeletionOrUpdateAdapter<Topic> __updateAdapterOfTopic;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTopics;

  public TopicDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTopic = new EntityInsertionAdapter<Topic>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `topic_table` (`id`,`name`,`start`,`end`,`ad`,`community_contribution`,`episode_id`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Topic value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getStart());
        stmt.bindLong(4, value.getEnd());
        stmt.bindLong(5, value.getAd());
        stmt.bindLong(6, value.getCommunityContribution());
        stmt.bindLong(7, value.getEpisode());
      }
    };
    this.__deletionAdapterOfTopic = new EntityDeletionOrUpdateAdapter<Topic>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `topic_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Topic value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfTopic = new EntityDeletionOrUpdateAdapter<Topic>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `topic_table` SET `id` = ?,`name` = ?,`start` = ?,`end` = ?,`ad` = ?,`community_contribution` = ?,`episode_id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Topic value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getStart());
        stmt.bindLong(4, value.getEnd());
        stmt.bindLong(5, value.getAd());
        stmt.bindLong(6, value.getCommunityContribution());
        stmt.bindLong(7, value.getEpisode());
        stmt.bindLong(8, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllTopics = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM topic_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Topic topic) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfTopic.insert(topic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Topic topic) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfTopic.handle(topic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Topic topic) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfTopic.handle(topic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllTopics() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTopics.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllTopics.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Topic>> getAllTopics() {
    final String _sql = "SELECT * FROM topic_table ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"topic_table"}, false, new Callable<List<Topic>>() {
      @Override
      public List<Topic> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMStart = CursorUtil.getColumnIndexOrThrow(_cursor, "start");
          final int _cursorIndexOfMEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "end");
          final int _cursorIndexOfMAd = CursorUtil.getColumnIndexOrThrow(_cursor, "ad");
          final int _cursorIndexOfMCommunityContribution = CursorUtil.getColumnIndexOrThrow(_cursor, "community_contribution");
          final int _cursorIndexOfMEpisode = CursorUtil.getColumnIndexOrThrow(_cursor, "episode_id");
          final List<Topic> _result = new ArrayList<Topic>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Topic _item;
            final int _tmpMId;
            _tmpMId = _cursor.getInt(_cursorIndexOfMId);
            final String _tmpMName;
            if (_cursor.isNull(_cursorIndexOfMName)) {
              _tmpMName = null;
            } else {
              _tmpMName = _cursor.getString(_cursorIndexOfMName);
            }
            final int _tmpMStart;
            _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
            final int _tmpMEnd;
            _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
            final int _tmpMAd;
            _tmpMAd = _cursor.getInt(_cursorIndexOfMAd);
            final int _tmpMCommunityContribution;
            _tmpMCommunityContribution = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item = new Topic(_tmpMId,_tmpMName,_tmpMStart,_tmpMEnd,_tmpMAd,_tmpMCommunityContribution,_tmpMEpisode);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Topic>> getAllTopicsFrom(final int episode) {
    final String _sql = "SELECT * FROM topic_table WHERE episode_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episode);
    return __db.getInvalidationTracker().createLiveData(new String[]{"topic_table"}, false, new Callable<List<Topic>>() {
      @Override
      public List<Topic> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMStart = CursorUtil.getColumnIndexOrThrow(_cursor, "start");
          final int _cursorIndexOfMEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "end");
          final int _cursorIndexOfMAd = CursorUtil.getColumnIndexOrThrow(_cursor, "ad");
          final int _cursorIndexOfMCommunityContribution = CursorUtil.getColumnIndexOrThrow(_cursor, "community_contribution");
          final int _cursorIndexOfMEpisode = CursorUtil.getColumnIndexOrThrow(_cursor, "episode_id");
          final List<Topic> _result = new ArrayList<Topic>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Topic _item;
            final int _tmpMId;
            _tmpMId = _cursor.getInt(_cursorIndexOfMId);
            final String _tmpMName;
            if (_cursor.isNull(_cursorIndexOfMName)) {
              _tmpMName = null;
            } else {
              _tmpMName = _cursor.getString(_cursorIndexOfMName);
            }
            final int _tmpMStart;
            _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
            final int _tmpMEnd;
            _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
            final int _tmpMAd;
            _tmpMAd = _cursor.getInt(_cursorIndexOfMAd);
            final int _tmpMCommunityContribution;
            _tmpMCommunityContribution = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item = new Topic(_tmpMId,_tmpMName,_tmpMStart,_tmpMEnd,_tmpMAd,_tmpMCommunityContribution,_tmpMEpisode);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
