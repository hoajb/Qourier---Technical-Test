package com.qourier.technicaltest.question1;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Hoa Nguyen on Jul 19 2019.
 */
public class TimeData {
    @SerializedName("currentTime")
    private String currentTime;

    @SerializedName("arrivals")
    private ArrayList<String> arrivalsTime;

    @SerializedName("departures")
    private Map<String, ArrayList<String>> departuresTime;

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public ArrayList<String> getArrivalsTime() {
        return arrivalsTime;
    }

    public void setArrivalsTime(ArrayList<String> arrivalsTime) {
        this.arrivalsTime = arrivalsTime;
    }

    public Map<String, ArrayList<String>> getDeparturesTime() {
        return departuresTime;
    }

    public void setDeparturesTime(Map<String, ArrayList<String>> departuresTime) {
        this.departuresTime = departuresTime;
    }

    //--

    public ArrayList<String> getDepartureListByArrival(String arrival) {
        if (departuresTime == null || departuresTime.isEmpty())
            return new ArrayList<>();

        ArrayList<String> list = departuresTime.get(arrival);

        return list != null ? list : new ArrayList<String>();
    }

}
