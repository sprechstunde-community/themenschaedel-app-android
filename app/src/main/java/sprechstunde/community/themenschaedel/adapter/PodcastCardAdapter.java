package sprechstunde.community.themenschaedel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Episode;

public class PodcastCardAdapter extends BaseAdapter {

    private final Context context;
    Episode[] mEpisodes;
    LayoutInflater mInflater;

    public PodcastCardAdapter(Context context, Episode[] episodes)
    {
        this.context = context;
        mEpisodes = episodes;
    }

    @Override
    public int getCount() {
        return mEpisodes.length;
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

        String episode = context.getResources().getString(R.string.podcast_episode) + " " + mEpisodes[position].getNumber();
        number.setText(episode);
        date.setText(mEpisodes[position].getDate());
        length.setText(mEpisodes[position].getDuration());
        imageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_view_cards));
        return convertView;
    }
}
