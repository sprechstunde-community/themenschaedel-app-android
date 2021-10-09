package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;

@Entity(tableName = "episode_fts_table")
@Fts4(contentEntity = Episode.class)
public class EpisodeFTS {

    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "subtitle")
    private String mSubtitle;

    @ColumnInfo(name = "description")
    private String mDescription;

    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "number")
    private int mNumber;

    @ColumnInfo(name = "image")
    private String mImage;

    @ColumnInfo(name = "duration")
    private String mDuration;

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

    public void setImage(String img) {
        mImage = img;
    }

    public String getDuration() { return mDuration; }

    public void setDuration(String length) { mDuration = length; }
}
