package com.example.user.igem_ncku_tainan_2017;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by david on 9/26/2017.
 */

public class NitrateResponses {
    @SerializedName("nitrates")
    @Expose
    private List<Nitrate> nitrates;

    public List<Nitrate> getNitrates() {
        return nitrates;
    }
}
