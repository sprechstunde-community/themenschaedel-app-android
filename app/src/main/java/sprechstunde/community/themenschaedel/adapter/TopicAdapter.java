package sprechstunde.community.themenschaedel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;
import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.viewHolder.SubtopicDetailsViewHolder;
import sprechstunde.community.themenschaedel.adapter.viewHolder.SubtopicViewHolder;
import sprechstunde.community.themenschaedel.adapter.viewHolder.TopicDetailsViewHolder;
import sprechstunde.community.themenschaedel.adapter.viewHolder.TopicViewHolder;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TOPIC = 1;
    private static final int TYPE_TOPIC_WITH_DETAILS_COMMUNITY = 2;
    private static final int TYPE_TOPIC_WITH_DETAILS_BOYS = 3;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS = 4;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS_COMMUNITY = 5;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS_BOYS = 6;

    private Context mContext;
    private List<Topic> mTopics;

    public TopicAdapter(List<Topic> topics, Context context) {
        mTopics = topics;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_TOPIC_WITH_DETAILS_COMMUNITY:
            case TYPE_TOPIC_WITH_DETAILS_BOYS: {
                    view = LayoutInflater.from(mContext).inflate(R.layout.list_item_suggested_topic_details, viewGroup, false);
                    return new TopicDetailsViewHolder(view);
                }
            case TYPE_TOPIC_WITH_SUBTOPICS: {
                view = LayoutInflater.from(mContext).inflate(R.layout.list_item_suggested_topic_with_subtopics, viewGroup, false);

                List<Subtopic> subtopics = new LinkedList<>();
                subtopics.add(new Subtopic("Subtopic 1"));
                subtopics.add(new Subtopic("Subtopic 2"));
                subtopics.add(new Subtopic("Subtopic 3"));

                return new SubtopicViewHolder(view, subtopics);
            }
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS_COMMUNITY:
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS_BOYS: {
                view = LayoutInflater.from(mContext).inflate(R.layout.list_item_suggested_topic_with_subtopics_details, viewGroup, false);
                return new SubtopicDetailsViewHolder(view);
            }
            default: {
                view = LayoutInflater.from(mContext).inflate(R.layout.list_item_suggested_topic, viewGroup, false);
                return new TopicViewHolder(view);
            }
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
        switch (getItemViewType(position)) {
            case TYPE_TOPIC_WITH_DETAILS_COMMUNITY:
            case TYPE_TOPIC_WITH_DETAILS_BOYS: {
                ((TopicDetailsViewHolder) viewHolder).setTopicValues(mTopics.get(position));
                break;
            }
            case TYPE_TOPIC_WITH_SUBTOPICS: {
                ((SubtopicViewHolder) viewHolder).setTopicValues(mTopics.get(position));
                break;
            }
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS_COMMUNITY:
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS_BOYS: {
                ((SubtopicDetailsViewHolder) viewHolder).setTopicValues(mTopics.get(position));
                break;
            }
            default: {
                ((TopicViewHolder) viewHolder).setTopicValues(mTopics.get(position));
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return mTopics.size();
    }

}
