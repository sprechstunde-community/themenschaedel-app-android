package sprechstunde.community.themenschaedel.adapter.episodeList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.List;

import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Host;

public class EpisodeHostAdapter extends BaseAdapter {

  private final Context context;
  List<Host> mHosts;
  LayoutInflater mInflater;

  public EpisodeHostAdapter(Context context, List<Host> hosts)
  {
    this.context = context;
    mHosts = hosts;
  }

  @Override
  public int getCount() {
    return mHosts.size();
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
      convertView = mInflater.inflate(R.layout.list_item_episode_host, null);
    }

    ImageView imageView = convertView.findViewById(R.id.list_item_host_image);
    imageView.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.img_default_profile));

    return convertView;
  }
}