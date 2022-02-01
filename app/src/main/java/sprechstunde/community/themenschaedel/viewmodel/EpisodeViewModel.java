package sprechstunde.community.themenschaedel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.room.Repository;

public class EpisodeViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<List<Episode>> mEpisodes;
    private final MutableLiveData<Episode> selected = new MutableLiveData<>();
    private int mCurrentPage = 1;

    public EpisodeViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mEpisodes = mRepository.getAllEpisodes();
    }

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        mCurrentPage = currentPage;
    }

    public void selectedEpisode(Episode episode) {
        selected.setValue(episode);
    }

    public LiveData<Episode> getSelected() {
        return selected;
    }

    public void insert(Episode e) {
        mRepository.insert(e);
    }

    public void update(Episode e) {
        mRepository.update(e);
    }

    public void delete(Episode e) {
        mRepository.delete(e);
    }

    public void deleteAllEpisode() {
        mRepository.deleteAllEpisodes();
    }

    public LiveData<List<Episode>> getAllEpisodes() {
        return mRepository.getAllEpisodes();
    }

    public LiveData<Episode> getEpisode(int id) {
        return mRepository.getEpisode(id);
    }

    public LiveData<Episode> getEpisodeByNumber (int number) {
        return mRepository.getEpisodeByNumber(number);
    }

    public LiveData<Episode> getEpisode(String title) {
        return mRepository.getEpisode(title);
    }

    public LiveData<List<Episode>> searchForEpisodes(String title) {
        return mRepository.searchForEpisodes(title);
    }
}
