package sprechstunde.community.themenschaedel.adapter.list;

import android.util.Log;
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
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.TopicWithSubtopic;
import sprechstunde.community.themenschaedel.view.topic.BottomSheetDialogFilterFragment;
import sprechstunde.community.themenschaedel.viewmodel.TopicViewModel;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TOPIC = 1;
    private static final int TYPE_TOPIC_WITH_DETAILS = 2;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS = 3;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS = 4;

    private final MainActivity mMainActivity;
    private List<TopicWithSubtopic> mTopics;
    private TopicViewModel mViewModel;
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
            case TYPE_TOPIC_WITH_DETAILS:
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS: {
                view = LayoutInflater.from(mMainActivity).inflate(R.layout.list_item_suggested_topic_details, viewGroup, false);
                return new TopicDetailsViewHolder(view);
            }
            case TYPE_TOPIC_WITH_SUBTOPICS:
            default: {
                view = LayoutInflater.from(mMainActivity).inflate(R.layout.list_item_suggested_topic, viewGroup, false);
                return new TopicViewHolder(view);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (mViewModel == null) {
            mViewModel = new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(TopicViewModel.class);
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
        switch (getItemViewType(position)) {
            case TYPE_TOPIC_WITH_DETAILS:
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS: {
                ((TopicDetailsViewHolder) viewHolder).setTopicValues(mTopics.get(position).getSubtopics(), mTopics.get(position).getTopic());
                ((TopicDetailsViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                    RecyclerView recyclerView = ((TopicDetailsViewHolder) viewHolder).getRecyclerView();
                    if (recyclerView.getVisibility() == View.VISIBLE) {
                        recyclerView.setVisibility(View.GONE);
                    } else if (mTopics.get(position).getSubtopics() != null && mTopics.get(position).getSubtopics().size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
                break;
            }
            case TYPE_TOPIC_WITH_SUBTOPICS:
            case TYPE_TOPIC:
            default: {
                ((TopicViewHolder) viewHolder).setTopicValues(mTopics.get(position).getSubtopics(), mTopics.get(position).getTopic());
                ((TopicViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                    RecyclerView recyclerView = ((TopicViewHolder) viewHolder).getRecyclerView();
                    if (recyclerView.getVisibility() == View.VISIBLE) {
                        recyclerView.setVisibility(View.GONE);
                    } else if (mTopics.get(position).getSubtopics() != null && mTopics.get(position).getSubtopics().size() > 0) {
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                });
                break;
            }
        }
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
