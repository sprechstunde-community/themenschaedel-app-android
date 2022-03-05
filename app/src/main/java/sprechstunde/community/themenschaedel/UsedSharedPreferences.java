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

    public void saveTopicCountToSharedPreferences(int episodeCount) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(mMainActivity.getString(R.string.saved_topicCount), episodeCount);
        editor.apply();
    }

    public int getEpisodeCountFromSharedPreferences() {
        int defaultValue = 0;
        return mSharedPref.getInt(mMainActivity.getString(R.string.saved_episodeCount), defaultValue);
    }

    public int getTopicCountFromSharedPreferences() {
        int defaultValue = 0;
        return mSharedPref.getInt(mMainActivity.getString(R.string.saved_topicCount), defaultValue);
    }

    public void getUserRoleToSharedPreferences() {
        int defaultValue = 1;
        int value = mSharedPref.getInt(mMainActivity.getString(R.string.saved_user_role), defaultValue);
        SessionManagement.getInstance().setCurrentRole(value);
    }

    public void saveUserRoleToSharedPreferences(SessionManagement.ROLE role) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(mMainActivity.getString(R.string.saved_user_role), SessionManagement.getInstance().getIntFromEnum(role));
        editor.apply();
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

    public void saveFilterTypeToSharedPreferences(Enums.SORTED_BY sortedBy) {
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.putInt(mMainActivity.getString(R.string.saved_filter_type_list), sortedBy.ordinal());
        editor.apply();
    }

    public Enums.SORTED_BY getFilterTypeFromSharedPreferences() {
        int defaultValue = Enums.SORTED_BY.NUMBER_DOWN.ordinal();
        int displayType = mSharedPref.getInt(mMainActivity.getString(R.string.saved_filter_type_list), defaultValue);
        return Enums.SORTED_BY.values()[displayType];
    }

}