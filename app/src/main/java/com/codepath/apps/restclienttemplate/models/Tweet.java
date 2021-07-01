package com.codepath.apps.restclienttemplate.models;

        import androidx.room.ColumnInfo;
        import androidx.room.Embedded;

        import androidx.room.Entity;
        import androidx.room.PrimaryKey;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;

@Entity
public class Tweet {
    // Define database columns and associated fields
    @PrimaryKey
    @ColumnInfo
    public String post_id;
    @ColumnInfo
    public String created_at;
    @ColumnInfo
    public String body;

    // Use @Embedded to keep the column entries as part of the same table while still
    // keeping the logical separation between the two objects.
    @Embedded
    public User user;

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.post_id = jsonObject.getString("id_str");
        tweet.body = jsonObject.getString("text");
        tweet.created_at = jsonObject.getString("created_at");
        tweet.user = User.parseJSON(jsonObject.getJSONObject("user"));
        return tweet;
    }

    public static ArrayList<Tweet> fromJson(JSONArray jsonArray) throws JSONException {
        ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());
        for (int i=0; i < jsonArray.length(); i++) {
                tweets.add(fromJson(jsonArray.getJSONObject(i)));
        }
        return tweets;
    }
}