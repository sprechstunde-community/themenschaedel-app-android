package sprechstunde.community.themenschaedel.adapter.list;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;

public class TopicDetailsViewHolderWithSubtopic extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final ImageView mIcon;
    private final RecyclerView mRecyclerView;

    private final Drawable mCommunity;
    private final Drawable mBoys;

    public TopicDetailsViewHolderWithSubtopic(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_topic_with_subtopic_detail_title);
        mNumber = itemView.findViewById(R.id.list_item_topic_with_subtopic_detail_number);
        mIcon = itemView.findViewById(R.id.list_item_topic_with_subtopic_detail_icon);
        mRecyclerView = itemView.findViewById(R.id.list_item_topic_with_subtopic_detail_recyclerview);

        mCommunity = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_community, null);
        mBoys = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_boys, null);
    }

    public void setTopicValues(List<Subtopic> subtopics, Topic topic, int episodeNumber) {
        getName().setText(topic.getName());
        getName().setTypeface(null, Typeface.NORMAL);
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + episodeNumber;
        getNumber().setText(number);

        if(topic.getCommunityContribution()) {
            mIcon.setBackground(mCommunity);
        } else {
            mIcon.setBackground(mBoys);
        }

        if(subtopics != null && subtopics.size() > 0) {
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
