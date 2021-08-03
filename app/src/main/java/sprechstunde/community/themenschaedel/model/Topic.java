package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
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
    int mAd;

    @ColumnInfo(name = "community_contribution")
    @SerializedName("community_contribution")
    int mCommunityContribution;

    @Ignore
    boolean mSubtopics;

    @ColumnInfo(name = "episode_id")
    @SerializedName("episode_id")
    int mEpisode;

    public Topic (int id, String name, int start, int end, int ad, int communityContribution, int episode) {
        mId = id;
        mName = name;
        mStart = start;
        mEnd = end;
        mAd = ad;
        mCommunityContribution = communityContribution;
        mEpisode = episode;
    }

    @Ignore
    public Topic (String name, int ad, int communityContribution, boolean subtopics, int episode) {
        mName = name;
        mAd = ad;
        mCommunityContribution = communityContribution;
        mSubtopics = subtopics;
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

    public int getAd() {
        return mAd;
    }

    public void setAd(int ad) {
        mAd = ad;
    }

    public int getCommunityContribution() {
        return mCommunityContribution;
    }

    public void setCommunityContribution(int communityContribution) {
        mCommunityContribution = communityContribution;
    }

    public boolean hasSubtopics() {
        return mSubtopics;
    }

    public void setSubtopics(boolean subtopics) {
        mSubtopics = subtopics;
    }

    public int getEpisode() {
        return mEpisode;
    }

    public void setEpisode(int episode) {
        mEpisode = episode;
    }
}
