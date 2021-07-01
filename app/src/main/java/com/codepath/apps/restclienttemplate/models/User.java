package com.codepath.apps.restclienttemplate.models;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    @ColumnInfo
    public String name;
    public String screenName;
    public String publicImageUrl;

    // Parse model from JSON
    public static User parseJSON(JSONObject tweetJson) throws JSONException {
        User user = new User();
        user.name = tweetJson.getString("id");
        user.screenName = tweetJson.getString("name");
        user.publicImageUrl = tweetJson.getString("profile_image_url_https");
        return user;
    }
}