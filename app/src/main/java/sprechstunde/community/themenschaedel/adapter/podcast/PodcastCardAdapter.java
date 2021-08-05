package sprechstunde.community.themenschaedel.adapter.podcast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.adapter.episodeList.EpisodeTopicViewHolder;
import sprechstunde.community.themenschaedel.model.Episode;
import sprechstunde.community.themenschaedel.model.ViewModel;
import sprechstunde.community.themenschaedel.view.podcast.PodcastFragmentDirections;

public class PodcastCardAdapter extends BaseAdapter {

    private final Context context;
    List<Episode> mEpisodes;
    LayoutInflater mInflater;

    public PodcastCardAdapter(Context context, List<Episode> episodes)
    {
        this.context = context;
        mEpisodes = episodes;
    }

    @Override
    public int getCount() {
        return mEpisodes.size();
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
            convertView = mInflater.inflate(R.layout.list_item_podcast_card, null);
        }

        ImageView imageView = convertView.findViewById(R.id.card_image);
        TextView number = convertView.findViewById(R.id.card_number);
        TextView date = convertView.findViewById(R.id.card_date);
        TextView length = convertView.findViewById(R.id.card_length);

        String episode = context.getResources().getString(R.string.podcast_episode) + " " + mEpisodes.get(position).getNumber();
        number.setText(episode);
        date.setText(mEpisodes.get(position).getDate());
        length.setText(mEpisodes.get(position).getDuration());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(40));

        Glide.with(context)
                .load(mEpisodes.get(position).getImage())
                .apply(requestOptions)
                .into(imageView);

        convertView.setOnClickListener(v -> {
            PodcastFragmentDirections.ActionPodcastToEpisode action = PodcastFragmentDirections.actionPodcastToEpisode();
            action.setEpisodeId(mEpisodes.get(position).getId());
            Navigation.findNavController(v).navigate(action);
        });

        return convertView;
    }

    public List<Episode> getEpisodes() {
        return mEpisodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        mEpisodes = episodes;
    }

}
