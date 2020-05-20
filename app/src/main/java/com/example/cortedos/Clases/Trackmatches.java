package com.example.cortedos.Clases;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Trackmatches {

    @SerializedName("track")
    @Expose
    private List<TrackBuscar> track = null;

    public List<TrackBuscar> getTrack() {
        return track;
    }

    public void setTrack(List<TrackBuscar> track) {
        this.track = track;
    }

}