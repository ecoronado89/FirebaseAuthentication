package com.example.ecoronado.firebaseauthentication.usuarios;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by crrf on 12/20/2016.
 */

public class Info implements Parcelable {
    String image;
    String username;

    public Info(){}

    public Info(String image, String username) {
        this.image = image;
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public String getUsername() {
        return username;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.image);
        dest.writeString(this.username);
    }

    protected Info(Parcel in) {
        this.image = in.readString();
        this.username = in.readString();
    }

    public static final Creator<Info> CREATOR = new Creator<Info>() {
        @Override
        public Info createFromParcel(Parcel source) {
            return new Info(source);
        }

        @Override
        public Info[] newArray(int size) {
            return new Info[size];
        }
    };
}
