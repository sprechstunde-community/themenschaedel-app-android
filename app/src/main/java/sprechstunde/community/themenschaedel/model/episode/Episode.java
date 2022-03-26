package sprechstunde.community.themenschaedel.model.episode;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import sprechstunde.community.themenschaedel.model.Host;
import sprechstunde.community.themenschaedel.model.topic.Topic;

@Entity(tableName = "episode_table")
public class Episode {

    @PrimaryKey
    @ColumnInfo(name = "episodeNumber")
    @SerializedName("episodeNumber")
    public int mEpisodeNumber;

    @SerializedName("title")
    @ColumnInfo(name = "title")
    private String mTitle;

    @SerializedName("subtitle")
    @ColumnInfo(name = "subtitle")
    private String mSubtitle;

    @SerializedName("published_at")
    @ColumnInfo(name = "date")
    private String mDate;

    @ColumnInfo(name = "image")
    @SerializedName("image")
    private String mImage;

    @ColumnInfo(name = "duration")
    @SerializedName("duration")
    private String mDuration;

    @ColumnInfo(name = "verified")
    @SerializedName("verified")
    private boolean mVerified;

    @ColumnInfo(name = "claimed")
    @SerializedName("claimed")
    private boolean mClaimed;

    @ColumnInfo(name = "upvotes")
    @SerializedName("upvotes")
    private int mUpvotes;

    @ColumnInfo(name = "downvotes")
    @SerializedName("downvotes")
    private int mDownvotes;

    @SerializedName("isFavorite")
    private boolean mIsFavorite;

    @Ignore
    @SerializedName("topic")
    private ArrayList<Topic> mTopics;

    @Ignore
    @SerializedName("person")
    private ArrayList<Host> mHosts;

    public Episode() {

    }

    public Episode(String title, String subtitle, String date, int number, String image, String duration, boolean claimed, boolean verified, int upvotes, int downvotes, boolean isFavorite) {
        mTitle = title;
        mSubtitle = subtitle;
        mDate = date;
        mEpisodeNumber = number;
        mImage = image;
        mDuration = duration;
        mVerified = verified;
        mClaimed = claimed;
        mUpvotes = upvotes;
        mDownvotes = downvotes;
        mIsFavorite = isFavorite;
    }

    @Ignore
    public Episode(String title, String date, int number, String duration) {
        mTitle = title;
        mDate = date;
        mEpisodeNumber = number;
        mDuration = duration;
    }

    public String getSubtitle() {
        return mSubtitle;
    }

    public void setSubtitle(String subtitle) {
        this.mSubtitle = subtitle;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() { return mDate; }

    public void setDate(String date) { mDate = date; }

    public int getEpisodeNumber() {
        return mEpisodeNumber;
    }

    public void setEpisodeNumber(int number) {
        mEpisodeNumber = number;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String img) {
        mImage = img;
    }

    public String getDuration() { return mDuration; }

    public void setDuration(String length) { mDuration = length; }

    public boolean getVerified() {
        return mVerified;
    }

    public void setVerified(boolean verified) {
        mVerified = verified;
    }

    public boolean isClaimed() {
        return mClaimed;
    }

    public void setClaimed(boolean claimed) {
        mClaimed = claimed;
    }

    public int getUpvotes() {
        return mUpvotes;
    }

    public void setUpvotes(int upvotes) {
        mUpvotes = upvotes;
    }

    public int getDownvotes() {
        return mDownvotes;
    }

    public void setDownvotes(int downvotes) {
        mDownvotes = downvotes;
    }

    public boolean isIsFavorite() {
        return mIsFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        mIsFavorite = isFavorite;
    }

    public ArrayList<Topic> getTopics() {
        return mTopics;
    }

    public void setTopics(ArrayList<Topic> topics) {
        mTopics = topics;
    }

    public ArrayList<Host> getHosts() {
        return mHosts;
    }

    public void setHosts(ArrayList<Host> hosts) {
        mHosts = hosts;
    }
}