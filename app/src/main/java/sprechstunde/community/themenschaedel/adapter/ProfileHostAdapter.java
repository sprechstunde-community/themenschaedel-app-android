package sprechstunde.community.themenschaedel.adapter;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import sprechstunde.community.themenschaedel.Enums;
import sprechstunde.community.themenschaedel.MainActivity;
import sprechstunde.community.themenschaedel.R;
import sprechstunde.community.themenschaedel.model.Host;

public class ProfileHostAdapter extends RecyclerView.Adapter<ProfileHostAdapter.ViewHolder> {

    private final MainActivity mActivity;
    private final List<Host> mHosts;
    private Enums.HostState hostState = Enums.HostState.NONE;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image;
        private final ImageView icon;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.list_item_profile_host_image);
            icon = view.findViewById(R.id.list_item_profile_host_state);
        }

        public ImageView getImage() {
            return image;
        }

        public ImageView getIcon() { return icon; }
    }

    public ProfileHostAdapter(List<Host> hosts, MainActivity context) {
        mHosts = hosts;
        mActivity = context;
    }

    @NonNull
    @Override
    public ProfileHostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_profile_host, viewGroup, false);
        return new ProfileHostAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ProfileHostAdapter.ViewHolder holder, int position) {
        holder.getImage().setOnClickListener(view -> {
            Drawable image = AppCompatResources.getDrawable(mActivity, R.drawable.img_default_profile);
            switch (hostState) {
                case IN:
                    image = AppCompatResources.getDrawable(mActivity, R.drawable.ic_close_circle);
                    holder.getIcon().setVisibility(View.VISIBLE);
                    hostState = Enums.HostState.OUT;
                    break;
                case NONE:
                    image = AppCompatResources.getDrawable(mActivity, R.drawable.ic_check_circle);
                    holder.getIcon().setVisibility(View.VISIBLE);
                    hostState = Enums.HostState.IN;
                    break;
                default:
                    holder.getIcon().setVisibility(View.GONE);
                    hostState = Enums.HostState.NONE;
            }
            holder.getIcon().setImageDrawable(image);

        });

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
        holder.getImage().setImageDrawable(image);
    }

    @Override
    public int getItemCount() {
        return mHosts.size();
    }

    public List<Host> getHosts() {
        return mHosts;
    }
}