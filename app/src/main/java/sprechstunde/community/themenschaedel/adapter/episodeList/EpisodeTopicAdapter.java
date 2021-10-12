package sprechstunde.community.themenschaedel.adapter.episodeList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class EpisodeTopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TOPIC = 1;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS = 2;

    private final Context mContext;
    private final List<Topic> mTopics;

    public EpisodeTopicAdapter(List<Topic> topics, Context context) {
        mTopics = topics;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_episode_topic, viewGroup, false);
        if(viewType == TYPE_TOPIC_WITH_SUBTOPICS) {
            List<Subtopic> subtopics = new LinkedList<>();
            subtopics.add(new Subtopic("Subtopic 1"));
            subtopics.add(new Subtopic("Subtopic 2"));
            subtopics.add(new Subtopic("Subtopic 3"));

            return new EpisodeTopicViewHolder(view, subtopics);
        } else {
            return new EpisodeTopicViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mTopics.get(position).hasSubtopics()) {
            return TYPE_TOPIC_WITH_SUBTOPICS;
        } else {
            return TYPE_TOPIC;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {
        ((EpisodeTopicViewHolder) viewHolder).setTopicValues(mTopics.get(position));
        viewHolder.itemView.setOnClickListener(v -> {
            RecyclerView recyclerView = ((EpisodeTopicViewHolder) viewHolder).getRecyclerView();
            if (recyclerView.getVisibility() == View.VISIBLE) {
                recyclerView.setVisibility(View.GONE);
            } else if (((EpisodeTopicViewHolder) viewHolder).hasSubtopics()) {
                recyclerView.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

    public List<Topic> getTopics() {
        return mTopics;
    }
}
