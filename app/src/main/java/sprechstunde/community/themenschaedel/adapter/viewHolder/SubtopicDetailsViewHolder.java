package sprechstunde.community.themenschaedel.adapter.viewHolder;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Topic;

public class SubtopicDetailsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final ImageView mIcon;
    private final RecyclerView mSubtopics;

    private final Drawable mCommunity;
    private final Drawable mBoys;

    public SubtopicDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_sugg_details_subtopics_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_details_subtopics_number);
        mIcon = itemView.findViewById(R.id.list_item_sugg_details_subtopics_icon);
        mSubtopics = itemView.findViewById(R.id.list_item_sugg_details_subtopics_recyclerview);

        mCommunity = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_community, null);
        mBoys = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_boys, null);
    }

    public void setTopicValues(Topic topic) {
        getName().setText(topic.getName());
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + topic.getEpisode();
        getNumber().setText(number);

        if(topic.getCommunityContribution() == 1) {
            mIcon.setBackground(mCommunity);
        } else {
            mIcon.setBackground(mBoys);
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

    public RecyclerView getSubtopics() {
        return mSubtopics;
    }

}
