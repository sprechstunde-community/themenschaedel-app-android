package sprechstunde.community.themenschaedel;

import android.content.Context;
import android.content.SharedPreferences;

public class UsedSharedPreferences {

    private static UsedSharedPreferences mInstance = null;
    private final SharedPreferences mSharedPref;
    private final MainActivity mMainActivity;

    UsedSharedPreferences(MainActivity a) {
        mSharedPref = a.getPreferences(Context.MODE_PRIVATE);
        mMainActivity = a;
    }

    public static UsedSharedPreferences getInstance(MainActivity a) {
        if (mInstance == null) {
            mInstance = new UsedSharedPreferences(a);
        }
        return mInstance;
    }

    public void saveEpisodeCountToSharedPreferences(int episodeCount) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(mMainActivity.getString(R.string.saved_episodeCount), episodeCount);
        editor.apply();
    }

    public int getEpisodeCountFromSharedPreferences() {
        int defaultValue = 0;
        return mSharedPref.getInt(mMainActivity.getString(R.string.saved_episodeCount), defaultValue);
    }

    public void saveFirstStartToSharedPreferences(int value) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(mMainActivity.getString(R.string.saved_first_start), value);
        editor.apply();
    }

    public int getFirstStartFromSharedPreferences() {
        int defaultValue = 0;
        return mSharedPref.getInt(mMainActivity.getString(R.string.saved_first_start), defaultValue);
    }
}