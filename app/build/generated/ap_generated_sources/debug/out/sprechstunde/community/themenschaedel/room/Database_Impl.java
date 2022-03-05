package sprechstunde.community.themenschaedel.room;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Database_Impl extends Database {
  private volatile EpisodeDAO _episodeDAO;

  private volatile TopicDAO _topicDAO;

  private volatile SubtopicDAO _subtopicDAO;

  private volatile HostDAO _hostDAO;

  private volatile UserDAO _userDAO;

  private volatile SessionDAO _sessionDAO;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(10) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `episode_table` (`episodeNumber` INTEGER NOT NULL, `title` TEXT, `subtitle` TEXT, `date` TEXT, `image` TEXT, `duration` TEXT, `verified` INTEGER NOT NULL, `claimed` INTEGER NOT NULL, `upvotes` INTEGER NOT NULL, `downvotes` INTEGER NOT NULL, PRIMARY KEY(`episodeNumber`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `topic_table` (`id` INTEGER NOT NULL, `name` TEXT, `start` INTEGER NOT NULL, `end` INTEGER NOT NULL, `ad` INTEGER NOT NULL, `community_contribution` INTEGER NOT NULL, `episode_id` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `subtopic_table` (`id` INTEGER NOT NULL, `name` TEXT, `id_topic` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `host_table` (`hostName` TEXT NOT NULL, `description` TEXT, `host` INTEGER NOT NULL, PRIMARY KEY(`hostName`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `EpisodeHostCrossRef` (`episodeNumber` INTEGER NOT NULL, `hostName` TEXT NOT NULL, PRIMARY KEY(`episodeNumber`, `hostName`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user_table` (`id` INTEGER NOT NULL, `username` TEXT, `email` TEXT, `role_id` INTEGER NOT NULL, `platform` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `session_table` (`session_id` INTEGER NOT NULL, `refresh_token` TEXT, `access_token` TEXT, `id` INTEGER, `username` TEXT, `email` TEXT, `role_id` INTEGER, `platform` TEXT, PRIMARY KEY(`session_id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7ff516234500b5afdb2dab9df31b7ef8')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `episode_table`");
        _db.execSQL("DROP TABLE IF EXISTS `topic_table`");
        _db.execSQL("DROP TABLE IF EXISTS `subtopic_table`");
        _db.execSQL("DROP TABLE IF EXISTS `host_table`");
        _db.execSQL("DROP TABLE IF EXISTS `EpisodeHostCrossRef`");
        _db.execSQL("DROP TABLE IF EXISTS `user_table`");
        _db.execSQL("DROP TABLE IF EXISTS `session_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsEpisodeTable = new HashMap<String, TableInfo.Column>(10);
        _columnsEpisodeTable.put("episodeNumber", new TableInfo.Column("episodeNumber", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("subtitle", new TableInfo.Column("subtitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("image", new TableInfo.Column("image", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("duration", new TableInfo.Column("duration", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("verified", new TableInfo.Column("verified", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("claimed", new TableInfo.Column("claimed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("upvotes", new TableInfo.Column("upvotes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("downvotes", new TableInfo.Column("downvotes", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEpisodeTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEpisodeTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEpisodeTable = new TableInfo("episode_table", _columnsEpisodeTable, _foreignKeysEpisodeTable, _indicesEpisodeTable);
        final TableInfo _existingEpisodeTable = TableInfo.read(_db, "episode_table");
        if (! _infoEpisodeTable.equals(_existingEpisodeTable)) {
          return new RoomOpenHelper.ValidationResult(false, "episode_table(sprechstunde.community.themenschaedel.model.episode.Episode).\n"
                  + " Expected:\n" + _infoEpisodeTable + "\n"
                  + " Found:\n" + _existingEpisodeTable);
        }
        final HashMap<String, TableInfo.Column> _columnsTopicTable = new HashMap<String, TableInfo.Column>(7);
        _columnsTopicTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("start", new TableInfo.Column("start", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("end", new TableInfo.Column("end", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("ad", new TableInfo.Column("ad", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("community_contribution", new TableInfo.Column("community_contribution", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("episode_id", new TableInfo.Column("episode_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTopicTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTopicTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTopicTable = new TableInfo("topic_table", _columnsTopicTable, _foreignKeysTopicTable, _indicesTopicTable);
        final TableInfo _existingTopicTable = TableInfo.read(_db, "topic_table");
        if (! _infoTopicTable.equals(_existingTopicTable)) {
          return new RoomOpenHelper.ValidationResult(false, "topic_table(sprechstunde.community.themenschaedel.model.topic.Topic).\n"
                  + " Expected:\n" + _infoTopicTable + "\n"
                  + " Found:\n" + _existingTopicTable);
        }
        final HashMap<String, TableInfo.Column> _columnsSubtopicTable = new HashMap<String, TableInfo.Column>(3);
        _columnsSubtopicTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtopicTable.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtopicTable.put("id_topic", new TableInfo.Column("id_topic", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubtopicTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubtopicTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubtopicTable = new TableInfo("subtopic_table", _columnsSubtopicTable, _foreignKeysSubtopicTable, _indicesSubtopicTable);
        final TableInfo _existingSubtopicTable = TableInfo.read(_db, "subtopic_table");
        if (! _infoSubtopicTable.equals(_existingSubtopicTable)) {
          return new RoomOpenHelper.ValidationResult(false, "subtopic_table(sprechstunde.community.themenschaedel.model.topic.Subtopic).\n"
                  + " Expected:\n" + _infoSubtopicTable + "\n"
                  + " Found:\n" + _existingSubtopicTable);
        }
        final HashMap<String, TableInfo.Column> _columnsHostTable = new HashMap<String, TableInfo.Column>(3);
        _columnsHostTable.put("hostName", new TableInfo.Column("hostName", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHostTable.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsHostTable.put("host", new TableInfo.Column("host", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysHostTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesHostTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoHostTable = new TableInfo("host_table", _columnsHostTable, _foreignKeysHostTable, _indicesHostTable);
        final TableInfo _existingHostTable = TableInfo.read(_db, "host_table");
        if (! _infoHostTable.equals(_existingHostTable)) {
          return new RoomOpenHelper.ValidationResult(false, "host_table(sprechstunde.community.themenschaedel.model.Host).\n"
                  + " Expected:\n" + _infoHostTable + "\n"
                  + " Found:\n" + _existingHostTable);
        }
        final HashMap<String, TableInfo.Column> _columnsEpisodeHostCrossRef = new HashMap<String, TableInfo.Column>(2);
        _columnsEpisodeHostCrossRef.put("episodeNumber", new TableInfo.Column("episodeNumber", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeHostCrossRef.put("hostName", new TableInfo.Column("hostName", "TEXT", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEpisodeHostCrossRef = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEpisodeHostCrossRef = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEpisodeHostCrossRef = new TableInfo("EpisodeHostCrossRef", _columnsEpisodeHostCrossRef, _foreignKeysEpisodeHostCrossRef, _indicesEpisodeHostCrossRef);
        final TableInfo _existingEpisodeHostCrossRef = TableInfo.read(_db, "EpisodeHostCrossRef");
        if (! _infoEpisodeHostCrossRef.equals(_existingEpisodeHostCrossRef)) {
          return new RoomOpenHelper.ValidationResult(false, "EpisodeHostCrossRef(sprechstunde.community.themenschaedel.model.episode.EpisodeHostCrossRef).\n"
                  + " Expected:\n" + _infoEpisodeHostCrossRef + "\n"
                  + " Found:\n" + _existingEpisodeHostCrossRef);
        }
        final HashMap<String, TableInfo.Column> _columnsUserTable = new HashMap<String, TableInfo.Column>(5);
        _columnsUserTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("role_id", new TableInfo.Column("role_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("platform", new TableInfo.Column("platform", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserTable = new TableInfo("user_table", _columnsUserTable, _foreignKeysUserTable, _indicesUserTable);
        final TableInfo _existingUserTable = TableInfo.read(_db, "user_table");
        if (! _infoUserTable.equals(_existingUserTable)) {
          return new RoomOpenHelper.ValidationResult(false, "user_table(sprechstunde.community.themenschaedel.model.User).\n"
                  + " Expected:\n" + _infoUserTable + "\n"
                  + " Found:\n" + _existingUserTable);
        }
        final HashMap<String, TableInfo.Column> _columnsSessionTable = new HashMap<String, TableInfo.Column>(8);
        _columnsSessionTable.put("session_id", new TableInfo.Column("session_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionTable.put("refresh_token", new TableInfo.Column("refresh_token", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionTable.put("access_token", new TableInfo.Column("access_token", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionTable.put("id", new TableInfo.Column("id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionTable.put("username", new TableInfo.Column("username", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionTable.put("email", new TableInfo.Column("email", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionTable.put("role_id", new TableInfo.Column("role_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSessionTable.put("platform", new TableInfo.Column("platform", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSessionTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSessionTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSessionTable = new TableInfo("session_table", _columnsSessionTable, _foreignKeysSessionTable, _indicesSessionTable);
        final TableInfo _existingSessionTable = TableInfo.read(_db, "session_table");
        if (! _infoSessionTable.equals(_existingSessionTable)) {
          return new RoomOpenHelper.ValidationResult(false, "session_table(sprechstunde.community.themenschaedel.model.SessionData).\n"
                  + " Expected:\n" + _infoSessionTable + "\n"
                  + " Found:\n" + _existingSessionTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "7ff516234500b5afdb2dab9df31b7ef8", "dfdf2bbbfa7e0954700aca70c067fbec");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "episode_table","topic_table","subtopic_table","host_table","EpisodeHostCrossRef","user_table","session_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `episode_table`");
      _db.execSQL("DELETE FROM `topic_table`");
      _db.execSQL("DELETE FROM `subtopic_table`");
      _db.execSQL("DELETE FROM `host_table`");
      _db.execSQL("DELETE FROM `EpisodeHostCrossRef`");
      _db.execSQL("DELETE FROM `user_table`");
      _db.execSQL("DELETE FROM `session_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(EpisodeDAO.class, EpisodeDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(TopicDAO.class, TopicDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(SubtopicDAO.class, SubtopicDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(HostDAO.class, HostDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDAO.class, UserDAO_Impl.getRequiredConverters());
    _typeConvertersMap.put(SessionDAO.class, SessionDAO_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public EpisodeDAO episodes() {
    if (_episodeDAO != null) {
      return _episodeDAO;
    } else {
      synchronized(this) {
        if(_episodeDAO == null) {
          _episodeDAO = new EpisodeDAO_Impl(this);
        }
        return _episodeDAO;
      }
    }
  }

  @Override
  public TopicDAO topics() {
    if (_topicDAO != null) {
      return _topicDAO;
    } else {
      synchronized(this) {
        if(_topicDAO == null) {
          _topicDAO = new TopicDAO_Impl(this);
        }
        return _topicDAO;
      }
    }
  }

  @Override
  public SubtopicDAO subtopics() {
    if (_subtopicDAO != null) {
      return _subtopicDAO;
    } else {
      synchronized(this) {
        if(_subtopicDAO == null) {
          _subtopicDAO = new SubtopicDAO_Impl(this);
        }
        return _subtopicDAO;
      }
    }
  }

  @Override
  public HostDAO hosts() {
    if (_hostDAO != null) {
      return _hostDAO;
    } else {
      synchronized(this) {
        if(_hostDAO == null) {
          _hostDAO = new HostDAO_Impl(this);
        }
        return _hostDAO;
      }
    }
  }

  @Override
  public UserDAO users() {
    if (_userDAO != null) {
      return _userDAO;
    } else {
      synchronized(this) {
        if(_userDAO == null) {
          _userDAO = new UserDAO_Impl(this);
        }
        return _userDAO;
      }
    }
  }

  @Override
  public SessionDAO sessionData() {
    if (_sessionDAO != null) {
      return _sessionDAO;
    } else {
      synchronized(this) {
        if(_sessionDAO == null) {
          _sessionDAO = new SessionDAO_Impl(this);
        }
        return _sessionDAO;
      }
    }
  }
}
