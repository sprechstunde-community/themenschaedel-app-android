package sprechstunde.community.themenschaedel.room;

import android.database.Cursor;
import androidx.collection.ArrayMap;
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
import java.util.Set;
import java.util.concurrent.Callable;
import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.model.episode.EpisodeHostCrossRef;
import sprechstunde.community.themenschaedel.model.episode.EpisodeWithHosts;
import sprechstunde.community.themenschaedel.model.episode.HostWithEpisodes;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EpisodeDAO_Impl implements EpisodeDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Episode> __insertionAdapterOfEpisode;

  private final EntityInsertionAdapter<EpisodeHostCrossRef> __insertionAdapterOfEpisodeHostCrossRef;

  private final EntityDeletionOrUpdateAdapter<Episode> __deletionAdapterOfEpisode;

  private final EntityDeletionOrUpdateAdapter<Episode> __updateAdapterOfEpisode;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllEpisodes;

  public EpisodeDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfEpisode = new EntityInsertionAdapter<Episode>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `episode_table` (`episodeNumber`,`title`,`subtitle`,`date`,`image`,`duration`,`verified`,`claimed`,`upvotes`,`downvotes`) VALUES (?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Episode value) {
        stmt.bindLong(1, value.mEpisodeNumber);
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
        if (value.getDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDate());
        }
        if (value.getImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImage());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDuration());
        }
        final int _tmp = value.getVerified() ? 1 : 0;
        stmt.bindLong(7, _tmp);
        final int _tmp_1 = value.isClaimed() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        stmt.bindLong(9, value.getUpvotes());
        stmt.bindLong(10, value.getDownvotes());
      }
    };
    this.__insertionAdapterOfEpisodeHostCrossRef = new EntityInsertionAdapter<EpisodeHostCrossRef>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `EpisodeHostCrossRef` (`episodeNumber`,`hostName`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, EpisodeHostCrossRef value) {
        stmt.bindLong(1, value.mEpisodeNumber);
        if (value.mHostName == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.mHostName);
        }
      }
    };
    this.__deletionAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `episode_table` WHERE `episodeNumber` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Episode value) {
        stmt.bindLong(1, value.mEpisodeNumber);
      }
    };
    this.__updateAdapterOfEpisode = new EntityDeletionOrUpdateAdapter<Episode>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `episode_table` SET `episodeNumber` = ?,`title` = ?,`subtitle` = ?,`date` = ?,`image` = ?,`duration` = ?,`verified` = ?,`claimed` = ?,`upvotes` = ?,`downvotes` = ? WHERE `episodeNumber` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Episode value) {
        stmt.bindLong(1, value.mEpisodeNumber);
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
        if (value.getDate() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDate());
        }
        if (value.getImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImage());
        }
        if (value.getDuration() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDuration());
        }
        final int _tmp = value.getVerified() ? 1 : 0;
        stmt.bindLong(7, _tmp);
        final int _tmp_1 = value.isClaimed() ? 1 : 0;
        stmt.bindLong(8, _tmp_1);
        stmt.bindLong(9, value.getUpvotes());
        stmt.bindLong(10, value.getDownvotes());
        stmt.bindLong(11, value.mEpisodeNumber);
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
  public void insertEpisodeHostCrossRef(final EpisodeHostCrossRef crossRef) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfEpisodeHostCrossRef.insert(crossRef);
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
          final int _cursorIndexOfMEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfMVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
          final int _cursorIndexOfMClaimed = CursorUtil.getColumnIndexOrThrow(_cursor, "claimed");
          final int _cursorIndexOfMUpvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "upvotes");
          final int _cursorIndexOfMDownvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "downvotes");
          final Episode _result;
          if(_cursor.moveToFirst()) {
            _result = new Episode();
            _result.mEpisodeNumber = _cursor.getInt(_cursorIndexOfMEpisodeNumber);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            _result.setTitle(_tmpMTitle);
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            _result.setSubtitle(_tmpMSubtitle);
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            _result.setDate(_tmpMDate);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            _result.setImage(_tmpMImage);
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _result.setDuration(_tmpMDuration);
            final boolean _tmpMVerified;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMVerified);
            _tmpMVerified = _tmp != 0;
            _result.setVerified(_tmpMVerified);
            final boolean _tmpMClaimed;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMClaimed);
            _tmpMClaimed = _tmp_1 != 0;
            _result.setClaimed(_tmpMClaimed);
            final int _tmpMUpvotes;
            _tmpMUpvotes = _cursor.getInt(_cursorIndexOfMUpvotes);
            _result.setUpvotes(_tmpMUpvotes);
            final int _tmpMDownvotes;
            _tmpMDownvotes = _cursor.getInt(_cursorIndexOfMDownvotes);
            _result.setDownvotes(_tmpMDownvotes);
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
  public LiveData<Episode> getEpisodeByNumber(final int number) {
    final String _sql = "SELECT * FROM episode_table WHERE episodeNumber = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, number);
    return __db.getInvalidationTracker().createLiveData(new String[]{"episode_table"}, false, new Callable<Episode>() {
      @Override
      public Episode call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfMVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
          final int _cursorIndexOfMClaimed = CursorUtil.getColumnIndexOrThrow(_cursor, "claimed");
          final int _cursorIndexOfMUpvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "upvotes");
          final int _cursorIndexOfMDownvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "downvotes");
          final Episode _result;
          if(_cursor.moveToFirst()) {
            _result = new Episode();
            _result.mEpisodeNumber = _cursor.getInt(_cursorIndexOfMEpisodeNumber);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            _result.setTitle(_tmpMTitle);
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            _result.setSubtitle(_tmpMSubtitle);
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            _result.setDate(_tmpMDate);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            _result.setImage(_tmpMImage);
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _result.setDuration(_tmpMDuration);
            final boolean _tmpMVerified;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMVerified);
            _tmpMVerified = _tmp != 0;
            _result.setVerified(_tmpMVerified);
            final boolean _tmpMClaimed;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMClaimed);
            _tmpMClaimed = _tmp_1 != 0;
            _result.setClaimed(_tmpMClaimed);
            final int _tmpMUpvotes;
            _tmpMUpvotes = _cursor.getInt(_cursorIndexOfMUpvotes);
            _result.setUpvotes(_tmpMUpvotes);
            final int _tmpMDownvotes;
            _tmpMDownvotes = _cursor.getInt(_cursorIndexOfMDownvotes);
            _result.setDownvotes(_tmpMDownvotes);
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
          final int _cursorIndexOfMEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfMVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
          final int _cursorIndexOfMClaimed = CursorUtil.getColumnIndexOrThrow(_cursor, "claimed");
          final int _cursorIndexOfMUpvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "upvotes");
          final int _cursorIndexOfMDownvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "downvotes");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Episode _item;
            _item = new Episode();
            _item.mEpisodeNumber = _cursor.getInt(_cursorIndexOfMEpisodeNumber);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            _item.setTitle(_tmpMTitle);
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            _item.setSubtitle(_tmpMSubtitle);
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            _item.setDate(_tmpMDate);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            _item.setImage(_tmpMImage);
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _item.setDuration(_tmpMDuration);
            final boolean _tmpMVerified;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMVerified);
            _tmpMVerified = _tmp != 0;
            _item.setVerified(_tmpMVerified);
            final boolean _tmpMClaimed;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMClaimed);
            _tmpMClaimed = _tmp_1 != 0;
            _item.setClaimed(_tmpMClaimed);
            final int _tmpMUpvotes;
            _tmpMUpvotes = _cursor.getInt(_cursorIndexOfMUpvotes);
            _item.setUpvotes(_tmpMUpvotes);
            final int _tmpMDownvotes;
            _tmpMDownvotes = _cursor.getInt(_cursorIndexOfMDownvotes);
            _item.setDownvotes(_tmpMDownvotes);
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
  public LiveData<List<Episode>> search(final String query) {
    final String _sql = "SELECT * FROM episode_table WHERE (episode_table.title LIKE '%' || ? || '%') OR (episode_table.episodeNumber LIKE '%' || ? || '%') ORDER BY episode_table.episodeNumber DESC";
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
    return __db.getInvalidationTracker().createLiveData(new String[]{"episode_table"}, false, new Callable<List<Episode>>() {
      @Override
      public List<Episode> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfMEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
          final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
          final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
          final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
          final int _cursorIndexOfMVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
          final int _cursorIndexOfMClaimed = CursorUtil.getColumnIndexOrThrow(_cursor, "claimed");
          final int _cursorIndexOfMUpvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "upvotes");
          final int _cursorIndexOfMDownvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "downvotes");
          final List<Episode> _result = new ArrayList<Episode>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Episode _item;
            _item = new Episode();
            _item.mEpisodeNumber = _cursor.getInt(_cursorIndexOfMEpisodeNumber);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            _item.setTitle(_tmpMTitle);
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            _item.setSubtitle(_tmpMSubtitle);
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            _item.setDate(_tmpMDate);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            _item.setImage(_tmpMImage);
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _item.setDuration(_tmpMDuration);
            final boolean _tmpMVerified;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMVerified);
            _tmpMVerified = _tmp != 0;
            _item.setVerified(_tmpMVerified);
            final boolean _tmpMClaimed;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMClaimed);
            _tmpMClaimed = _tmp_1 != 0;
            _item.setClaimed(_tmpMClaimed);
            final int _tmpMUpvotes;
            _tmpMUpvotes = _cursor.getInt(_cursorIndexOfMUpvotes);
            _item.setUpvotes(_tmpMUpvotes);
            final int _tmpMDownvotes;
            _tmpMDownvotes = _cursor.getInt(_cursorIndexOfMDownvotes);
            _item.setDownvotes(_tmpMDownvotes);
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
  public LiveData<HostWithEpisodes> getHostsFromEpisode(final String hostName) {
    final String _sql = "SELECT * FROM host_table WHERE hostName = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (hostName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, hostName);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"EpisodeHostCrossRef","episode_table","host_table"}, true, new Callable<HostWithEpisodes>() {
      @Override
      public HostWithEpisodes call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfMName = CursorUtil.getColumnIndexOrThrow(_cursor, "hostName");
            final int _cursorIndexOfMDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
            final int _cursorIndexOfMHost = CursorUtil.getColumnIndexOrThrow(_cursor, "host");
            final ArrayMap<String, ArrayList<Episode>> _collectionMEpisodes = new ArrayMap<String, ArrayList<Episode>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfMName)) {
                final String _tmpKey = _cursor.getString(_cursorIndexOfMName);
                ArrayList<Episode> _tmpMEpisodesCollection = _collectionMEpisodes.get(_tmpKey);
                if (_tmpMEpisodesCollection == null) {
                  _tmpMEpisodesCollection = new ArrayList<Episode>();
                  _collectionMEpisodes.put(_tmpKey, _tmpMEpisodesCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshipepisodeTableAssprechstundeCommunityThemenschaedelModelEpisodeEpisode(_collectionMEpisodes);
            final HostWithEpisodes _result;
            if(_cursor.moveToFirst()) {
              final Host _tmpMHost;
              if (! (_cursor.isNull(_cursorIndexOfMName) && _cursor.isNull(_cursorIndexOfMDescription) && _cursor.isNull(_cursorIndexOfMHost))) {
                final String _tmpMName;
                if (_cursor.isNull(_cursorIndexOfMName)) {
                  _tmpMName = null;
                } else {
                  _tmpMName = _cursor.getString(_cursorIndexOfMName);
                }
                final String _tmpMDescription;
                if (_cursor.isNull(_cursorIndexOfMDescription)) {
                  _tmpMDescription = null;
                } else {
                  _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
                }
                final boolean _tmpMHost_1;
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfMHost);
                _tmpMHost_1 = _tmp != 0;
                _tmpMHost = new Host(_tmpMName,_tmpMDescription,_tmpMHost_1);
              }  else  {
                _tmpMHost = null;
              }
              ArrayList<Episode> _tmpMEpisodesCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfMName)) {
                final String _tmpKey_1 = _cursor.getString(_cursorIndexOfMName);
                _tmpMEpisodesCollection_1 = _collectionMEpisodes.get(_tmpKey_1);
              }
              if (_tmpMEpisodesCollection_1 == null) {
                _tmpMEpisodesCollection_1 = new ArrayList<Episode>();
              }
              _result = new HostWithEpisodes();
              _result.setHost(_tmpMHost);
              _result.setEpisodes(_tmpMEpisodesCollection_1);
            } else {
              _result = null;
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
  public LiveData<EpisodeWithHosts> getEpisodeWithHosts(final int number) {
    final String _sql = "SELECT * FROM episode_table WHERE episodeNumber = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, number);
    return __db.getInvalidationTracker().createLiveData(new String[]{"EpisodeHostCrossRef","host_table","episode_table"}, true, new Callable<EpisodeWithHosts>() {
      @Override
      public EpisodeWithHosts call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfMEpisodeNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "episodeNumber");
            final int _cursorIndexOfMTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
            final int _cursorIndexOfMSubtitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subtitle");
            final int _cursorIndexOfMDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
            final int _cursorIndexOfMImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
            final int _cursorIndexOfMDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
            final int _cursorIndexOfMVerified = CursorUtil.getColumnIndexOrThrow(_cursor, "verified");
            final int _cursorIndexOfMClaimed = CursorUtil.getColumnIndexOrThrow(_cursor, "claimed");
            final int _cursorIndexOfMUpvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "upvotes");
            final int _cursorIndexOfMDownvotes = CursorUtil.getColumnIndexOrThrow(_cursor, "downvotes");
            final LongSparseArray<ArrayList<Host>> _collectionMHosts = new LongSparseArray<ArrayList<Host>>();
            while (_cursor.moveToNext()) {
              if (!_cursor.isNull(_cursorIndexOfMEpisodeNumber)) {
                final long _tmpKey = _cursor.getLong(_cursorIndexOfMEpisodeNumber);
                ArrayList<Host> _tmpMHostsCollection = _collectionMHosts.get(_tmpKey);
                if (_tmpMHostsCollection == null) {
                  _tmpMHostsCollection = new ArrayList<Host>();
                  _collectionMHosts.put(_tmpKey, _tmpMHostsCollection);
                }
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiphostTableAssprechstundeCommunityThemenschaedelModelHost(_collectionMHosts);
            final EpisodeWithHosts _result;
            if(_cursor.moveToFirst()) {
              final Episode _tmpMEpisode;
              if (! (_cursor.isNull(_cursorIndexOfMEpisodeNumber) && _cursor.isNull(_cursorIndexOfMTitle) && _cursor.isNull(_cursorIndexOfMSubtitle) && _cursor.isNull(_cursorIndexOfMDate) && _cursor.isNull(_cursorIndexOfMImage) && _cursor.isNull(_cursorIndexOfMDuration) && _cursor.isNull(_cursorIndexOfMVerified) && _cursor.isNull(_cursorIndexOfMClaimed) && _cursor.isNull(_cursorIndexOfMUpvotes) && _cursor.isNull(_cursorIndexOfMDownvotes))) {
                _tmpMEpisode = new Episode();
                _tmpMEpisode.mEpisodeNumber = _cursor.getInt(_cursorIndexOfMEpisodeNumber);
                final String _tmpMTitle;
                if (_cursor.isNull(_cursorIndexOfMTitle)) {
                  _tmpMTitle = null;
                } else {
                  _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
                }
                _tmpMEpisode.setTitle(_tmpMTitle);
                final String _tmpMSubtitle;
                if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
                  _tmpMSubtitle = null;
                } else {
                  _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
                }
                _tmpMEpisode.setSubtitle(_tmpMSubtitle);
                final String _tmpMDate;
                if (_cursor.isNull(_cursorIndexOfMDate)) {
                  _tmpMDate = null;
                } else {
                  _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
                }
                _tmpMEpisode.setDate(_tmpMDate);
                final String _tmpMImage;
                if (_cursor.isNull(_cursorIndexOfMImage)) {
                  _tmpMImage = null;
                } else {
                  _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
                }
                _tmpMEpisode.setImage(_tmpMImage);
                final String _tmpMDuration;
                if (_cursor.isNull(_cursorIndexOfMDuration)) {
                  _tmpMDuration = null;
                } else {
                  _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
                }
                _tmpMEpisode.setDuration(_tmpMDuration);
                final boolean _tmpMVerified;
                final int _tmp;
                _tmp = _cursor.getInt(_cursorIndexOfMVerified);
                _tmpMVerified = _tmp != 0;
                _tmpMEpisode.setVerified(_tmpMVerified);
                final boolean _tmpMClaimed;
                final int _tmp_1;
                _tmp_1 = _cursor.getInt(_cursorIndexOfMClaimed);
                _tmpMClaimed = _tmp_1 != 0;
                _tmpMEpisode.setClaimed(_tmpMClaimed);
                final int _tmpMUpvotes;
                _tmpMUpvotes = _cursor.getInt(_cursorIndexOfMUpvotes);
                _tmpMEpisode.setUpvotes(_tmpMUpvotes);
                final int _tmpMDownvotes;
                _tmpMDownvotes = _cursor.getInt(_cursorIndexOfMDownvotes);
                _tmpMEpisode.setDownvotes(_tmpMDownvotes);
              }  else  {
                _tmpMEpisode = null;
              }
              ArrayList<Host> _tmpMHostsCollection_1 = null;
              if (!_cursor.isNull(_cursorIndexOfMEpisodeNumber)) {
                final long _tmpKey_1 = _cursor.getLong(_cursorIndexOfMEpisodeNumber);
                _tmpMHostsCollection_1 = _collectionMHosts.get(_tmpKey_1);
              }
              if (_tmpMHostsCollection_1 == null) {
                _tmpMHostsCollection_1 = new ArrayList<Host>();
              }
              _result = new EpisodeWithHosts();
              _result.setEpisode(_tmpMEpisode);
              _result.setHosts(_tmpMHostsCollection_1);
            } else {
              _result = null;
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

  private void __fetchRelationshipepisodeTableAssprechstundeCommunityThemenschaedelModelEpisodeEpisode(
      final ArrayMap<String, ArrayList<Episode>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      ArrayMap<String, ArrayList<Episode>> _tmpInnerMap = new ArrayMap<String, ArrayList<Episode>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshipepisodeTableAssprechstundeCommunityThemenschaedelModelEpisodeEpisode(_tmpInnerMap);
          _tmpInnerMap = new ArrayMap<String, ArrayList<Episode>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshipepisodeTableAssprechstundeCommunityThemenschaedelModelEpisodeEpisode(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `episode_table`.`episodeNumber` AS `episodeNumber`,`episode_table`.`title` AS `title`,`episode_table`.`subtitle` AS `subtitle`,`episode_table`.`date` AS `date`,`episode_table`.`image` AS `image`,`episode_table`.`duration` AS `duration`,`episode_table`.`verified` AS `verified`,`episode_table`.`claimed` AS `claimed`,`episode_table`.`upvotes` AS `upvotes`,`episode_table`.`downvotes` AS `downvotes`,_junction.`hostName` FROM `EpisodeHostCrossRef` AS _junction INNER JOIN `episode_table` ON (_junction.`episodeNumber` = `episode_table`.`episodeNumber`) WHERE _junction.`hostName` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 10; // _junction.hostName;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfMEpisodeNumber = 0;
      final int _cursorIndexOfMTitle = 1;
      final int _cursorIndexOfMSubtitle = 2;
      final int _cursorIndexOfMDate = 3;
      final int _cursorIndexOfMImage = 4;
      final int _cursorIndexOfMDuration = 5;
      final int _cursorIndexOfMVerified = 6;
      final int _cursorIndexOfMClaimed = 7;
      final int _cursorIndexOfMUpvotes = 8;
      final int _cursorIndexOfMDownvotes = 9;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final String _tmpKey = _cursor.getString(_itemKeyIndex);
          ArrayList<Episode> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Episode _item_1;
            _item_1 = new Episode();
            _item_1.mEpisodeNumber = _cursor.getInt(_cursorIndexOfMEpisodeNumber);
            final String _tmpMTitle;
            if (_cursor.isNull(_cursorIndexOfMTitle)) {
              _tmpMTitle = null;
            } else {
              _tmpMTitle = _cursor.getString(_cursorIndexOfMTitle);
            }
            _item_1.setTitle(_tmpMTitle);
            final String _tmpMSubtitle;
            if (_cursor.isNull(_cursorIndexOfMSubtitle)) {
              _tmpMSubtitle = null;
            } else {
              _tmpMSubtitle = _cursor.getString(_cursorIndexOfMSubtitle);
            }
            _item_1.setSubtitle(_tmpMSubtitle);
            final String _tmpMDate;
            if (_cursor.isNull(_cursorIndexOfMDate)) {
              _tmpMDate = null;
            } else {
              _tmpMDate = _cursor.getString(_cursorIndexOfMDate);
            }
            _item_1.setDate(_tmpMDate);
            final String _tmpMImage;
            if (_cursor.isNull(_cursorIndexOfMImage)) {
              _tmpMImage = null;
            } else {
              _tmpMImage = _cursor.getString(_cursorIndexOfMImage);
            }
            _item_1.setImage(_tmpMImage);
            final String _tmpMDuration;
            if (_cursor.isNull(_cursorIndexOfMDuration)) {
              _tmpMDuration = null;
            } else {
              _tmpMDuration = _cursor.getString(_cursorIndexOfMDuration);
            }
            _item_1.setDuration(_tmpMDuration);
            final boolean _tmpMVerified;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMVerified);
            _tmpMVerified = _tmp != 0;
            _item_1.setVerified(_tmpMVerified);
            final boolean _tmpMClaimed;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfMClaimed);
            _tmpMClaimed = _tmp_1 != 0;
            _item_1.setClaimed(_tmpMClaimed);
            final int _tmpMUpvotes;
            _tmpMUpvotes = _cursor.getInt(_cursorIndexOfMUpvotes);
            _item_1.setUpvotes(_tmpMUpvotes);
            final int _tmpMDownvotes;
            _tmpMDownvotes = _cursor.getInt(_cursorIndexOfMDownvotes);
            _item_1.setDownvotes(_tmpMDownvotes);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiphostTableAssprechstundeCommunityThemenschaedelModelHost(
      final LongSparseArray<ArrayList<Host>> _map) {
    if (_map.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      LongSparseArray<ArrayList<Host>> _tmpInnerMap = new LongSparseArray<ArrayList<Host>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiphostTableAssprechstundeCommunityThemenschaedelModelHost(_tmpInnerMap);
          _tmpInnerMap = new LongSparseArray<ArrayList<Host>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiphostTableAssprechstundeCommunityThemenschaedelModelHost(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `host_table`.`hostName` AS `hostName`,`host_table`.`description` AS `description`,`host_table`.`host` AS `host`,_junction.`episodeNumber` FROM `EpisodeHostCrossRef` AS _junction INNER JOIN `host_table` ON (_junction.`hostName` = `host_table`.`hostName`) WHERE _junction.`episodeNumber` IN (");
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
      final int _itemKeyIndex = 3; // _junction.episodeNumber;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfMName = 0;
      final int _cursorIndexOfMDescription = 1;
      final int _cursorIndexOfMHost = 2;
      while(_cursor.moveToNext()) {
        if (!_cursor.isNull(_itemKeyIndex)) {
          final long _tmpKey = _cursor.getLong(_itemKeyIndex);
          ArrayList<Host> _tmpRelation = _map.get(_tmpKey);
          if (_tmpRelation != null) {
            final Host _item_1;
            final String _tmpMName;
            if (_cursor.isNull(_cursorIndexOfMName)) {
              _tmpMName = null;
            } else {
              _tmpMName = _cursor.getString(_cursorIndexOfMName);
            }
            final String _tmpMDescription;
            if (_cursor.isNull(_cursorIndexOfMDescription)) {
              _tmpMDescription = null;
            } else {
              _tmpMDescription = _cursor.getString(_cursorIndexOfMDescription);
            }
            final boolean _tmpMHost;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfMHost);
            _tmpMHost = _tmp != 0;
            _item_1 = new Host(_tmpMName,_tmpMDescription,_tmpMHost);
            _tmpRelation.add(_item_1);
          }
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
