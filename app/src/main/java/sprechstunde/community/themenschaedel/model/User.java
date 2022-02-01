package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String mName;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "token")
    private String mToken;

    public User(int id, String username, String email, String description, String name, String token) {
        mId = id;
        mUsername = username;
        mEmail = email;
        mDescription = description;
        mName = name;
        mToken = token;
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

    public String setEmail() {
        return mEmail;
    }

    public void getEmail (String email) {
        mEmail = email;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
}
