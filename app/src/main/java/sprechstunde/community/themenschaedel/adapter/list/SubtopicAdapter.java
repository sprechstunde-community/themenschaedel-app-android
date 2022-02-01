package sprechstunde.community.themenschaedel.adapter.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.SubtopicViewHolder;
import sprechstunde.community.themenschaedel.model.topic.Subtopic;

public class SubtopicAdapter extends RecyclerView.Adapter<SubtopicViewHolder> {

    private final List<Subtopic> mSubtopics;

    public SubtopicAdapter(List<Subtopic> subtopics) {
        mSubtopics = subtopics;
    }

    @NonNull
    @Override
    public SubtopicViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_subtopic, viewGroup, false);
        return new SubtopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubtopicViewHolder viewHolder, final int position) {
        viewHolder.setVerticalLine(position, mSubtopics.size() - 1);
        viewHolder.setTopicValues(mSubtopics.get(position));
    }

    @Override
    public int getItemCount() {
        return mSubtopics.size();
    }

}
