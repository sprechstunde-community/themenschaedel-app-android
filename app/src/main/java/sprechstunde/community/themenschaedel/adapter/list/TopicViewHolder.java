package sprechstunde.community.themenschaedel.adapter.list;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class TopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final RecyclerView mRecyclerView;

    public TopicViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_sugg_topic_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_topic_number);
        mRecyclerView = itemView.findViewById(R.id.list_item_sugg_topic_recyclerview);
        mRecyclerView.setVisibility(View.GONE);
    }

    public void setTopicValues(List<Subtopic> subtopics, Topic topic) {
        getName().setText(topic.getName());
        // getName().setTextColor(ResourcesCompat.getColor(itemView.getResources(), R.color.onSurface, null));
        getName().setTypeface(null, Typeface.NORMAL);
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + topic.getEpisode();
        getNumber().setText(number);

        if(subtopics != null && subtopics.size() > 0) {
            getName().setTypeface(null, Typeface.BOLD);
            // getName().setTextColor(ResourcesCompat.getColor(itemView.getResources(), R.color.onBackgroundSecondary, null));
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
