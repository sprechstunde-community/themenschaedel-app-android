package sprechstunde.community.themenschaedel.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.room.Repository;

public class ViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<List<Episode>> mEpisodes;
    private final MutableLiveData<Episode> selected = new MutableLiveData<>();

    public ViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mEpisodes = mRepository.getAllEpisodes();
    }


    public void selectedEpisode(Episode episode) {
        selected.setValue(episode);
    }

    public LiveData<Episode> getSelected() {
        return selected;
    }

    public void insert(Episode e){
        mRepository.insert(e);
    }
    public void insert(Topic t){ mRepository.insert(t); }
    public void insert(Subtopic st){ mRepository.insert(st); }

    public void update(Episode e){ mRepository.update(e); }
    public void update(Topic t){
        mRepository.update(t);
    }

    public void delete(Episode e){
        mRepository.delete(e);
    }
    public void delete(Topic t){
        mRepository.delete(t);
    }

    public void deleteAllEpisode(){
        mRepository.deleteAllEpisodes();
    }
    public void deleteAllTopic(){
        mRepository.deleteAllTopics();
    }

    public LiveData<List<Episode>> getAllEpisodes() { return mRepository.getAllEpisodes(); }
    public LiveData<List<Topic>> getAllTopics() { return mRepository.getAllTopics(); }
    public LiveData<List<Subtopic>> getAllSubtopics() { return mRepository.getAllSubtopics(); }

    public LiveData<List<Subtopic>> getAllSubtopicsFromTopic(int number) { return mRepository.getAllSubtopicsFromTopic(number); }
    public LiveData<List<Topic>> getAllTopicsFromEpisode(int number) { return mRepository.getAllTopicsFromEpisode(number); }

    public LiveData<Episode> getEpisode(int number) { return mRepository.getEpisode(number); }

    public LiveData<Episode> getEpisode(String title) { return mRepository.getEpisode(title); }

    public LiveData<List<Episode>> search(String title) {
        return mRepository.search(title); }

}
