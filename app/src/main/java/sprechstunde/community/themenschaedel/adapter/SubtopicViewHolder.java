package sprechstunde.community.themenschaedel.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Subtopic;
import sprechstunde.community.themenschaedel.model.Topic;

public class SubtopicViewHolder extends RecyclerView.ViewHolder {

    private final TextView mTitle;
    private final ImageView mLine;

    public SubtopicViewHolder(View view) {
        super(view);
        mTitle = view.findViewById(R.id.list_item_subtopic_title);
        mLine = view.findViewById(R.id.list_item_subtopic_line);
    }

    public TextView getTitle() {
        return mTitle;
    }
    public ImageView getLine() {
        return mLine;
    }


    public void setTopicValues(Subtopic topic) {
        getTitle().setText(topic.getName());
    }

    public void setVerticalLine(int position, int maxSize) {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) getLine().getLayoutParams();

        if (maxSize == 0) {
            getLine().setVisibility(View.INVISIBLE);
        } else if(position == maxSize) {
            params.bottomMargin = 30;
        } else if(position == 0) {
            params.topMargin = 50;
        } else {
            params.topMargin = 0;
            params.bottomMargin = 0;
        }
        getLine().requestLayout();
    }
}
