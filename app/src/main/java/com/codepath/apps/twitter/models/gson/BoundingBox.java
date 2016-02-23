
package com.codepath.apps.twitter.models.gson;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Generated;

import java.util.ArrayList;
import java.util.List;

@Generated("org.jsonschema2pojo")
public class BoundingBox {

    @SerializedName("coordinates")
    @Expose
    private List<List<List<Double>>> coordinates = new ArrayList<List<List<Double>>>();
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The coordinates
     */
    public List<List<List<Double>>> getCoordinates() {
        return coordinates;
    }

    /**
     * 
     * @param coordinates
     *     The coordinates
     */
    public void setCoordinates(List<List<List<Double>>> coordinates) {
        this.coordinates = coordinates;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
