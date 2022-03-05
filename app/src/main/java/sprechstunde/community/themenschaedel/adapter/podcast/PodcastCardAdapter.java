package sprechstunde.community.themenschaedel.adapter.podcast;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.MemoryCategory;
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.episode.Episode;
import sprechstunde.community.themenschaedel.view.podcast.PodcastFragmentDirections;

public class PodcastCardAdapter extends BaseAdapter {

    private final Context mFragment;
    private List<Episode> mEpisodes;
    private LayoutInflater mInflater;
    ImageView mIllustration;
    TextView mText;
    private boolean mShowState;

    public PodcastCardAdapter(Context context, List<Episode> episodes, ImageView illustration, TextView text)
    {
        mFragment = context;
        mEpisodes = episodes;
        mIllustration = illustration;
        mText = text;
        checkIfListIsEmpty();
    }

    public boolean isShowState() {
        return mShowState;
    }

    public void setShowState(boolean showState) {
        mShowState = showState;
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
            mInflater = (LayoutInflater) mFragment.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_podcast_card, null);
        }

        ImageView corner = convertView.findViewById(R.id.card_corner);
        ImageView icon = convertView.findViewById(R.id.card_corner_icon);
        ImageView imageView = convertView.findViewById(R.id.card_image);
        TextView number = convertView.findViewById(R.id.card_number);
        TextView date = convertView.findViewById(R.id.card_date);
        TextView length = convertView.findViewById(R.id.card_length);

        String episode = mFragment.getResources().getString(R.string.podcast_episode) + " " + mEpisodes.get(position).getEpisodeNumber();
        number.setText(episode);
        date.setText(mEpisodes.get(position).getDate());
        length.setText(mEpisodes.get(position).getDuration());

        setUpState( corner, icon, position);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop());

        Glide.get(mFragment).setMemoryCategory(MemoryCategory.LOW);
        Glide.with(mFragment).asBitmap()
                .load(mEpisodes.get(position).getImage())
                .fitCenter()
                .transition(BitmapTransitionOptions.withCrossFade())
                .apply(requestOptions)
                .into(imageView);

        convertView.setOnClickListener(v -> {
            PodcastFragmentDirections.ActionPodcastToEpisode action = PodcastFragmentDirections.actionPodcastToEpisode();
            action.setEpisodeId(mEpisodes.get(position).getEpisodeNumber());
            Navigation.findNavController(v).navigate(action);
        });

        return convertView;
    }

    private void setUpState( ImageView corner, ImageView icon, int position)  {
        Episode episode = mEpisodes.get(position);
        corner.setVisibility(View.GONE);
        icon.setVisibility(View.GONE);
        if(isShowState()) {
            corner.setVisibility(View.VISIBLE);
            icon.setVisibility(View.VISIBLE);
            Drawable stateDrawable = ContextCompat.getDrawable(mInflater.getContext(), R.drawable.ic_open);
            if(episode.getVerified()) {
                corner.setVisibility(View.GONE);
                icon.setVisibility(View.GONE);
            } else if (!episode.getVerified() && episode.isClaimed()) {
                stateDrawable = ContextCompat.getDrawable(mInflater.getContext(), R.drawable.ic_unverified);
                icon.setBackground(stateDrawable);
            } else if (episode.isClaimed()) {
                stateDrawable = ContextCompat.getDrawable(mInflater.getContext(), R.drawable.ic_claimed);
                icon.setBackground(stateDrawable);
            }
            icon.setBackground(stateDrawable);
        }
    }

    public List<Episode> getEpisodes() {
        return mEpisodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        mEpisodes = episodes;
        checkIfListIsEmpty();
    }

    private void checkIfListIsEmpty() {
        if(mEpisodes != null && mEpisodes.size() != 0) {
            mIllustration.setVisibility(View.GONE);
            mText.setVisibility(View.GONE);
        } else {
            mIllustration.setVisibility(View.VISIBLE);
            mText.setVisibility(View.VISIBLE);
        }
    }

}
