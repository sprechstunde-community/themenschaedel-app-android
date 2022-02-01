package sprechstunde.community.themenschaedel.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;

@androidx.room.Database(entities = {Episode.class, Topic.class, Subtopic.class, Host.class}, version = 1, exportSchema = false)
abstract public class Database extends RoomDatabase {

    private static Database mInstance;

    public abstract EpisodeDAO episodes();
    public abstract TopicDAO topics();
    public abstract SubtopicDAO subtopics();
    public abstract HostDAO hosts();

    /**
     * Create the database. Only one instance possible.
     * @param _context the context
     * @return the instance of the database
     */
    public static synchronized Database getInstance(Context _context) {

        if (mInstance == null) {
            synchronized (Database.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(
                            _context.getApplicationContext(), Database.class, "database")
                            .fallbackToDestructiveMigration()
                            .addCallback(mRoomCallBack)
                            .build();
                }
            }
        }
        return mInstance;
    }

    /**
     * Populate the database with data right when created.
     */
    private static final Callback mRoomCallBack = new Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };
}