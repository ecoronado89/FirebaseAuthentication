package com.example.ecoronado.firebaseauthentication.usuarios;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by crrf on 12/20/2016.
 */

public class InfoChat implements Parcelable {
    String sender, receiver;

    public InfoChat() {}

    public InfoChat(String sender, String receiver) {
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sender);
        dest.writeString(this.receiver);
    }

    protected InfoChat(Parcel in) {
        this.sender = in.readString();
        this.receiver = in.readString();
    }

    public static final Creator<InfoChat> CREATOR = new Creator<InfoChat>() {
        @Override
        public InfoChat createFromParcel(Parcel source) {
            return new InfoChat(source);
        }

        @Override
        public InfoChat[] newArray(int size) {
            return new InfoChat[size];
        }
    };
}
