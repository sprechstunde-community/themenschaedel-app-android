package sprechstunde.community.themenschaedel.adapter.list;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;

public class TopicViewHolderWithSubtopic extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final RecyclerView mRecyclerView;

    public TopicViewHolderWithSubtopic(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_topic_with_subtopic_title);
        mNumber = itemView.findViewById(R.id.list_item_topic_with_subtopic_number);
        mRecyclerView = itemView.findViewById(R.id.list_item_topic_with_subtopic_recyclerview);
    }

    public void setTopicValues(List<Subtopic> subtopics, Topic topic, int episodeNumber) {
        getName().setText(topic.getName());
        getName().setTypeface(null, Typeface.NORMAL);
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + episodeNumber;
        getNumber().setText(number);

        if (subtopics != null && subtopics.size() > 0) {
            SubtopicAdapter adapter = new SubtopicAdapter(subtopics);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }

    public TextView getName() {
        return mName;
    }

    public TextView getNumber() {
        return mNumber;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
