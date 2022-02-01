package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "episode_table")
public class Episode {

    @PrimaryKey
    @SerializedName("id")
    @ColumnInfo(name = "episode_id")
    private int mId;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String mTitle;

    @SerializedName("subtitle")
    @ColumnInfo(name = "subtitle")
    private String mSubtitle;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String mDescription;

    @SerializedName("published_at")
    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "number")
    @SerializedName("episode_number")
    private int mNumber;

    @ColumnInfo(name = "image")
    @SerializedName("image")
    private String mImage;

    @ColumnInfo(name = "duration")
    @SerializedName("duration")
    private String mDuration;

    public Episode(int id, String title, String subtitle, String description, String date, int number, String image, String duration) {
        mId = id;
        mTitle = title;
        mSubtitle = subtitle;
        mDescription = description;
        mDate = date;
        mNumber = number;
        mImage = image;
        mDuration = duration;
    }

    @Ignore
    public Episode(String title, String date, int number, String duration) {
        mTitle = title;
        mDate = date;
        mNumber = number;
        mDuration = duration;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        this.mSubtitle = subtitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() { return mDate; }

    public void setDate(String date) { mDate = date; }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public String getImage() {
        return mImage;
    }

    public void setImg(String img) {
        mImage = img;
    }

    public String getDuration() { return mDuration; }

    public void setDuration(String length) { mDuration = length; }
}