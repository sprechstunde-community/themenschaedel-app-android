package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "topic_table")
public class Topic {

    @PrimaryKey
    @ColumnInfo(name="id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    String mName;

    @ColumnInfo(name = "start")
    @SerializedName("start")
    int mStart;

    @ColumnInfo(name = "end")
    @SerializedName("end")
    int mEnd;

    @ColumnInfo(name = "ad")
    @SerializedName("ad")
    boolean mAd;

    @ColumnInfo(name = "episode_id")
    @SerializedName("episode_id")
    int mEpisode;

    public Topic (int id, String name, int start, int end, boolean ad, int episode) {
        mId = id;
        mName = name;
        mStart = start;
        mEnd = end;
        mAd = ad;
        mEpisode = episode;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getStart() {
        return mStart;
    }

    public void setStart(int start) {
        mStart = start;
    }

    public int getEnd() {
        return mEnd;
    }

    public void setEnd(int end) {
        mEnd = end;
    }

    public boolean isAd() {
        return mAd;
    }

    public void setAd(boolean ad) {
        mAd = ad;
    }

    public int getEpisode() {
        return mEpisode;
    }

    public void setEpisode(int episode) {
        mEpisode = episode;
    }
}
