package sprechstunde.community.themenschaedel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.room.Repository;

public class TopicViewModel extends AndroidViewModel {

    private final Repository mRepository;

    public TopicViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
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

    public LiveData<List<Subtopic>> searchForSubtopics(String name) {
        return mRepository.searchForSubtopics(name);
    }

}
