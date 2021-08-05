package sprechstunde.community.themenschaedel.adapter.episodeList;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.list.SubtopicAdapter;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class EpisodeTopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private final TextView mTime;
    private final ImageView mIcon;
    private final RecyclerView mRecyclerView;
    private boolean mHasSubtopics;

    protected Drawable mAd;
    protected Drawable mCommunity;
    protected Drawable mBoys;

    public EpisodeTopicViewHolder(@NonNull View itemView, List<Subtopic> subtopics) {
        this(itemView);
        mHasSubtopics = true;

        SubtopicAdapter adapter = new SubtopicAdapter(subtopics);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
    }

    public EpisodeTopicViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item_episode_topic_title);
        mTime = itemView.findViewById(R.id.list_item_episode_topic_time);
        mIcon = itemView.findViewById(R.id.list_item_episode_topic_type);
        mRecyclerView = itemView.findViewById(R.id.list_item_episode_topic_recyclerView);
        mRecyclerView.setVisibility(View.GONE);
        mHasSubtopics = false;

        mAd = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_ad, null);
        mCommunity = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_community, null);
        mBoys = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_boys, null);
    }

    public void setTopicValues(Topic topic) {
        getTitle().setText(topic.getName());

        long time = topic.getStart();
        long hours = TimeUnit.SECONDS.toHours(time);
        time -= TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toSeconds(minutes);
        long seconds = TimeUnit.SECONDS.toSeconds(time);

        String startTime = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);
        getTime().setText(startTime);

        if(topic.getAd() == 1) {
            getIcon().setBackground(mAd);
        } else if(topic.getCommunityContribution() == 1) {
            getIcon().setBackground(mCommunity);
        } else {
            getIcon().setBackground(mBoys);
        }
    }

    public TextView getTitle() {
        return mTitle;
    }
    public TextView getTime() {
        return mTime;
    }
    public ImageView getIcon() {
        return mIcon;
    }
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }
    public boolean hasSubtopics() {
        return mHasSubtopics;
    }
}
