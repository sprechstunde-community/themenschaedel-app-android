package sprechstunde.community.themenschaedel.adapter.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.TopicWithSubtopic;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TOPIC = 1;
    private static final int TYPE_TOPIC_WITH_DETAILS = 2;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS = 3;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS = 4;

    private final MainActivity mMainActivity;
    private List<TopicWithSubtopic> mTopics;
    private TopicViewModel mTopicViewModel;
    private EpisodeViewModel mEpisodeViewModel;
    private boolean mShowDetails;

    public TopicAdapter(List<TopicWithSubtopic> topics, MainActivity mainActivity) {
        mTopics = topics;
        mMainActivity = mainActivity;
    }

    public boolean isShowDetails() {
        return mShowDetails;
    }

    public void setShowDetails(boolean showDetails) {
        mShowDetails = showDetails;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        switch (viewType) {
            case TYPE_TOPIC_WITH_DETAILS: {
                view = LayoutInflater.from(mMainActivity).inflate(R.layout.list_item_topic_detail, viewGroup, false);
                return new TopicDetailsViewHolder(view);
            }
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS: {
                view = LayoutInflater.from(mMainActivity).inflate(R.layout.list_item_topic_with_subtopic_detail, viewGroup, false);
                return new TopicDetailsViewHolderWithSubtopic(view);
            }
            case TYPE_TOPIC: {
                view = LayoutInflater.from(mMainActivity).inflate(R.layout.list_item_topic, viewGroup, false);
                return new TopicViewHolder(view);
            }
            case TYPE_TOPIC_WITH_SUBTOPICS:
            default: {
                view = LayoutInflater.from(mMainActivity).inflate(R.layout.list_item_topic_with_subtopic, viewGroup, false);
                return new TopicViewHolderWithSubtopic(view);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mTopicViewModel == null) {
            mTopicViewModel = new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(TopicViewModel.class);
        }
        if(mEpisodeViewModel == null) {
            mEpisodeViewModel = new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(EpisodeViewModel.class);
        }
    }

    @Override
    public int getItemViewType(int position) {
        List<Subtopic> subtopics = mTopics.get(position).getSubtopics();

        if (subtopics != null && subtopics.size() > 0) {
            return mShowDetails ? TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS : TYPE_TOPIC_WITH_SUBTOPICS;
        } else {
            return mShowDetails ? TYPE_TOPIC_WITH_DETAILS : TYPE_TOPIC;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int position) {

        mEpisodeViewModel.getEpisode(mTopics.get(position).getTopic().getEpisode()).observe(mMainActivity, episode -> {

        switch (getItemViewType(position)) {
            case TYPE_TOPIC_WITH_DETAILS: {
                ((TopicDetailsViewHolder) viewHolder).setTopicValues(mTopics.get(position).getTopic(), episode.getNumber());
            } break;
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS: {
                ((TopicDetailsViewHolderWithSubtopic) viewHolder).setTopicValues(mTopics.get(position).getSubtopics(), mTopics.get(position).getTopic(), episode.getNumber());
            } break;
            case TYPE_TOPIC: {
                ((TopicViewHolder) viewHolder).setTopicValues(mTopics.get(position).getTopic(), episode.getNumber());
            } break;
            case TYPE_TOPIC_WITH_SUBTOPICS:
            default: {
                ((TopicViewHolderWithSubtopic) viewHolder).setTopicValues(mTopics.get(position).getSubtopics(), mTopics.get(position).getTopic(), episode.getNumber());
            }
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

    public void setTopics(List<TopicWithSubtopic> topics) {
        mTopics = topics;
    }
}
