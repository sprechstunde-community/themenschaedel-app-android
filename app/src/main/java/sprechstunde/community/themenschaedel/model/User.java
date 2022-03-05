package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int mId;

    @SerializedName("username")
    @ColumnInfo(name = "username")
    private String mUsername;

    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String mEmail;

    @SerializedName("role_id")
    @ColumnInfo(name = "role_id")
    private int mRoleId;

    @ColumnInfo(name = "platform")
    private String mPlatform;

    @Ignore
    public User() {

    }

    public User(int id, String username, String email, int roleId, String platform) {
        mId = id;
        mUsername = username;
        mEmail = email;
        mRoleId = roleId;
        mPlatform = platform;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail (String email) {
        mEmail = email;
    }

    public int getRoleId() {
        return mRoleId;
    }

    public void setRoleId(int roleId) {
        mRoleId = roleId;
    }

    public String getPlatform() {
        return mPlatform;
    }

    public void setPlatform(String platform) {
        mPlatform = platform;
    }
}
