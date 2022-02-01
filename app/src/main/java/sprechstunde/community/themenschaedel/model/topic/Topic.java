package sprechstunde.community.themenschaedel.model.topic;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "topic_table")
public class Topic {

    @PrimaryKey
    @ColumnInfo(name = "id")
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

    @ColumnInfo(name = "community_contribution")
    @SerializedName("community_contribution")
    boolean mCommunityContribution;

    @Ignore
    @SerializedName("subtopics")
    List<Subtopic> mSubtopics;

    @ColumnInfo(name = "episode_id")
    @SerializedName("episode_id")
    int mEpisode;

    public Topic(int id, String name) {
        mId = id;
        mName = name;
    }

    @Ignore
    public Topic(int id, String name, int start, int end, boolean ad, boolean communityContribution, int episode) {
        mId = id;
        mName = name;
        mStart = start;
        mEnd = end;
        mAd = ad;
        mCommunityContribution = communityContribution;
        mEpisode = episode;
    }

    @Ignore
    public Topic(String name, int start, int end, boolean ad, boolean communityContribution, List<Subtopic> subtopics, int episode) {
        mName = name;
        mStart = start;
        mEnd = end;
        mAd = ad;
        mCommunityContribution = communityContribution;
        mSubtopics = subtopics;
        mEpisode = episode;
    }

    @Ignore
    public Topic(String name, boolean ad, boolean communityContribution, List<Subtopic> subtopics, int episode) {
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

    public boolean getAd() {
        return mAd;
    }

    public void setAd(boolean ad) {
        mAd = ad;
    }

    public boolean getCommunityContribution() {
        return mCommunityContribution;
    }

    public void setCommunityContribution(boolean communityContribution) {
        mCommunityContribution = communityContribution;
    }

    public void setSubtopics(List<Subtopic> subtopics) {
        mSubtopics = subtopics;
    }

    public List<Subtopic> getSubtopics() {
        return mSubtopics;
    }

    public int getEpisode() {
        return mEpisode;
    }

    public void setEpisode(int episode) {
        mEpisode = episode;
    }
}
