package sprechstunde.community.themenschaedel.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.User;
import sprechstunde.community.themenschaedel.room.Repository;

public class UserViewModel extends AndroidViewModel {

    private final Repository mRepository;
    private final LiveData<List<User>> mUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        mRepository = new Repository(application);
        mUsers = mRepository.getAllUsers();
    }

    public void insert(User u) {
        mRepository.insert(u);
    }

    public void update(User u) {
        mRepository.update(u);
    }

    public void delete(User u) {
        mRepository.delete(u);
    }

    public void deleteAllUsers() {
        mRepository.deleteAllUsers();
    }

    public LiveData<List<User>> getAllUsers() {
        return mRepository.getAllUsers();
    }
}
