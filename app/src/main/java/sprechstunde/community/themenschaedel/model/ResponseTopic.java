package sprechstunde.community.themenschaedel.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import sprechstunde.community.themenschaedel.model.topic.Topic;

public class ResponseTopic {

    @SerializedName("data")
    private List<Topic> mData;

    @SerializedName("meta")
    private ResponseMeta mResponseMetas;

    public List<Topic> getData() {
        return mData;
    }

    public void setData(List<Topic> data) {
        mData = data;
    }

    public ResponseMeta getMeta() {
        return mResponseMetas;
    }

    public void setMeta(ResponseMeta responseMetas) {
        mResponseMetas = responseMetas;
    }
}