package sprechstunde.community.themenschaedel.model.topic;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class TopicWithSubtopic {

    @Embedded
    private Topic mTopic;

    @Relation(
            parentColumn = "id",
            entityColumn = "id_topic"
    )
    private List<Subtopic> mSubtopics;

    public Topic getTopic() {
        return mTopic;
    }

    public void setTopic(Topic topic) {
        this.mTopic = topic;
    }

    public List<Subtopic> getSubtopics() {
        return mSubtopics;
    }

    public void setSubtopics(List<Subtopic> subtopics) {
        mSubtopics = subtopics;
    }
}
