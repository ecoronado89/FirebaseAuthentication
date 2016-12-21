package com.example.ecoronado.firebaseauthentication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by llbean on 12/20/16.
 */

public class Message implements Parcelable {
    private String mText;
    private String mSender;
    private Date mDate;

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmSender() {
        return mSender;
    }

    public void setmSender(String mSender) {
        this.mSender = mSender;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mText);
        dest.writeString(this.mSender);
        dest.writeLong(this.mDate != null ? this.mDate.getTime() : -1);
    }

    public Message() {
    }

    protected Message(Parcel in) {
        this.mText = in.readString();
        this.mSender = in.readString();
        long tmpMDate = in.readLong();
        this.mDate = tmpMDate == -1 ? null : new Date(tmpMDate);
    }

    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel source) {
            return new Message(source);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };
}
