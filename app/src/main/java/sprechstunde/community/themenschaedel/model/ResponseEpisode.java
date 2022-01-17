package sprechstunde.community.themenschaedel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseEpisode {

    @SerializedName("data")
    private List<Episode> mData;

    @SerializedName("meta")
    private ResponseMeta mResponseMetas;

    public List<Episode> getData() {
        return mData;
    }

    public void setData(List<Episode> data) {
        mData = data;
    }

    public ResponseMeta getMeta() {
        return mResponseMetas;
    }

    public void setMeta(ResponseMeta responseMetas) {
        mResponseMetas = responseMetas;
    }
}