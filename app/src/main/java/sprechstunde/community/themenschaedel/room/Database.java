package sprechstunde.community.themenschaedel.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.Topic;

@androidx.room.Database(entities = {Episode.class, Topic.class}, version = 1, exportSchema = false)
abstract public class Database extends RoomDatabase {

    private static Database mInstance;
    public abstract EpisodeDAO episodeDAO();
    public abstract TopicDAO topicDAO();

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
    private static RoomDatabase.Callback mRoomCallBack = new RoomDatabase.Callback(){

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDBAsyncTask(mInstance).execute(); //insert the AsyncTask below
        }
    };


    private static class PopulateDBAsyncTask extends AsyncTask<Void, Void, Void> {

        private EpisodeDAO mEpisodeDAO;
        private TopicDAO mTopicDAO;

        private PopulateDBAsyncTask(Database _db){
            mEpisodeDAO = _db.episodeDAO();
            mTopicDAO = _db.topicDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }
}
