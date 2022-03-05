package sprechstunde.community.themenschaedel;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.google.android.material.navigation.NavigationView;
import sprechstunde.community.themenschaedel.api.ApiClient;
import sprechstunde.community.themenschaedel.model.User;

public class SessionManagement {

    public static SessionManagement mInstance;
    private static ROLE mCurrentRole;

    public enum ROLE {
        USER,
        FROID,
        MOD,
        ADMIN,
        ANONYM,
    }

    private SessionManagement() {

    }

    public static SessionManagement getInstance() {
        if(mInstance == null) {
            mInstance = new SessionManagement();
            mCurrentRole = ROLE.ANONYM;
        }
        return mInstance;
    }
    public ROLE getCurrentRole() {
        return mCurrentRole;
    }

    public void setCurrentRole(ROLE currentRole) {
        mCurrentRole = currentRole;
    }
    public void setCurrentRole(int currentRole) {
        mCurrentRole = getEnumFromInt(currentRole);
    }

    public void setUIWhenLoggedIn(NavigationView navigationView, MainActivity activity, User myself) {
        SpannableString spanString = new SpannableString(activity.getString(R.string.dialog_info_user_state));
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.onSurfaceSecondary)), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);

        navigationView.getMenu().getItem(2).setTitle(R.string.logout);
        navigationView.getMenu().getItem(2).setIcon(R.drawable.ic_logout);
        navigationView.getMenu().getItem(3).setTitle(spanString);
        navigationView.getHeaderView(0).setOnClickListener(activity);
        navigationView.getMenu().getItem(2).setOnMenuItemClickListener(menuItem -> {
            ApiClient.getInstance(activity).logout();
            return false;
        });

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.drawer_name);
        username.setText(myself.getUsername());
        username.setVisibility(View.VISIBLE);

        TextView email = headerView.findViewById(R.id.drawer_email);
        email.setText(myself.getEmail());
        email.setVisibility(View.VISIBLE);
        headerView.findViewById(R.id.drawer_image_mask).setVisibility(View.VISIBLE);
    }

    public void setUIWhenAnonym(NavigationView navigationView, MainActivity activity) {
        SpannableString spanString = new SpannableString(activity.getString(R.string.dialog_info_anonym_state));
        spanString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(activity, R.color.onSurfaceSecondary)), 0, spanString.length(), 0);
        spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);

        navigationView.getMenu().getItem(2).setTitle(R.string.login_register);
        navigationView.getMenu().getItem(2).setIcon(R.drawable.ic_login);
        navigationView.getMenu().getItem(3).setTitle(spanString);

        View headerView = navigationView.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.drawer_name);
        username.setVisibility(View.GONE);

        TextView email = headerView.findViewById(R.id.drawer_email);
        email.setVisibility(View.GONE);
        headerView.findViewById(R.id.drawer_image_mask).setVisibility(View.INVISIBLE);
    }

    public ROLE getEnumFromInt(int roleId) {
        switch(roleId) {
            case 2: {
                return ROLE.USER;
            }
            case 3: {
                return ROLE.FROID;
            }
            case 4: {
                return ROLE.MOD;
            }
            case 5: {
                return ROLE.ADMIN;
            }
            default:
                return ROLE.ANONYM;
        }
    }

    public int getIntFromEnum(ROLE role) {
        switch(role) {
            case USER: {
                return 2;
            }
            case FROID: {
                return 3;
            }
            case MOD: {
                return 4;
            }
            case ADMIN: {
                return 5;
            }
            default:
                return 0;
        }
    }
}
