package com.example.user.igem_ncku_tainan_2017;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class locationResponses {
    @SerializedName("locations")
    @Expose
    private List<Locations> locations;

    public List<Locations> getLocations() {
        return locations;
    }
}
