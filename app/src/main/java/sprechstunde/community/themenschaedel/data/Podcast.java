package sprechstunde.community.themenschaedel.data;

public class Podcast {

    private String mTitle;
    private String mDate;
    private int mNumber;
    private String mImage;
    private String mLength;

    public Podcast (String title, String date, int number, String length) {
        mTitle = title;
        mDate = date;
        mNumber = number;
        mLength = length;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDate() { return mDate; }

    public void setDate(String date) { mDate = date; }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public String getImage() {
        return mImage;
    }

    public void setImg(String img) {
        mImage = img;
    }

    public String getLength() { return mLength; }

    public void setLength(String length) { mLength = length; }
}
