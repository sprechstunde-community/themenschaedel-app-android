package sprechstunde.community.themenschaedel.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.room.Repository;

public class ViewModel extends AndroidViewModel {

    private Repository mRepository;
    private LiveData<List<Episode>> mEpisodes;

    public ViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mEpisodes = mRepository.getAllEpisodes();
    }

    public void insert(Episode e){
        mRepository.insert(e);
    }
    public void insert(Topic t){ mRepository.insert(t); }

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

    public LiveData<List<Topic>> getAllTopicsFromEpisode(int number) { return mRepository.getAllTopicsFromEpisode(number); }

    public LiveData<Episode> getEpisode(int number) { return mRepository.getEpisode(number); }

    public LiveData<Episode> getEpisode(String title) { return mRepository.getEpisode(title); }

}
