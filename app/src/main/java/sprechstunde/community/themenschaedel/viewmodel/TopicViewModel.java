package sprechstunde.community.themenschaedel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;
import sprechstunde.community.themenschaedel.room.Repository;

public class TopicViewModel extends AndroidViewModel {
    private final Repository mRepository;
    private int mCurrentPage = 1;

    public TopicViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public void insert(Topic t) {
        mRepository.insert(t);
    }

    public void insert(Subtopic st) {
        mRepository.insert(st);
    }

    public void update(Topic t) {
        mRepository.update(t);
    }

    public void delete(Topic t) {
        mRepository.delete(t);
    }

    public void deleteAllTopic() {
        mRepository.deleteAllTopics();
    }

    public LiveData<Topic> getTopicById(int topicId) {
        return mRepository.getTopicById(topicId);
    }

    public LiveData<List<Topic>> getAllTopics() {
        return mRepository.getAllTopics();
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopics() {
        return mRepository.getAllTopicsWithSubtopics();
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsAllWithoutAds() {
        return mRepository.getAllTopicsWithSubtopicsAllWithoutAds();
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsCommunityAndAds(int community, int ad) {
        return mRepository.getAllTopicsWithSubtopicsCommunityAndAds(community, ad);
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsCommunity(int community) {
        return mRepository.getAllTopicsWithSubtopicsCommunity(community);
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsOnlyAds() {
        return mRepository.getAllTopicsWithSubtopicsOnlyAds();
    }

    public LiveData<List<Subtopic>> getAllSubtopics() {
        return mRepository.getAllSubtopics();
    }

    public LiveData<List<Subtopic>> getAllSubtopicsFromTopic(int number) {
        return mRepository.getAllSubtopicsFromTopic(number);
    }

    public LiveData<List<Topic>> getAllTopicsFromEpisode(int number) {
        return mRepository.getAllTopicsFromEpisode(number);
    }

    public LiveData<List<Topic>> searchForTopics(String name) {
        return mRepository.searchForTopics(name);
    }

    public LiveData<List<TopicWithSubtopic>> searchForSubtopics(String name) {
        return mRepository.searchForSubtopics(name);
    }

    public LiveData<List<TopicWithSubtopic>> getAllTopicsWithSubtopicsFromEpisode(int number) {
        return mRepository.getAllTopicsWithSubtopicsFromEpisode(number);
    }

}
