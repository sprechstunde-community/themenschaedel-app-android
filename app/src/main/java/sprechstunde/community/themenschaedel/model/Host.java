package sprechstunde.community.themenschaedel.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "host_table")
public class Host {

    @PrimaryKey
    @SerializedName("name")
    @ColumnInfo(name = "hostName")
    @NonNull
    private String mName;

    @SerializedName("description")
    @ColumnInfo(name = "description")
    private String mDescription;

    @SerializedName("host")
    @ColumnInfo(name = "host")
    private boolean mHost;

    public Host(@NonNull String name, String description, boolean host) {
        mName = name;
        mDescription = description;
        mHost = host;
    }

    @Ignore
    public Host(@NonNull String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public boolean isHost() {
        return mHost;
    }

    public void setHost(boolean isHost) {
        mHost = isHost;
    }
}
