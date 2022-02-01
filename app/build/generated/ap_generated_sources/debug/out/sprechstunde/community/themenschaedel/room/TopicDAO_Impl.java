package sprechstunde.community.themenschaedel.room;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;

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
        return "INSERT OR REPLACE INTO `topic_table` (`id`,`name`,`start`,`end`,`ad`,`community_contribution`,`episode_id`) VALUES (?,?,?,?,?,?,?)";
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
        final int _tmp;
        _tmp = value.getAd() ? 1 : 0;
        stmt.bindLong(5, _tmp);
        final int _tmp_1;
        _tmp_1 = value.getCommunityContribution() ? 1 : 0;
        stmt.bindLong(6, _tmp_1);
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
            _result = new Topic(_tmpMId,_tmpMName);
            final int _tmpMStart;
            _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
            _result.setStart(_tmpMStart);
            final int _tmpMEnd;
            _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
            _result.setEnd(_tmpMEnd);
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            _result.setAd(_tmpMAd);
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            _result.setCommunityContribution(_tmpMCommunityContribution);
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _result.setEpisode(_tmpMEpisode);
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
            _item = new Topic(_tmpMId,_tmpMName);
            final int _tmpMStart;
            _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
            _item.setStart(_tmpMStart);
            final int _tmpMEnd;
            _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
            _item.setEnd(_tmpMEnd);
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            _item.setAd(_tmpMAd);
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            _item.setCommunityContribution(_tmpMCommunityContribution);
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item.setEpisode(_tmpMEpisode);
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
  public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopics() {
    final String _sql = "SELECT * FROM topic_table ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"subtopic_table","topic_table"}, true, new Callable<List<TopicWithSubtopic>>() {
      @Override
      public List<TopicWithSubtopic> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfMStart = CursorUtil.getColumnIndexOrThrow(_cursor, "start");
            final int _cursorIndexOfMEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "end");
            final int _cursorIndexOfMAd = CursorUtil.getColumnIndexOrThrow(_cursor, "ad");
            final int _cursorIndexOfMCommunityContribution = CursorUtil.getColumnIndexOrThrow(_cursor, "community_contribution");
            final int _cursorIndexOfMEpisode = CursorUtil.getColumnIndexOrThrow(_cursor, "episode_id");
            final LongSparseArray<ArrayList<Subtopic>> _collectionMSubtopics = new LongSparseArray<ArrayList<Subtopic>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfMId)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfMId);
                ArrayList<Subtopic> _tmpMSubtopicsCollection = _collectionMSubtopics.get(_tmpKey);
                if (_tmpMSubtopicsCollection == null) {
                  _tmpMSubtopicsCollection = new ArrayList<Subtopic>();
                  _collectionMSubtopics.put(_tmpKey, _tmpMSubtopicsCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipsubtopicTableAssprechstundeCommunityThemenschaedelModelTopicSubtopic(_collectionMSubtopics);
            final List<TopicWithSubtopic> _result = new ArrayList<TopicWithSubtopic>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final TopicWithSubtopic _item;
              final Topic _tmpMTopic;
              if (! (_cursor.isNull(_cursorIndexOfMId) && _cursor.isNull(_cursorIndexOfMName) && _cursor.isNull(_cursorIndexOfMStart) && _cursor.isNull(_cursorIndexOfMEnd) && _cursor.isNull(_cursorIndexOfMAd) && _cursor.isNull(_cursorIndexOfMCommunityContribution) && _cursor.isNull(_cursorIndexOfMEpisode))) {
                final int _tmpMId;
                _tmpMId = _cursor.getInt(_cursorIndexOfMId);
                final String _tmpMName;
                if (_cursor.isNull(_cursorIndexOfMName)) {
                  _tmpMName = null;
                } else {
                  _tmpMName = _cursor.getString(_cursorIndexOfMName);
                }
                _tmpMTopic = new Topic(_tmpMId,_tmpMName);
                final int _tmpMStart;
                _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
                _tmpMTopic.setStart(_tmpMStart);
                final int _tmpMEnd;
                _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
                _tmpMTopic.setEnd(_tmpMEnd);
                final boolean _tmpMAd;
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfMAd);
                _tmpMAd = _tmp != 0;
                _tmpMTopic.setAd(_tmpMAd);
                final boolean _tmpMCommunityContribution;
                final int _tmp_1;
                _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
                _tmpMCommunityContribution = _tmp_1 != 0;
                _tmpMTopic.setCommunityContribution(_tmpMCommunityContribution);
                final int _tmpMEpisode;
                _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
                _tmpMTopic.setEpisode(_tmpMEpisode);
              }  else  {
                _tmpMTopic = null;
              }
              ArrayList<Subtopic> _tmpMSubtopicsCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfMId)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfMId);
                _tmpMSubtopicsCollection_1 = _collectionMSubtopics.get(_tmpKey_1);
              }
              if (_tmpMSubtopicsCollection_1 == null) {
                _tmpMSubtopicsCollection_1 = new ArrayList<Subtopic>();
              }
              _item = new TopicWithSubtopic();
              _item.setTopic(_tmpMTopic);
              _item.setSubtopics(_tmpMSubtopicsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsFrom(final int episode) {
    final String _sql = "SELECT * FROM topic_table WHERE episode_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, episode);
    return __db.getInvalidationTracker().createLiveData(new String[]{"subtopic_table","topic_table"}, true, new Callable<List<TopicWithSubtopic>>() {
      @Override
      public List<TopicWithSubtopic> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
            final int _cursorIndexOfMStart = CursorUtil.getColumnIndexOrThrow(_cursor, "start");
            final int _cursorIndexOfMEnd = CursorUtil.getColumnIndexOrThrow(_cursor, "end");
            final int _cursorIndexOfMAd = CursorUtil.getColumnIndexOrThrow(_cursor, "ad");
            final int _cursorIndexOfMCommunityContribution = CursorUtil.getColumnIndexOrThrow(_cursor, "community_contribution");
            final int _cursorIndexOfMEpisode = CursorUtil.getColumnIndexOrThrow(_cursor, "episode_id");
            final LongSparseArray<ArrayList<Subtopic>> _collectionMSubtopics = new LongSparseArray<ArrayList<Subtopic>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfMId)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfMId);
                ArrayList<Subtopic> _tmpMSubtopicsCollection = _collectionMSubtopics.get(_tmpKey);
                if (_tmpMSubtopicsCollection == null) {
                  _tmpMSubtopicsCollection = new ArrayList<Subtopic>();
                  _collectionMSubtopics.put(_tmpKey, _tmpMSubtopicsCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipsubtopicTableAssprechstundeCommunityThemenschaedelModelTopicSubtopic(_collectionMSubtopics);
            final List<TopicWithSubtopic> _result = new ArrayList<TopicWithSubtopic>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final TopicWithSubtopic _item;
              final Topic _tmpMTopic;
              if (! (_cursor.isNull(_cursorIndexOfMId) && _cursor.isNull(_cursorIndexOfMName) && _cursor.isNull(_cursorIndexOfMStart) && _cursor.isNull(_cursorIndexOfMEnd) && _cursor.isNull(_cursorIndexOfMAd) && _cursor.isNull(_cursorIndexOfMCommunityContribution) && _cursor.isNull(_cursorIndexOfMEpisode))) {
                final int _tmpMId;
                _tmpMId = _cursor.getInt(_cursorIndexOfMId);
                final String _tmpMName;
                if (_cursor.isNull(_cursorIndexOfMName)) {
                  _tmpMName = null;
                } else {
                  _tmpMName = _cursor.getString(_cursorIndexOfMName);
                }
                _tmpMTopic = new Topic(_tmpMId,_tmpMName);
                final int _tmpMStart;
                _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
                _tmpMTopic.setStart(_tmpMStart);
                final int _tmpMEnd;
                _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
                _tmpMTopic.setEnd(_tmpMEnd);
                final boolean _tmpMAd;
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfMAd);
                _tmpMAd = _tmp != 0;
                _tmpMTopic.setAd(_tmpMAd);
                final boolean _tmpMCommunityContribution;
                final int _tmp_1;
                _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
                _tmpMCommunityContribution = _tmp_1 != 0;
                _tmpMTopic.setCommunityContribution(_tmpMCommunityContribution);
                final int _tmpMEpisode;
                _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
                _tmpMTopic.setEpisode(_tmpMEpisode);
              }  else  {
                _tmpMTopic = null;
              }
              ArrayList<Subtopic> _tmpMSubtopicsCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfMId)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfMId);
                _tmpMSubtopicsCollection_1 = _collectionMSubtopics.get(_tmpKey_1);
              }
              if (_tmpMSubtopicsCollection_1 == null) {
                _tmpMSubtopicsCollection_1 = new ArrayList<Subtopic>();
              }
              _item = new TopicWithSubtopic();
              _item.setTopic(_tmpMTopic);
              _item.setSubtopics(_tmpMSubtopicsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
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
            _item = new Topic(_tmpMId,_tmpMName);
            final int _tmpMStart;
            _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
            _item.setStart(_tmpMStart);
            final int _tmpMEnd;
            _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
            _item.setEnd(_tmpMEnd);
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            _item.setAd(_tmpMAd);
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            _item.setCommunityContribution(_tmpMCommunityContribution);
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item.setEpisode(_tmpMEpisode);
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
    final String _sql = "SELECT * FROM topic_table WHERE topic_table.name LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
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
            _item = new Topic(_tmpMId,_tmpMName);
            final int _tmpMStart;
            _tmpMStart = _cursor.getInt(_cursorIndexOfMStart);
            _item.setStart(_tmpMStart);
            final int _tmpMEnd;
            _tmpMEnd = _cursor.getInt(_cursorIndexOfMEnd);
            _item.setEnd(_tmpMEnd);
            final boolean _tmpMAd;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMAd);
            _tmpMAd = _tmp != 0;
            _item.setAd(_tmpMAd);
            final boolean _tmpMCommunityContribution;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMCommunityContribution);
            _tmpMCommunityContribution = _tmp_1 != 0;
            _item.setCommunityContribution(_tmpMCommunityContribution);
            final int _tmpMEpisode;
            _tmpMEpisode = _cursor.getInt(_cursorIndexOfMEpisode);
            _item.setEpisode(_tmpMEpisode);
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

  private void __fetchRelationshipsubtopicTableAssprechstundeCommunityThemenschaedelModelTopicSubtopic(
      final LongSparseArray<ArrayList<Subtopic>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<Subtopic>> _tmpInnerMap = new LongSparseArray<ArrayList<Subtopic>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipsubtopicTableAssprechstundeCommunityThemenschaedelModelTopicSubtopic(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<Subtopic>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipsubtopicTableAssprechstundeCommunityThemenschaedelModelTopicSubtopic(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `id`,`name`,`topic_id` FROM `subtopic_table` WHERE `topic_id` IN (");
    final int _inputSize = _map.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (int i = 0; i < _map.size(); i++) {
      long _item = _map.keyAt(i);
      _stmt.bindLong(_argIndex, _item);
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = CursorUtil.getColumnIndex(_cursor, "topic_id");
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfMTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topic_id");
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          ArrayList<Subtopic> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Subtopic _item_1;
            final int _tmpMId;
            _tmpMId = _cursor.getInt(_cursorIndexOfMId);
            final String _tmpMName;
            if (_cursor.isNull(_cursorIndexOfMName)) {
              _tmpMName = null;
            } else {
              _tmpMName = _cursor.getString(_cursorIndexOfMName);
            }
            final int _tmpMTopicId;
            _tmpMTopicId = _cursor.getInt(_cursorIndexOfMTopicId);
            _item_1 = new Subtopic(_tmpMId,_tmpMName,_tmpMTopicId);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
