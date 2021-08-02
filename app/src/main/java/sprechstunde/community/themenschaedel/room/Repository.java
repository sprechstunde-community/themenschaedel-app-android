package sprechstunde.community.themenschaedel.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.Topic;

public class Repository {

        private EpisodeDAO mEpisodeDAO;
        private TopicDAO mTopicDAO;

        private LiveData<List<Episode>> mAllEpisodes;
        private LiveData<List<Topic>> mAllTopics;

        /**
         * Constructor
         * Call of the abstarct methods "messageDAO()", "conversationDAO" and "participantDAO" only works because
         * of the builder in class MessengerDatabase
         */
        public Repository(Application _application){
            Database db = Database.getInstance(_application);
            mEpisodeDAO = db.episodeDAO();
            mAllEpisodes = mEpisodeDAO.getAllEpisodes();

            mTopicDAO = db.topicDAO();
            mAllTopics = mTopicDAO.getAllTopics();
        }

        /**
         * Create methods for all the different database operations.
         * Those are the ones that the API exposes to the outside.
         * Make an AsyncTask for each method
         */

        public void insert(Topic topic){ new InsertTopicAsyncTask(mTopicDAO).execute(topic); }

        public void insert(Episode episode){ new InsertEpisodeAsyncTask(mEpisodeDAO).execute(episode); }

        public void update(Topic topic){ mTopicDAO.update(topic); }

        public void update(Episode episode){ new UpdateEpisodeAsyncTask(mEpisodeDAO).execute(episode); }

        public void delete(Topic topic){ mTopicDAO.delete(topic); }

        public void delete(Episode episode){ new DeleteEpisodeAsyncTask(mEpisodeDAO).execute(episode); }

        public void deleteAllTopics(){ mTopicDAO.deleteAllTopics(); }

        public void deleteAllEpisodes(){ mEpisodeDAO.deleteAllEpisodes(); }

        public LiveData<List<Topic>> getAllTopics(){
            return mAllTopics;
        }

        public LiveData<List<Episode>> getAllEpisodes(){
            return mAllEpisodes;
        }

        public LiveData<List<Topic>> getAllTopicsFromEpisode(int number){ return mTopicDAO.getAllTopicsFrom(number); }

        public LiveData<Episode> getEpisode(String name) { return mEpisodeDAO.getEpisode(name); }

        public LiveData<Episode> getEpisode(int number) { return mEpisodeDAO.getEpisode(number); }

    private static class InsertTopicAsyncTask extends AsyncTask<Topic, Void, Void> {
            private final TopicDAO mTopicDAO;

            private InsertTopicAsyncTask(TopicDAO topicDAO){ mTopicDAO = topicDAO; }

            @Override
            protected Void doInBackground(Topic... topics) {
                mTopicDAO.insert(topics[0]);
                return null;
            }
        }

        private static class UpdateEpisodeAsyncTask extends AsyncTask<Episode, Void, Void> {
            private final EpisodeDAO mEpisodeDAO;

            public UpdateEpisodeAsyncTask(EpisodeDAO episodeDAO) { mEpisodeDAO = episodeDAO; }

            @Override
            protected Void doInBackground(Episode... episodes) {
                mEpisodeDAO.update(episodes[0]);
                return null;
            }
        }

        private static class InsertEpisodeAsyncTask extends AsyncTask<Episode, Void, Void> {
            private final EpisodeDAO mEpisodeDAO;

            public InsertEpisodeAsyncTask(EpisodeDAO episodeDAO) {  mEpisodeDAO = episodeDAO; }

            @Override
            protected Void doInBackground(Episode... episodes) {
                mEpisodeDAO.insert(episodes[0]);
                return null;
            }
        }

        private static class DeleteEpisodeAsyncTask extends AsyncTask<Episode, Void, Void> {
            private final EpisodeDAO mEpisodeDAO;

            public DeleteEpisodeAsyncTask(EpisodeDAO episodeDAO) {  mEpisodeDAO = episodeDAO; }

            @Override
            protected Void doInBackground(Episode... episodes) {
                mEpisodeDAO.delete(episodes[0]);
                return null;
            }
        }
}