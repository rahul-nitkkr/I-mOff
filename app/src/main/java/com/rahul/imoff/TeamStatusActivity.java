package com.rahul.imoff;

import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.List;


public class TeamStatusActivity extends ListActivity {

    ParseUser mCurrentUser;
    protected ParseRelation<ParseUser> mFriendsRelation;
    protected List<ParseUser> mFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_status);

    }


    @Override
    public void onResume() {
        super.onResume();
        setProgressBarIndeterminateVisibility(true);

        retrieveTeamList();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_team_status, menu);
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

    private void retrieveTeamList() {

        mCurrentUser = ParseUser.getCurrentUser();
        mFriendsRelation = mCurrentUser.getRelation(ParseConstants.KEY_FRIENDS_RELATION);

        ParseQuery<ParseUser> query = mFriendsRelation.getQuery();
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> friends, com.parse.ParseException e) {
                setProgressBarIndeterminateVisibility(false);

                if (e == null) {
                    mFriends = friends;

                    String[] usernames = new String[mFriends.size()];
                    int i = 0;
                    for (ParseUser user : mFriends) {
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    if (getListView().getAdapter() == null) {
                        TeamAdapter adapter = new TeamAdapter(getListView().getContext(), mFriends);
                        setListAdapter(adapter);
                    } else {
                        ((TeamAdapter) getListView().getAdapter()).refill(mFriends);
                    }
                }
            }


        });
    }
}

