package sprechstunde.community.themenschaedel.adapter.episodeList;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;
import sprechstunde.community.themenschaedel.model.topic.Topic;
import sprechstunde.community.themenschaedel.viewmodel.EpisodeViewModel;

public class EpisodeTopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private final TextView mTime;
    private final ImageView mIcon;
    private final ImageView mSubtopics;
    private final RecyclerView mRecyclerView;
    private final ImageView mPlayButton;

    protected Drawable mAd;
    protected Drawable mCommunity;
    protected Drawable mBoys;

    public EpisodeTopicViewHolder(@NonNull View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.list_item_episode_topic_title);
        mTime = itemView.findViewById(R.id.list_item_episode_topic_time);
        mIcon = itemView.findViewById(R.id.list_item_episode_topic_type);
        mPlayButton = itemView.findViewById(R.id.list_item_episode_topic_play);
        mSubtopics = itemView.findViewById(R.id.list_item_episode_topic_subtopics);

        mRecyclerView = itemView.findViewById(R.id.list_item_episode_topic_recyclerView);
        mRecyclerView.setVisibility(View.GONE);
        mSubtopics.setVisibility(View.GONE);

        mAd = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_ad, null);
        mCommunity = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_community, null);
        mBoys = ResourcesCompat.getDrawable(itemView.getResources(), R.drawable.ic_boys, null);
    }

    public void setTopicValues(List<Subtopic> subtopics, Topic topic, MainActivity activity) {
        getTitle().setText(topic.getName());

        long time = topic.getStart();
        long hours = TimeUnit.SECONDS.toHours(time);
        time -= TimeUnit.HOURS.toSeconds(hours);
        long minutes = TimeUnit.SECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toSeconds(minutes);
        long seconds = TimeUnit.SECONDS.toSeconds(time);

        String startTime = String.format(Locale.getDefault(), "%02d:%02d:%02d",hours, minutes, seconds);
        getTime().setText(startTime);

        if(topic.getAd()) {
            getIcon().setBackground(mAd);
        } else if(topic.getCommunityContribution()) {
            getIcon().setBackground(mCommunity);
        } else {
            getIcon().setBackground(mBoys);
        }

        if (subtopics != null && subtopics.size() > 0) {
            mSubtopics.setVisibility(View.VISIBLE);

            EpisodeSubtopicAdapter adapter = new EpisodeSubtopicAdapter(subtopics);
            mRecyclerView.setAdapter(adapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }

        mPlayButton.setOnClickListener(view -> {
            EpisodeViewModel episodeViewModel = new ViewModelProvider(activity).get(EpisodeViewModel.class);
            episodeViewModel.getEpisode(topic.getEpisode()).observe(activity, episode -> {

                activity.startPlayingEpisodeAt("7uV1nodGQuj4EeVZSCNCpz", topic.getStart() * 1000L);
            });
        });
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
}
