package sprechstunde.community.themenschaedel.adapter.list;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class TopicDetailsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final ImageButton mArrow;
    private final ImageView mIcon;
    private final RecyclerView mRecyclerView;

    private final Drawable mCommunity;
    private final Drawable mBoys;

    public TopicDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_sugg_details_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_details_number);
        mArrow = itemView.findViewById(R.id.list_item_sugg_details_arrow);
        mIcon = itemView.findViewById(R.id.list_item_sugg_details_icon);
        mRecyclerView = itemView.findViewById(R.id.list_item_sugg_details_recyclerview);
        mRecyclerView.setVisibility(View.GONE);
        mArrow.setVisibility(View.GONE);
        mCommunity = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_community, null);
        mBoys = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_boys, null);
    }

    public void setTopicValues(List<Subtopic> subtopics, Topic topic, int episodeNumber) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mName.getLayoutParams();
        getName().setText(topic.getName());
        getName().setTypeface(null, Typeface.NORMAL);
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + episodeNumber;
        getNumber().setText(number);

        params.leftMargin = 50;
        if(topic.getCommunityContribution()) {
            mIcon.setBackground(mCommunity);
        } else {
            mIcon.setBackground(mBoys);
        }

        mArrow.setOnClickListener(v -> {
            mArrow.animate().rotation(180).start();
            if (mRecyclerView.getVisibility() == View.VISIBLE) {
                mRecyclerView.setVisibility(View.GONE);
            } else if (subtopics != null && subtopics.size() > 0) {
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });

        if(subtopics != null && subtopics.size() > 0) {
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

    public ImageView getIcon() {
        return mIcon;
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
}
