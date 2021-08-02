package sprechstunde.community.themenschaedel.data;

public class Topic {

    private String mTitle;
    private int mStart;
    private int mEpisode;
    private boolean mHasSubtopics;

    public Topic(String title, int start, int episode, boolean hasSubtopics) {
        mTitle = title;
        mStart = start;
        mEpisode = episode;
        mHasSubtopics = hasSubtopics;
    }

    public Topic(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getStart() {
        return mStart;
    }

    public void setStart(int start) {
        mStart = start;
    }

    public int getEpisode() {
        return mEpisode;
    }

    public void setEpisode(int episode) {
        mEpisode = episode;
    }

    public boolean isHasSubtopics() {
        return mHasSubtopics;
    }

    public void setHasSubtopics(boolean hasSubtopics) {
        mHasSubtopics = hasSubtopics;
    }
}
