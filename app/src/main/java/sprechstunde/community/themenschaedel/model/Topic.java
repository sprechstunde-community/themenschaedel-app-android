package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "topic_table")
public class Topic {

    @PrimaryKey
    @ColumnInfo(name="id")
    private int mId;

    @ColumnInfo(name = "name")
    String mName;

    @ColumnInfo(name = "start")
    int mStart;

    @ColumnInfo(name = "end")
    int mEnd;

    @ColumnInfo(name = "duration")
    int mDuration;

    @ColumnInfo(name = "ad")
    boolean mAd;

    @ColumnInfo(name = "episode")
    int mEpisode;

    public Topic (int id, String name, int start, int end, int duration, boolean ad, int episode) {
        mId = id;
        mName = name;
        mStart = start;
        mEnd = end;
        mDuration = duration;
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

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int duration) {
        mDuration = duration;
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
