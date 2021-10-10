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
        return "INSERT OR REPLACE INTO `topic_table` (`id`,`name`,`start`,`end`,`ad`,`community_contribution`,`hasSubTopics`,`episode_id`) VALUES (?,?,?,?,?,?,?,?)";
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
        final int _tmp;
        _tmp = value.getAd() ? 1 : 0;
        stmt.bindLong(5, _tmp);
        final int _tmp_1;
        _tmp_1 = value.getCommunityContribution() ? 1 : 0;
        stmt.bindLong(6, _tmp_1);
        final int _tmp_2;
        _tmp_2 = value.hasSubtopics() ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
        stmt.bindLong(8, value.getEpisode());
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
        return "UPDATE OR ABORT `topic_table` SET `id` = ?,`name` = ?,`start` = ?,`end` = ?,`ad` = ?,`community_contribution` = ?,`hasSubTopics` = ?,`episode_id` = ? WHERE `id` = ?";
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
        final int _tmp;
        _tmp = value.getAd() ? 1 : 0;
        stmt.bindLong(5, _tmp);
        final int _tmp_1;
        _tmp_1 = value.getCommunityContribution() ? 1 : 0;
        stmt.bindLong(6, _tmp_1);
        final int _tmp_2;
        _tmp_2 = value.hasSubtopics() ? 1 : 0;
        stmt.bindLong(7, _tmp_2);
        stmt.bindLong(8, value.getEpisode());
        stmt.bindLong(9, value.getId());
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
  public LiveData<Topic> getTopicById(final int topicId) {
    final String _sql = "SELECT * FROM topic_table WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, topicId);
    return __db.getInvalidationTracker().createLiveData(new String[]{"topic_table"}, false, new Callable<Topic>() {
      @Override
      public Topic call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMStart = CursorUtil.getColumnIndexOrThrow(_cursor, "start");
          final int _cursorIndexOfMEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "end");
          final int _cursorIndexOfMAd = CursorUtil.getColumnIndexOrThrow(_cursor, "ad");
          final int _cursorIndexOfMCommunityContribution = CursorUtil.getColumnIndexOrThrow(_cursor, "community_contribution");
          final int _cursorIndexOfMHasSubtopics = CursorUtil.getColumnIndexOrThrow(_cursor, "hasSubTopics");
          final int _cursorIndexOfMEpisode = CursorUtil.getColumnIndexOrThrow(_cursor, "episode_id");
          final Topic _result;
          if(_cursor.moveToFirst()) {
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
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _result = new Topic(_tmpMId,_tmpMName,_tmpMStart,_tmpMEnd,_tmpMAd,_tmpMCommunityContribution,_tmpMEpisode);
            final boolean _tmpMHasSubtopics;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMHasSubtopics);
            _tmpMHasSubtopics = _tmp_2 != 0;
            _result.setHasSubtopics(_tmpMHasSubtopics);
          } else {
            _result = null;
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
          final int _cursorIndexOfMHasSubtopics = CursorUtil.getColumnIndexOrThrow(_cursor, "hasSubTopics");
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
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item = new Topic(_tmpMId,_tmpMName,_tmpMStart,_tmpMEnd,_tmpMAd,_tmpMCommunityContribution,_tmpMEpisode);
            final boolean _tmpMHasSubtopics;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMHasSubtopics);
            _tmpMHasSubtopics = _tmp_2 != 0;
            _item.setHasSubtopics(_tmpMHasSubtopics);
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
          final int _cursorIndexOfMHasSubtopics = CursorUtil.getColumnIndexOrThrow(_cursor, "hasSubTopics");
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
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item = new Topic(_tmpMId,_tmpMName,_tmpMStart,_tmpMEnd,_tmpMAd,_tmpMCommunityContribution,_tmpMEpisode);
            final boolean _tmpMHasSubtopics;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMHasSubtopics);
            _tmpMHasSubtopics = _tmp_2 != 0;
            _item.setHasSubtopics(_tmpMHasSubtopics);
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
  public LiveData<List<Topic>> search(final String query) {
    final String _sql = "SELECT * FROM topic_table WHERE (topic_table.name LIKE '%' || ? || '%') OR (topic_table.episode_id LIKE '%' || ? || '%')";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
    _argIndex = 2;
    if (query == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, query);
    }
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
          final int _cursorIndexOfMHasSubtopics = CursorUtil.getColumnIndexOrThrow(_cursor, "hasSubTopics");
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
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item = new Topic(_tmpMId,_tmpMName,_tmpMStart,_tmpMEnd,_tmpMAd,_tmpMCommunityContribution,_tmpMEpisode);
            final boolean _tmpMHasSubtopics;
            final int _tmp_2;
            _tmp_2 = _cursor.getInt(_cursorIndexOfMHasSubtopics);
            _tmpMHasSubtopics = _tmp_2 != 0;
            _item.setHasSubtopics(_tmpMHasSubtopics);
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
