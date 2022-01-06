package sprechstunde.community.themenschaedel.adapter.episodeList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.SubtopicViewHolder;
import sprechstunde.community.themenschaedel.model.Subtopic;

public class EpisodeSubtopicAdapter extends RecyclerView.Adapter<SubtopicViewHolder> {

    private final List<Subtopic> mSubtopics;

    public EpisodeSubtopicAdapter(List<Subtopic> subtopics) {
        mSubtopics = subtopics;
    }

    @NonNull
    @Override
    public SubtopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_topic_subtopic, viewGroup, false);
        return new SubtopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtopicViewHolder holder, int position) {
        holder.setVerticalLine(position, mSubtopics.size() - 1);
        holder.setTopicValues(mSubtopics.get(position));
    }


    @Override
    public int getItemCount() {
        return mSubtopics.size();
    }

}
