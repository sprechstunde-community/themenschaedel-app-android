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
    private final Context mContext;

    public EpisodeSubtopicAdapter(List<Subtopic> subtopics, Context context) {
        mSubtopics = subtopics;
        mContext = context;
    }

    @NonNull
    @Override
    public SubtopicViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_suggested_topic_details, viewGroup, false);
        return new SubtopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtopicViewHolder holder, int position) {
        holder.setVerticalLine(position, mSubtopics.size() - 1);
    }


    @Override
    public int getItemCount() {
        return mSubtopics.size();
    }

}
