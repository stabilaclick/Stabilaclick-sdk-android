package com.tronlink.sdk.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Walllet {
    String name;
    String address;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Walllet() {
    }

    protected Walllet(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
    }


}
