package sprechstunde.community.themenschaedel.adapter.viewHolder;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Topic;

public class TopicDetailsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final ImageView mIcon;

    private final Drawable mCommunity;
    private final Drawable mBoys;

    public TopicDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_sugg_details_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_details_number);
        mIcon = itemView.findViewById(R.id.list_item_sugg_details_icon);
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

}
