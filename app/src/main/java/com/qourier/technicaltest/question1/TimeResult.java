package com.qourier.technicaltest.question1;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Hoa Nguyen on Jul 19 2019.
 */
public class TimeResult {
    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private ArrayList<TimeData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<TimeData> getData() {
        return data;
    }

    public void setData(ArrayList<TimeData> data) {
        this.data = data;
    }

    public TimeData firstData() {
        if (data == null || data.size() == 0) return null;

        return data.get(0);
    }
}
