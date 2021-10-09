package sprechstunde.community.themenschaedel.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "data_remote_keys")
public class ResponseMeta {

    @PrimaryKey
    @SerializedName("current_page")
    private int mCurrentPage;

    @SerializedName("last_page")
    private int mLastPage;

    public int getCurrentPage() {
        return mCurrentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }

    public int getLastPage() {
        return mLastPage;
    }

    public void setLastPage(int lastPage) {
        mLastPage = lastPage;
    }
}
