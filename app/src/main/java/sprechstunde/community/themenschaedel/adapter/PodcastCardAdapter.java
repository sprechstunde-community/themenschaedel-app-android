package sprechstunde.community.themenschaedel.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.data.Podcast;

public class PodcastCardAdapter extends BaseAdapter {

    private final Context context;
    Podcast[] mPodcasts;
    LayoutInflater mInflater;

    public PodcastCardAdapter(Context context, Podcast[] podcasts)
    {
        this.context = context;
        mPodcasts = podcasts;
    }

    @Override
    public int getCount() {
        return mPodcasts.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(mInflater == null) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_podcast_card, null);
        }

        ImageView imageView = convertView.findViewById(R.id.card_image);
        TextView number = convertView.findViewById(R.id.card_number);
        TextView date = convertView.findViewById(R.id.card_date);
        TextView length = convertView.findViewById(R.id.card_length);

        String episode = context.getResources().getString(R.string.podcast_episode) + " " + mPodcasts[position].getNumber();
        number.setText(episode);
        date.setText(mPodcasts[position].getDate());
        length.setText(mPodcasts[position].getLength());
        imageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_view_cards));
        return convertView;
    }
}
