package sprechstunde.community.themenschaedel.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

@androidx.room.Database(entities = {Episode.class, Topic.class, Subtopic.class}, version = 1, exportSchema = false)
abstract public class Database extends RoomDatabase {

    private static Database mInstance;
    private static Context mActivity;

    public abstract EpisodeDAO episodeDAO();
    public abstract TopicDAO topicDAO();
    public abstract SubtopicDAO subtopicDAO();

    /**
     * Create the database. Only one instance possible.
     * @param _context the context
     * @return the instance of the database
     */
    public static synchronized Database getInstance(Context _context) {

        mActivity = _context.getApplicationContext();

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

        private final EpisodeDAO mEpisodeDAO;
        private final TopicDAO mTopicDAO;
        private final SubtopicDAO mSubtopicDAO;

        private PopulateDBAsyncTask(Database _db){
            mEpisodeDAO = _db.episodeDAO();
            mTopicDAO = _db.topicDAO();
            mSubtopicDAO = _db.subtopicDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            fillWithStartingData(mActivity);
            return null;
        }


        private static void fillWithStartingData (Context context) {
            JSONArray episodes = loadJSONArray(context, R.raw.dummydata);

            try {
                for (int i = 0; i < episodes.length(); i++) {
                    jsonToEpisode(context, episodes,i);
                }
            } catch (ParseException | JSONException exception) {
                exception.printStackTrace();
            }
        }

        private static void jsonToEpisode(Context context, JSONArray episodes, int i) throws JSONException, ParseException {
            EpisodeDAO dao = getInstance(context).episodeDAO();
            JSONObject episode = episodes.getJSONObject(i);

            int id = episode.getInt("id");
            String title = episode.getString("title");
            String subtitle = episode.getString("subtitle");
            String description = episode.getString("description");
            String image = episode.getString("image");
            int number = episode.getInt("episode_number");

            String date = episode.getString("published_at");
            SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());
            SimpleDateFormat myFormat = new SimpleDateFormat("d. MMMM yyyy", Locale.getDefault());
            String reformattedDate = myFormat.format(fromUser.parse(date));

            int duration = episode.getInt("duration");
            int hours = duration / 3600;
            int minutes = (duration % 3600) / 60;
            String reformattedDuration = String.format(Locale.getDefault(), "%01dh %01dmin", hours, minutes);

            JSONArray array = episode.getJSONArray("topics");
            for (int j = 0; j < array.length(); j++) {
                jsonToTopic(context, array,j);
            }

            dao.insert(new Episode(id, title, subtitle, description, reformattedDate, number, image, reformattedDuration));
        }

        private static void jsonToTopic(Context context, JSONArray topics, int i) throws JSONException {
            TopicDAO dao = getInstance(context).topicDAO();
            SubtopicDAO subDao = getInstance(context).subtopicDAO();
            JSONObject episode = topics.getJSONObject(i);

            int id = episode.getInt("id");
            String name = episode.getString("name");
            int start = episode.getInt("start");
            int end = episode.getInt("end");
            int ad = episode.getInt("ad");
            int communityContribution = episode.getInt("community_contribution");
            int episodeId = episode.getInt("episode_id");
            JSONArray array = episode.getJSONArray("subtopics");
            for (int j = 0; j < array.length(); j++) {
                JSONObject subtopic = array.getJSONObject(j);
                int subId = subtopic.getInt("id");
                int topicId = subtopic.getInt("topic_id");
                String subName = subtopic.getString("name");
                subDao.insert(new Subtopic(subId, subName, topicId));
            }

            dao.insert(new Topic(id, name, start, end, ad, communityContribution, episodeId));
        }

        private static JSONArray loadJSONArray(Context context, int id) {
            StringBuilder builder = new StringBuilder();
            InputStream in = context.getResources().openRawResource(id);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    line = line.replace("&amp;","&");
                    builder.append(line);
                }

                JSONObject json = new JSONObject(builder.toString());
                return json.getJSONArray("data");

            } catch (IOException | JSONException exception) {
                exception.printStackTrace();
            }
            return null;
        }
    }
}
