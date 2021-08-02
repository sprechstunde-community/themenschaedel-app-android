package sprechstunde.community.themenschaedel.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

public class Subtopic {

    @PrimaryKey
    @ColumnInfo(name="id")
    @SerializedName("id")
    private int mId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;

    @ColumnInfo(name="topic_id")
    @SerializedName("topic_id")
    private int mTopicId;

    public Subtopic(int id, String name, int topicId) {
        mId = id;
        mName = name;
        mTopicId = topicId;
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

    public int getTopicId() {
        return mTopicId;
    }

    public void setTopicId(int topicId) {
        mTopicId = topicId;
    }
}