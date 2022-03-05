package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "session_table")
public class SessionData {

    @PrimaryKey
    @SerializedName("session_id")
    @ColumnInfo(name = "session_id")
    private int mId;

    @SerializedName("refresh_token")
    @ColumnInfo(name = "refresh_token")
    private String mRefreshToken;

    @SerializedName("access_token")
    @ColumnInfo(name = "access_token")
    private String mAccessToken;

    @SerializedName("myself")
    @Embedded
    private User mMyself;

    @Ignore
    public SessionData() {

    }

    public SessionData(int id, String refreshToken, String accessToken, User myself) {
        mId = id;
        mRefreshToken = refreshToken;
        mAccessToken = accessToken;
        mMyself = myself;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getRefreshToken() {
        return mRefreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        mRefreshToken = refreshToken;
    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public User getMyself() {
        return mMyself;
    }

    public void setMyself(User myself) {
        mMyself = myself;
    }
}
