package sprechstunde.community.themenschaedel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.room.Repository;

public class HostViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<List<Host>> mHosts;

    public HostViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mHosts = mRepository.getAllHosts();
    }

    public void insert(Host h) {
        mRepository.insert(h);
    }

    public void update(Host h) {
        mRepository.update(h);
    }

    public void delete(Host h) {
        mRepository.delete(h);
    }

    public void deleteAllHosts() {
        mRepository.deleteAllHosts();
    }

    public LiveData<List<Host>> getAllHosts() {
        return mRepository.getAllHosts();
    }
}
