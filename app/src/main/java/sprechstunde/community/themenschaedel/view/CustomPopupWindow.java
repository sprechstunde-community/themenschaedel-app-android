package sprechstunde.community.themenschaedel.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import sprechstunde.community.themenschaedel.R;

public class CustomPopupWindow extends PopupWindow {

    public void showSortPopup(int viewId, int layoutId, final Activity context)
    {
        ConstraintLayout viewGroup = context.findViewById(viewId);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(layoutId, viewGroup);

        setContentView(layout);

        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

        if ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600, displayMetrics));
        } else {
            setWidth((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 300, displayMetrics));
        }

        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        setFocusable(true);

        setBackgroundDrawable(ResourcesCompat.getDrawable(context.getResources(), R.drawable.ic_card, context.getTheme()));
        showAtLocation(layout, Gravity.CENTER, 0, 0);

        dimBehind();
    }

    private void dimBehind() {
        View container = getContentView().getRootView();
        Context context = getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.3f;
        wm.updateViewLayout(container, p);
    }
}
