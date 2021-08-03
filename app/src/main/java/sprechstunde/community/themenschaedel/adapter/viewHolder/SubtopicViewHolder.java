package sprechstunde.community.themenschaedel.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.SubtopicAdapter;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class SubtopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final RecyclerView mRecyclerView;
    private final List<Subtopic> mSubtopics;

    public SubtopicViewHolder(@NonNull View itemView, List<Subtopic> subtopics) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_sugg_topic_subtopics_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_topic_subtopics_number);
        mRecyclerView = itemView.findViewById(R.id.list_item_sugg_topic_subtopics_recyclerview);
        mSubtopics = subtopics;

        SubtopicAdapter adapter = new SubtopicAdapter(subtopics);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

    public void setTopicValues(Topic topic) {
        getName().setText(topic.getName());
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + topic.getEpisode();
        getNumber().setText(number);
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
