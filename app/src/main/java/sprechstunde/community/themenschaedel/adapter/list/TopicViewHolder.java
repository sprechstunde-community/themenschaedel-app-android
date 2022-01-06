package sprechstunde.community.themenschaedel.adapter.list;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;
import sprechstunde.community.themenschaedel.view.topic.TopicFragmentDirections;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;

public class TopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final ImageButton mArrow;
    private final RecyclerView mRecyclerView;

    public TopicViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_sugg_topic_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_topic_number);
        mArrow = itemView.findViewById(R.id.list_item_sugg_topic_arrow);
        mRecyclerView = itemView.findViewById(R.id.list_item_sugg_topic_recyclerview);
        mArrow.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }

    public void setTopicValues(List<Subtopic> subtopics, Topic topic) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mName.getLayoutParams();
        getName().setText(topic.getName());
        getName().setTypeface(null, Typeface.NORMAL);
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + topic.getEpisode();
        getNumber().setText(number);
        params.leftMargin = 50;

        mArrow.setOnClickListener(v -> {
            if (mRecyclerView.getVisibility() == View.VISIBLE) {
                mRecyclerView.setVisibility(View.GONE);
            } else if (subtopics != null && subtopics.size() > 0) {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        if (subtopics != null && subtopics.size() > 0) {
            params.leftMargin = 0;
            mArrow.setVisibility(View.VISIBLE);
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
