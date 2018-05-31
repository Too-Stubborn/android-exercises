package com.company.android.base.router.sample.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 描述
 *
 * @author xuhj
 * @version 1.0.0
 * @since 2018/4/3
 */
public class User implements Parcelable {
    public User(String name) {
        this.name = name;
    }

    private String id;
    private String name;
    private String mobile;
    private String age;
    private String address;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeString(this.age);
        dest.writeString(this.address);
    }

    protected User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.mobile = in.readString();
        this.age = in.readString();
        this.address = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
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
