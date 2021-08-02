package sprechstunde.community.themenschaedel.adapter.viewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.data.Topic;

public class SubtopicDetailsViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private final TextView mNumber;
    private final ImageView mIcon;
    private final RecyclerView mSubtopics;

    public SubtopicDetailsViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item_sugg_details_subtopics_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_details_subtopics_number);
        mIcon = itemView.findViewById(R.id.list_item_sugg_details_subtopics_icon);
        mSubtopics = itemView.findViewById(R.id.list_item_sugg_details_subtopics_recyclerview);

    }

    public void setTopicValues(Topic topic) {
        getTitle().setText(topic.getTitle());
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + topic.getEpisode();
        getNumber().setText(number);
    }

    public TextView getTitle() {
        return mTitle;
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
