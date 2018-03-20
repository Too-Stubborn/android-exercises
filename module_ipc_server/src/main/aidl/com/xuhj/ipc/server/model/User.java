package com.xuhj.ipc.server.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描述
 *
 * @author xuhj
 */
public class User implements Parcelable {

    private long id;
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.name);
    }

    public void readFromParcel(Parcel dest) {
        // 注意：顺序要和writeToParcel一样！！！
        id = dest.readLong();
        name = dest.readString();
    }

    public User() {
    }

    protected User(Parcel in) {
        this.id = in.readLong();
        this.name = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
