package sprechstunde.community.themenschaedel.adapter.episodeList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import androidx.appcompat.content.res.AppCompatResources;

import java.util.List;

import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Host;

public class EpisodeHostAdapter extends BaseAdapter {

  private final MainActivity mActivity;
  List<Host> mHosts;
  LayoutInflater mInflater;

  public EpisodeHostAdapter(MainActivity activity, List<Host> hosts)
  {
    mActivity = activity;
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
      mInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    if(convertView == null) {
      convertView = mInflater.inflate(R.layout.list_item_episode_host, null);
    }

    ImageView imageView = convertView.findViewById(R.id.list_item_profile_host_image);
    Drawable image = AppCompatResources.getDrawable(mActivity, R.drawable.img_default_profile);

    switch (mHosts.get(position).getName()) {
      case "Flo":
        image = AppCompatResources.getDrawable(mActivity, R.drawable.img_host_flo);
        break;
      case "Oli":
        image = AppCompatResources.getDrawable(mActivity, R.drawable.img_host_olli);
        break;
      case "Paul":
        image = AppCompatResources.getDrawable(mActivity, R.drawable.img_host_paul);
        break;
    }
    imageView.setImageDrawable(image);

    return convertView;
  }
}