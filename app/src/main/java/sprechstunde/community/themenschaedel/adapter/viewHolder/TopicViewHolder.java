package sprechstunde.community.themenschaedel.adapter.viewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.data.Topic;

public class TopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private final TextView mNumber;

    public TopicViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item_sugg_topic_title);
        mNumber = itemView.findViewById(R.id.list_item_sugg_topic_number);
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
}
