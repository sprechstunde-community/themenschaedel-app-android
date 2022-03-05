package sprechstunde.community.themenschaedel.model;

import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {

    @PrimaryKey
    @SerializedName("user_id")
    private int mUserId;

    @SerializedName("refresh_token")
    private String mRefreshToken;

    @SerializedName("access_token")
    private String mAccessToken;

    public int getUserId() {
        return mUserId;
    }

    public void setUserId(int userId) {
        mUserId = userId;
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
}