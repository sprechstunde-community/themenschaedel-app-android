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
public final class SubtopicDAO_Impl implements SubtopicDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Subtopic> __insertionAdapterOfSubtopic;

  private final EntityDeletionOrUpdateAdapter<Subtopic> __deletionAdapterOfSubtopic;

  private final EntityDeletionOrUpdateAdapter<Subtopic> __updateAdapterOfSubtopic;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllSubtopics;

  public SubtopicDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSubtopic = new EntityInsertionAdapter<Subtopic>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `subtopic_table` (`id`,`name`,`topic_id`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subtopic value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getTopicId());
      }
    };
    this.__deletionAdapterOfSubtopic = new EntityDeletionOrUpdateAdapter<Subtopic>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `subtopic_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subtopic value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfSubtopic = new EntityDeletionOrUpdateAdapter<Subtopic>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `subtopic_table` SET `id` = ?,`name` = ?,`topic_id` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Subtopic value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        stmt.bindLong(3, value.getTopicId());
        stmt.bindLong(4, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllSubtopics = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM subtopic_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Subtopic topic) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSubtopic.insert(topic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Subtopic topic) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSubtopic.handle(topic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Subtopic topic) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSubtopic.handle(topic);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllSubtopics() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllSubtopics.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllSubtopics.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Subtopic>> getAllSubtopics() {
    final String _sql = "SELECT * FROM subtopic_table ORDER BY name ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"subtopic_table"}, false, new Callable<List<Subtopic>>() {
      @Override
      public List<Subtopic> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topic_id");
          final List<Subtopic> _result = new ArrayList<Subtopic>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Subtopic _item;
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
            _item = new Subtopic(_tmpMId,_tmpMName,_tmpMTopicId);
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
  public LiveData<List<Subtopic>> getAllSubtopicsFrom(final int topic) {
    final String _sql = "SELECT * FROM subtopic_table WHERE topic_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, topic);
    return __db.getInvalidationTracker().createLiveData(new String[]{"subtopic_table"}, false, new Callable<List<Subtopic>>() {
      @Override
      public List<Subtopic> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfMTopicId = CursorUtil.getColumnIndexOrThrow(_cursor, "topic_id");
          final List<Subtopic> _result = new ArrayList<Subtopic>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Subtopic _item;
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
            _item = new Subtopic(_tmpMId,_tmpMName,_tmpMTopicId);
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
  public LiveData<List<TopicWithSubtopic>> search(final String query) {
    final String _sql = "SELECT DISTINCT topic_table.id, topic_table.name, topic_table.episode_id FROM topic_table LEFT JOIN subtopic_table on topic_table.id = subtopic_table.topic_id WHERE topic_table.name LIKE '%' || ? || '%' OR subtopic_table.name LIKE '%' || ? || '%'";
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
    return __db.getInvalidationTracker().createLiveData(new String[]{"subtopic_table","topic_table"}, true, new Callable<List<TopicWithSubtopic>>() {
      @Override
      public List<TopicWithSubtopic> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
            final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
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
              if (! (_cursor.isNull(_cursorIndexOfMId) && _cursor.isNull(_cursorIndexOfMName) && _cursor.isNull(_cursorIndexOfMEpisode))) {
                final int _tmpMId;
                _tmpMId = _cursor.getInt(_cursorIndexOfMId);
                final String _tmpMName;
                if (_cursor.isNull(_cursorIndexOfMName)) {
                  _tmpMName = null;
                } else {
                  _tmpMName = _cursor.getString(_cursorIndexOfMName);
                }
                _tmpMTopic = new Topic(_tmpMId,_tmpMName);
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
