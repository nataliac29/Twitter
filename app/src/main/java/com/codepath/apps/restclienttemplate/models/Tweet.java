package com.codepath.apps.restclienttemplate.models;

        import android.util.Log;

        import androidx.room.ColumnInfo;
        import androidx.room.Embedded;

        import androidx.room.Entity;
        import androidx.room.PrimaryKey;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.parceler.Parcel;

        import java.util.ArrayList;

@Parcel
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
    @ColumnInfo
    public String imageURL;

    // Use @Embedded to keep the column entries as part of the same table while still
    // keeping the logical separation between the two objects.
    @Embedded
    public User user;

    // empty constructor needed by the Parceler library
    public Tweet() {}

    public static Tweet fromJson(JSONObject jsonObject) throws JSONException {
        Tweet tweet = new Tweet();
        tweet.post_id = jsonObject.getString("id_str");
        tweet.body = jsonObject.getString("text");
        tweet.created_at = jsonObject.getString("created_at");
        tweet.user = User.parseJSON(jsonObject.getJSONObject("user"));
        if (jsonObject.has("extended_entities")) {
            JSONObject entities = jsonObject.getJSONObject("extended_entities");
            JSONArray media = entities.getJSONArray("media");
            if (media.length() >= 1 && media.getJSONObject(0).getString("type").equals("photo")) {
                String url = media.getJSONObject(0).getString("media_url_https");
                String type = url.substring(url.lastIndexOf('.') + 1);
                String baseUrl = url.substring(0, url.length() - (type.length() + 1));
                Log.e("type_url", baseUrl + "?format=" + type + "&name=thumb");
                tweet.imageURL = baseUrl + "?format=" + type + "&name=small";
            }
        }
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