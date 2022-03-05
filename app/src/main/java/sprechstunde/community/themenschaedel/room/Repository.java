package sprechstunde.community.themenschaedel.room;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.SessionData;
import sprechstunde.community.themenschaedel.model.User;
import sprechstunde.community.themenschaedel.model.episode.EpisodeHostCrossRef;
import sprechstunde.community.themenschaedel.model.episode.EpisodeWithHosts;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;

public class Repository {

    private final EpisodeDAO mEpisodeDAO;
    private final TopicDAO mTopicDAO;
    private final SubtopicDAO mSubtopicDAO;
    private final HostDAO mHostDAO;
    private final UserDAO mUserDAO;
    private final SessionDAO mSessionDAO;

    private final LiveData<List<Episode>> mAllEpisodes;
    private final LiveData<List<Topic>> mAllTopics;
    private final LiveData<List<Subtopic>> mAllSubtopics;
    private final LiveData<List<Host>> mAllHosts;
    private final LiveData<List<User>> mAllUsers;
    private final LiveData<SessionData> mSessionData;

    private final Executor mExecutor = Executors.newSingleThreadExecutor();

    /**
     * Constructor
     * Call of the abstarct methods "messageDAO()", "conversationDAO" and "participantDAO" only works because
     * of the builder in class MessengerDatabase
     */
    public Repository(Application _application) {
        Database db = Database.getInstance(_application);
        mEpisodeDAO = db.episodes();
        mAllEpisodes = mEpisodeDAO.getAllEpisodes();

        mTopicDAO = db.topics();
        mAllTopics = mTopicDAO.getAllTopics();

        mSubtopicDAO = db.subtopics();
        mAllSubtopics = mSubtopicDAO.getAllSubtopics();

        mHostDAO = db.hosts();
        mAllHosts = mHostDAO.getAllHosts();

        mUserDAO = db.users();
        mAllUsers = mUserDAO.getAllUsers();

        mSessionDAO = db.sessionData();
        mSessionData = mSessionDAO.getSessionData();
    }

    /**
     * Create methods for all the different database operations.
     * Those are the ones that the API exposes to the outside.
     */
    public void insert(Subtopic subtopic) {
        mExecutor.execute(() -> mSubtopicDAO.insert(subtopic));
    }

    public void insert(Topic topic) {
        mExecutor.execute(() -> mTopicDAO.insert(topic));
    }

    public void insert(Episode episode) {
        mExecutor.execute(() -> mEpisodeDAO.insert(episode));
    }

    public void insertEpisodeHostCrossRef(EpisodeHostCrossRef crossRef) {
        mExecutor.execute(() -> mEpisodeDAO.insertEpisodeHostCrossRef(crossRef));
    }

    public void insert(Host host) {
        mExecutor.execute(() -> mHostDAO.insert(host));
    }

    public void insert(User user) {
        mExecutor.execute(() -> mUserDAO.insert(user));
    }

    public void insert(SessionData sessionData) {
        mExecutor.execute(() -> mSessionDAO.insert(sessionData));
    }

    public void insertMySelf(User user) {
        mExecutor.execute(() -> mSessionDAO.insertUser(user.getId(), user.getUsername(), user.getEmail(), user.getRoleId()));
    }

    public LiveData<User> getMyself() {
        return mSessionDAO.getMyself();
    }

    public void update(Topic topic) {
        mTopicDAO.update(topic);
    }

    public void update(Episode episode) {
        mExecutor.execute(() -> mEpisodeDAO.update(episode));
    }

    public void update(Host host) {
        mExecutor.execute(() -> mHostDAO.update(host));
    }

    public void update(User user) {
        mExecutor.execute(() -> mUserDAO.update(user));
    }

    public void update(SessionData sessionData) {
        mExecutor.execute(() -> mSessionDAO.update(sessionData));
    }

    public void delete(Topic topic) {
        mTopicDAO.delete(topic);
    }

    public void delete(Episode episode) {
        mExecutor.execute(() -> mEpisodeDAO.delete(episode));
    }

    public void delete(Host host) {
        mHostDAO.delete(host);
    }

    public void delete(User user) {
        mUserDAO.delete(user);
    }

    public void delete(SessionData sessionData) {
        mSessionDAO.delete(sessionData);
    }

    public void deleteToken() { mExecutor.execute(mSessionDAO::deleteToken); }

    public void deleteAllTopics() {
        mTopicDAO.deleteAllTopics();
    }

    public void deleteAllEpisodes() {
        mEpisodeDAO.deleteAllEpisodes();
    }

    public void deleteAllHosts() {
        mHostDAO.deleteAllHosts();
    }

    public void deleteAllUsers() {
        mUserDAO.deleteAllUsers();
    }

    public LiveData<Topic> getTopicById(int topicId) {
        return mTopicDAO.getTopicById(topicId);
    }

    public LiveData<List<Topic>> getAllTopics() {
        return mAllTopics;
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopics() {
        return mTopicDAO.getAllTopicsWithSubtopics();
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsAllWithoutAds() {
        return mTopicDAO.getAllTopicsWithSubtopicsAllWithoutAds();
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsCommunityAndAds(int community, int ad) {
        return mTopicDAO.getAllTopicsWithSubtopicsCommunityAndAds(community, ad);
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsCommunity(int community) {
        return mTopicDAO.getAllTopicsWithSubtopicsCommunity(community);
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsOnlyAds() {
        return mTopicDAO.getAllTopicsWithSubtopicsOnlyAds();
    }

    public LiveData<List<Subtopic>> getAllSubtopics() {
        return mAllSubtopics;
    }

    public LiveData<List<Episode>> getAllEpisodes() {
        return mAllEpisodes;
    }

    public LiveData<List<Host>> getAllHosts() {
        return mAllHosts;
    }

    public LiveData<List<User>> getAllUsers() {
        return mAllUsers;
    }

    public LiveData<SessionData> getSessionData() {
        return mSessionData;
    }

    public LiveData<List<Subtopic>> getAllSubtopicsFromTopic(int number) {
        return mSubtopicDAO.getAllSubtopicsFrom(number);
    }

    public LiveData<List<Topic>> getAllTopicsFromEpisode(int number) {
        return mTopicDAO.getAllTopicsFrom(number);
    }

    public LiveData<Episode> getEpisode(String name) {
        return mEpisodeDAO.getEpisode(name);
    }

    public LiveData<Episode> getEpisodeByNumber (int number) {
        return mEpisodeDAO.getEpisodeByNumber(number);
    }

    public LiveData<List<Episode>> searchForEpisodes(String query) {
        return mEpisodeDAO.search(query);
    }

    public LiveData<EpisodeWithHosts> getEpisodeWithHosts(int number) {
        return mEpisodeDAO.getEpisodeWithHosts(number);
    }

    public LiveData<List<Topic>> searchForTopics(String query) {
        return mTopicDAO.search(query);
    }

    public LiveData<List<TopicWithSubtopic>> searchForSubtopics(String query) {
        return mSubtopicDAO.search(query);
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsFromEpisode(int number) {
        return mTopicDAO.getAllTopicsWithSubtopicsFrom(number);
    }
}
