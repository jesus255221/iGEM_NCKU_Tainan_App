package com.example.user.igem_ncku_tainan_2017;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by david on 9/26/2017.
 */

public class Nitrate {
    @SerializedName("_id")
    @Expose
    private String _id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("ph")
    @Expose
    private Double ph;
    @SerializedName("temp")
    @Expose
    private Double temp;
    @SerializedName("concentration")
    @Expose
    private Double concentration;

    public String get_id() {
        return _id;
    }

    public String getDate() {
        return date;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getPh() {
        return ph;
    }

    public Double getTemp() {
        return temp;
    }

    public Double getConcentration() {
        return concentration;
    }
}
