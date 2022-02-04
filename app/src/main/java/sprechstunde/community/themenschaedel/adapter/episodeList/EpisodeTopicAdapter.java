package sprechstunde.community.themenschaedel.adapter.episodeList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;

public class EpisodeTopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TOPIC = 1;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS = 2;

    private final MainActivity mActivity;
    private final List<TopicWithSubtopic> mTopics;

    public EpisodeTopicAdapter(List<TopicWithSubtopic> topics, MainActivity context) {
        mTopics = topics;
        mActivity = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_episode_topic, viewGroup, false);
        return new EpisodeTopicViewHolder(view);

    }

    @Override
    public int getItemViewType(int position) {
        if (mTopics.get(position).getTopic().getSubtopics() != null && mTopics.get(position).getTopic().getSubtopics().size() > 0) {
            return TYPE_TOPIC_WITH_SUBTOPICS;
        } else {
            return TYPE_TOPIC;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        List<Subtopic> subtopics =  mTopics.get(position).getSubtopics();
        ((EpisodeTopicViewHolder) viewHolder).setTopicValues(mTopics.get(position).getSubtopics(), mTopics.get(position).getTopic(), mActivity);
        viewHolder.itemView.setOnClickListener(v -> {
            RecyclerView recyclerView = ((EpisodeTopicViewHolder) viewHolder).getRecyclerView();
            if (recyclerView.getVisibility() == View.VISIBLE) {
                recyclerView.setVisibility(View.GONE);
            } else if (subtopics != null && subtopics.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    public List<TopicWithSubtopic> getTopics() {
        return mTopics;
    }
}
