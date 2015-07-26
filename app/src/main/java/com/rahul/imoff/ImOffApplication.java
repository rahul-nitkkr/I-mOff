package com.rahul.imoff;

/**
 * Created by Apple on 06/07/15.
 */


import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseUser;
import com.parse.PushService;
import com.parse.SaveCallback;



public class ImOffApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "AslfYnHI9pbt5hbxqtk6UtKmKbYyGaZdUtMdnPdd", "9EJ6Ik6U4SmUwVeVLf9Mtng6OhbQL7NmdYIG61bh");

        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });

        ParseInstallation.getCurrentInstallation().saveInBackground();
    }

    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USERID, user.getObjectId());
        installation.saveInBackground();
    }
}
