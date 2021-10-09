package sprechstunde.community.themenschaedel.adapter.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.model.ViewModel;

public class TopicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_TOPIC = 1;
    private static final int TYPE_TOPIC_WITH_DETAILS = 2;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS = 3;
    private static final int TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS = 4;

    private final Context mContext;
    private final List<Topic> mTopics;
    private ViewModel mViewModel;

    public TopicAdapter(List<Topic> topics, Context context) {
        mTopics = topics;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;

        switch (viewType) {
            case TYPE_TOPIC_WITH_DETAILS:
            case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS:{
                    view = LayoutInflater.from(mContext).inflate(R.layout.list_item_suggested_topic_details, viewGroup, false);
                    return new TopicDetailsViewHolder(view);
                }
            case TYPE_TOPIC_WITH_SUBTOPICS:
            default:{
                view = LayoutInflater.from(mContext).inflate(R.layout.list_item_suggested_topic, viewGroup, false);
                return new TopicViewHolder(view);
            }
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if(mViewModel == null){
            mViewModel = new ViewModelProvider((ViewModelStoreOwner) recyclerView.getContext()).get(ViewModel.class);
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

        mViewModel.getAllSubtopicsFromTopic(mTopics.get(position).getId()).observe((LifecycleOwner) mContext, subtopics -> {
            switch (getItemViewType(position)) {
                case TYPE_TOPIC_WITH_DETAILS:
                case TYPE_TOPIC_WITH_SUBTOPICS_AND_DETAILS: {
                    ((TopicDetailsViewHolder) viewHolder).setTopicValues(subtopics, mTopics.get(position));
                    ((TopicDetailsViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                        RecyclerView recyclerView = ((TopicDetailsViewHolder) viewHolder).getRecyclerView();
                        if (recyclerView.getVisibility() == View.VISIBLE) {
                            recyclerView.setVisibility(View.GONE);
                        } else if (subtopics != null && subtopics.size() > 0) {
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                    });
                    break;
                }
                case TYPE_TOPIC_WITH_SUBTOPICS:
                case TYPE_TOPIC:
                default:
                 {
                    ((TopicViewHolder) viewHolder).setTopicValues(subtopics, mTopics.get(position));
                     ((TopicViewHolder) viewHolder).itemView.setOnClickListener(v -> {
                         RecyclerView recyclerView = ((TopicViewHolder) viewHolder).getRecyclerView();
                         if (recyclerView.getVisibility() == View.VISIBLE) {
                             recyclerView.setVisibility(View.GONE);
                         } else if (subtopics != null && subtopics.size() > 0) {
                             recyclerView.setVisibility(View.VISIBLE);
                         }
                     });
                    break;
                }
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
