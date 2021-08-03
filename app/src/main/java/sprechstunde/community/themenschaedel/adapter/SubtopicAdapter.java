package sprechstunde.community.themenschaedel.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class SubtopicAdapter extends RecyclerView.Adapter<SubtopicAdapter.ViewHolder> {

    private final List<Subtopic> mSubtopics;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final ImageView mLine;

        public ViewHolder(View view) {
            super(view);
            mTitle = view.findViewById(R.id.list_item_sugg_subtopic_title);
            mLine = view.findViewById(R.id.list_item_sugg_subtopic_line);
        }

        public TextView getTitle() {
            return mTitle;
        }
        public ImageView getLine() {
            return mLine;
        }
    }

    public SubtopicAdapter(List<Subtopic> subtopics) {
        mSubtopics = subtopics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item_suggested_topic_subtopic, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        setVerticalLine(viewHolder, position);
        viewHolder.getTitle().setText(mSubtopics.get(position).getName());
    }

    private void setVerticalLine(ViewHolder viewHolder, int position) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) viewHolder.getLine().getLayoutParams();

        if(position == mSubtopics.size() - 1) {
            params.bottomMargin = 30;
        } else if(position == 0) {
            params.topMargin = 50;
        } else {
            params.topMargin = 0;
            params.bottomMargin = 0;
        }
        viewHolder.getLine().requestLayout();
    }

    @Override
    public int getItemCount() {
        return mSubtopics.size();
    }

}
