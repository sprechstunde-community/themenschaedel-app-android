package sprechstunde.community.themenschaedel.room;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
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

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(7) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `episode_table` (`id` INTEGER NOT NULL, `title` TEXT, `subtitle` TEXT, `description` TEXT, `date` TEXT, `number` INTEGER NOT NULL, `image` TEXT, `duration` TEXT, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `topic_table` (`id` INTEGER NOT NULL, `name` TEXT, `start` INTEGER NOT NULL, `end` INTEGER NOT NULL, `ad` INTEGER NOT NULL, `community_contribution` INTEGER NOT NULL, `hasSubTopics` INTEGER NOT NULL, `episode_id` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `subtopic_table` (`id` INTEGER NOT NULL, `name` TEXT, `topic_id` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'bcf137725b2e90516649ef13691044e1')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `episode_table`");
        _db.execSQL("DROP TABLE IF EXISTS `topic_table`");
        _db.execSQL("DROP TABLE IF EXISTS `subtopic_table`");
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
        final HashMap<String, TableInfo.Column> _columnsEpisodeTable = new HashMap<String, TableInfo.Column>(8);
        _columnsEpisodeTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("subtitle", new TableInfo.Column("subtitle", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("description", new TableInfo.Column("description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("date", new TableInfo.Column("date", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("number", new TableInfo.Column("number", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("image", new TableInfo.Column("image", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsEpisodeTable.put("duration", new TableInfo.Column("duration", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysEpisodeTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesEpisodeTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoEpisodeTable = new TableInfo("episode_table", _columnsEpisodeTable, _foreignKeysEpisodeTable, _indicesEpisodeTable);
        final TableInfo _existingEpisodeTable = TableInfo.read(_db, "episode_table");
        if (! _infoEpisodeTable.equals(_existingEpisodeTable)) {
          return new RoomOpenHelper.ValidationResult(false, "episode_table(sprechstunde.community.themenschaedel.model.Episode).\n"
                  + " Expected:\n" + _infoEpisodeTable + "\n"
                  + " Found:\n" + _existingEpisodeTable);
        }
        final HashMap<String, TableInfo.Column> _columnsTopicTable = new HashMap<String, TableInfo.Column>(8);
        _columnsTopicTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("start", new TableInfo.Column("start", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("end", new TableInfo.Column("end", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("ad", new TableInfo.Column("ad", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("community_contribution", new TableInfo.Column("community_contribution", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("hasSubTopics", new TableInfo.Column("hasSubTopics", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTopicTable.put("episode_id", new TableInfo.Column("episode_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTopicTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTopicTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTopicTable = new TableInfo("topic_table", _columnsTopicTable, _foreignKeysTopicTable, _indicesTopicTable);
        final TableInfo _existingTopicTable = TableInfo.read(_db, "topic_table");
        if (! _infoTopicTable.equals(_existingTopicTable)) {
          return new RoomOpenHelper.ValidationResult(false, "topic_table(sprechstunde.community.themenschaedel.model.Topic).\n"
                  + " Expected:\n" + _infoTopicTable + "\n"
                  + " Found:\n" + _existingTopicTable);
        }
        final HashMap<String, TableInfo.Column> _columnsSubtopicTable = new HashMap<String, TableInfo.Column>(3);
        _columnsSubtopicTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtopicTable.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSubtopicTable.put("topic_id", new TableInfo.Column("topic_id", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSubtopicTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSubtopicTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSubtopicTable = new TableInfo("subtopic_table", _columnsSubtopicTable, _foreignKeysSubtopicTable, _indicesSubtopicTable);
        final TableInfo _existingSubtopicTable = TableInfo.read(_db, "subtopic_table");
        if (! _infoSubtopicTable.equals(_existingSubtopicTable)) {
          return new RoomOpenHelper.ValidationResult(false, "subtopic_table(sprechstunde.community.themenschaedel.model.Subtopic).\n"
                  + " Expected:\n" + _infoSubtopicTable + "\n"
                  + " Found:\n" + _existingSubtopicTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "bcf137725b2e90516649ef13691044e1", "d2170025271c5f39b40adc0ae462f880");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "episode_table","topic_table","subtopic_table");
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
    return _typeConvertersMap;
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
}
