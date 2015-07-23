package com.ruinkami.overrider.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ruinkami on 2015/7/15.
 */
public class TideObject implements Parcelable {
    private byte[] bytes;
    private int start;
    private int length;

    public TideObject(byte[] bytes, int start, int length) {
        this.bytes = bytes;
        this.start = start;
        this.length = length;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }
}
