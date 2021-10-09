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
import sprechstunde.community.themenschaedel.model.Subtopic;

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

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
