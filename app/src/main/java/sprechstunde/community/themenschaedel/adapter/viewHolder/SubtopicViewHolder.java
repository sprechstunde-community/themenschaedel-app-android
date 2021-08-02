package sprechstunde.community.themenschaedel.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.PodcastRowAdapter;
import sprechstunde.community.themenschaedel.adapter.SubtopicAdapter;
import sprechstunde.community.themenschaedel.data.Topic;

public class SubtopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private final TextView mNumber;
    private final RecyclerView mRecyclerView;
    private final List<Topic> mSubtopics;

    public SubtopicViewHolder(@NonNull View itemView, List<Topic> subtopics) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item_sugg_topic_subtopics_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_topic_subtopics_number);
        mRecyclerView = itemView.findViewById(R.id.list_item_sugg_topic_subtopics_recyclerview);
        mSubtopics = subtopics;

        SubtopicAdapter adapter = new SubtopicAdapter(subtopics);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

    public void setTopicValues(Topic topic) {
        getTitle().setText(topic.getTitle());
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + topic.getEpisode();
        getNumber().setText(number);
    }

    public TextView getTitle() {
        return mTitle;
    }

    public TextView getNumber() {
        return mNumber;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
