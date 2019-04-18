package com.example.preferences.shared.khang.practice;

import io.realm.RealmObject;

public class Manager extends RealmObject {
    private String name;
    private String maso;
    private String soluong;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaso() {
        return maso;
    }

    public void setMaso(String maso) {
        this.maso = maso;
    }
    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }


}