package com.example.cortedos.Clases;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrackBuscar {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("artist")
    @Expose
    private String artist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

}