package com.benson.rsamechanic.models;

import org.json.JSONObject;

/**
 * Created by benson on 10/30/16.
 */

public class Location {
    private String placename;
    private String longitude;
    private String latitude;
    private String address;
    private User user;
    private JSONObject jsonObject;

//setters and getters
    public String getPlacename() {

        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }


    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
