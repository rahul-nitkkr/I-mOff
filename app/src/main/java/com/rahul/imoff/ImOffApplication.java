package com.rahul.imoff;

/**
 * Created by Apple on 06/07/15.
 */


import android.app.Application;

import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseUser;

public class ImOffApplication extends Application{
    @Override
    public void onCreate(){
        super.onCreate();

        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "AslfYnHI9pbt5hbxqtk6UtKmKbYyGaZdUtMdnPdd", "9EJ6Ik6U4SmUwVeVLf9Mtng6OhbQL7NmdYIG61bh");
    }

    public static void updateParseInstallation(ParseUser user) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();
        installation.put(ParseConstants.KEY_USERID, user.getObjectId());
        installation.saveInBackground();
    }
}
