package com.xuhj.jellybean.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 示例
 */
public class SampleInfo implements Parcelable {

    private String id;
    private String name;

    protected SampleInfo(Parcel in) {
        id = in.readString();
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SampleInfo> CREATOR = new Creator<SampleInfo>() {
        @Override
        public SampleInfo createFromParcel(Parcel in) {
            return new SampleInfo(in);
        }

        @Override
        public SampleInfo[] newArray(int size) {
            return new SampleInfo[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SampleInfo() {
    }

}
