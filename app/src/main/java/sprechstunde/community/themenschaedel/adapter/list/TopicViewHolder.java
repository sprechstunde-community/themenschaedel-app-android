package sprechstunde.community.themenschaedel.adapter.list;

import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.topic.Topic;

public class TopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mName;
    private final TextView mNumber;

    public TopicViewHolder(@NonNull View itemView) {
        super(itemView);
        mName = itemView.findViewById(R.id.list_item_topic_title);
        mNumber = itemView.findViewById(R.id.list_item_topic_number);
    }

    public void setTopicValues(Topic topic, int episodeNumber) {
        getName().setText(topic.getName());
        getName().setTypeface(null, Typeface.NORMAL);
        String number = itemView.getContext().getString(R.string.list_item_topic_number) + " " + episodeNumber;
        mNumber.setText(number);
    }

    public TextView getName() {
        return mName;
    }

    public TextView getNumber() {
        return mNumber;
    }
}
