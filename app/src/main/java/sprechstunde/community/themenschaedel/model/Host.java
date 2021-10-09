package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "host_table")
public class Host {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "id")
    private int mId;

    @SerializedName("name")
    @ColumnInfo(name = "name")
    private String mName;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String mDescription;

    @SerializedName("profile_picture")
    @ColumnInfo(name = "profile_picture")
    private String mImage;

    public Host(int id, String name, String description, String image) {
        mId = id;
        mName = name;
        mDescription = description;
        mImage = image;
    }

    @Ignore
    public Host(String name) {
        mName = name;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
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

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }
}
