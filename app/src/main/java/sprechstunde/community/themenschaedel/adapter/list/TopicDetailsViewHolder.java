package sprechstunde.community.themenschaedel.adapter.list;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.topic.Topic;

public class TopicDetailsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;
    private final ImageView mIcon;

    private final Drawable mCommunity;
    private final Drawable mBoys;

    public TopicDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_topic_detail_title);
        mNumber = itemView.findViewById(R.id.list_item_topic_detail_number);
        mIcon = itemView.findViewById(R.id.list_item_topic_detail_icon);

        mCommunity = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_community, null);
        mBoys = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_boys, null);
    }

    public void setTopicValues(Topic topic, int episodeNumber) {
        getName().setText(topic.getName());
        getName().setTypeface(null, Typeface.NORMAL);
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + episodeNumber;
        getNumber().setText(number);

        if(topic.getCommunityContribution()) {
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
