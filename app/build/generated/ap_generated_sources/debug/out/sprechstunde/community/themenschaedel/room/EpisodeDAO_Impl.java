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
import sprechstunde.community.themenschaedel.model.Episode;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EpisodeDAO_Impl implements EpisodeDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Episode> __insertionAdapterOfEpisode;

  private final EntityDeletionOrUpdateAdapter<Episode> __deletionAdapterOfEpisode;

  private final EntityDeletionOrUpdateAdapter<Episode> __updateAdapterOfEpisode;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllEpisodes;

  public EpisodeDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEpisode = new EntityInsertionAdapter<Episode>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `episode_table` (`id`,`title`,`subtitle`,`description`,`date`,`number`,`image`,`duration`) VALUES (?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Episode value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getSubtitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSubtitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getDate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDate());
        }
        stmt.bindLong(6, value.getNumber());
        if (value.getImage() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImage());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getDuration());
        }
      }
    };
    this.__deletionAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `episode_table` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Episode value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `episode_table` SET `id` = ?,`title` = ?,`subtitle` = ?,`description` = ?,`date` = ?,`number` = ?,`image` = ?,`duration` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Episode value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getSubtitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSubtitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDescription());
        }
        if (value.getDate() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getDate());
        }
        stmt.bindLong(6, value.getNumber());
        if (value.getImage() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getImage());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getDuration());
        }
        stmt.bindLong(9, value.getId());
      }
    };
    this.__preparedStmtOfDeleteAllEpisodes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM episode_table";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Episode episode) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEpisode.insert(episode);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final Episode episode) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfEpisode.handle(episode);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final Episode episode) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfEpisode.handle(episode);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteAllEpisodes() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllEpisodes.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAllEpisodes.release(_stmt);
    }
  }

  @Override
  public LiveData<Episode> getEpisode(final String title) {
    final String _sql = "SELECT * FROM episode_table WHERE title = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"episode_table"}, false, new Callable<Episode>() {
      @Override
      public Episode call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfMDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "number");
          final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final Episode _result;
          if(_cursor.moveToFirst()) {
            final int _tmpMId;
            _tmpMId = _cursor.getInt(_cursorIndexOfMId);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            final String _tmpMDescription;
            if (_cursor.isNull(_cursorIndexOfMDescription)) {
              _tmpMDescription = null;
            } else {
              _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
            }
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            final int _tmpMNumber;
            _tmpMNumber = _cursor.getInt(_cursorIndexOfMNumber);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _result = new Episode(_tmpMId,_tmpMTitle,_tmpMSubtitle,_tmpMDescription,_tmpMDate,_tmpMNumber,_tmpMImage,_tmpMDuration);
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
  public LiveData<Episode> getEpisode(final int number) {
    final String _sql = "SELECT * FROM episode_table WHERE number = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, number);
    return __db.getInvalidationTracker().createLiveData(new String[]{"episode_table"}, false, new Callable<Episode>() {
      @Override
      public Episode call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfMDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "number");
          final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final Episode _result;
          if(_cursor.moveToFirst()) {
            final int _tmpMId;
            _tmpMId = _cursor.getInt(_cursorIndexOfMId);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            final String _tmpMDescription;
            if (_cursor.isNull(_cursorIndexOfMDescription)) {
              _tmpMDescription = null;
            } else {
              _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
            }
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            final int _tmpMNumber;
            _tmpMNumber = _cursor.getInt(_cursorIndexOfMNumber);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _result = new Episode(_tmpMId,_tmpMTitle,_tmpMSubtitle,_tmpMDescription,_tmpMDate,_tmpMNumber,_tmpMImage,_tmpMDuration);
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
  public LiveData<List<Episode>> getAllEpisodes() {
    final String _sql = "SELECT * FROM episode_table ORDER BY title ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"episode_table"}, false, new Callable<List<Episode>>() {
      @Override
      public List<Episode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfMDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "number");
          final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Episode _item;
            final int _tmpMId;
            _tmpMId = _cursor.getInt(_cursorIndexOfMId);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            final String _tmpMDescription;
            if (_cursor.isNull(_cursorIndexOfMDescription)) {
              _tmpMDescription = null;
            } else {
              _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
            }
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            final int _tmpMNumber;
            _tmpMNumber = _cursor.getInt(_cursorIndexOfMNumber);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _item = new Episode(_tmpMId,_tmpMTitle,_tmpMSubtitle,_tmpMDescription,_tmpMDate,_tmpMNumber,_tmpMImage,_tmpMDuration);
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
