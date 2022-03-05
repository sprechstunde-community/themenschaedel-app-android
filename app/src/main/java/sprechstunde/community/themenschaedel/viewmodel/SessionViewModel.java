package sprechstunde.community.themenschaedel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import sprechstunde.community.themenschaedel.model.SessionData;
import sprechstunde.community.themenschaedel.model.User;
import sprechstunde.community.themenschaedel.room.Repository;

public class SessionViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<SessionData> mSessionData;

    public SessionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mSessionData = mRepository.getSessionData();
    }

    public LiveData<SessionData> getSessionData() {
        return mRepository.getSessionData();
    }

    public LiveData<User> getMyself() {
        return mRepository.getMyself();
    }

    public void insert(SessionData s) {
        mRepository.insert(s);
    }

    public void insertMySelf (User u) {
        mRepository.insertMySelf(u);
    }

    public void update(SessionData s) {
        mRepository.update(s);
    }

    public void delete(SessionData s) {
        mRepository.delete(s);
    }

    public void deleteToken() {
        mRepository.deleteToken();
    }

}
