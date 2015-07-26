package com.rahul.imoff;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class MainActivity extends ActionBarActivity {

    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView)findViewById(R.id.imgPhoto);
        mImageView.setOnClickListener(sendNotificationListener);
    }

    View.OnClickListener sendNotificationListener =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            sendPushNotification();
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    protected void sendPushNotification(){
        ParseQuery<ParseInstallation> query = ParseInstallation.getQuery();
        query.whereExists(ParseConstants.KEY_MANAGER);
        //query.whereContainedIn(ParseConstants.KEY_USERID,getRecipientsId());

        ParsePush push = new ParsePush();
        push.setQuery(query);
        push.setMessage(ParseUser.getCurrentUser().get(ParseConstants.KEY_USERNAME) + "is off today!");
        push.sendInBackground();
    }
}

