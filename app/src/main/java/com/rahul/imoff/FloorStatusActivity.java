package com.rahul.imoff;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class FloorStatusActivity extends ListActivity {
    protected Boolean intDisplay = true;
    protected Spinner mSpinner;
    //    protected ListView mListView;
    protected List<ParseUser> mAvailableUsers;
    protected List<Floor> mSeats;
    private String mFloor;
    private static final String defaultFloor = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floor_status);


        mSpinner = (Spinner)findViewById(R.id.spinner);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                if(intDisplay){
                    intDisplay = false;
                }else{
                mFloor = parent.getItemAtPosition(pos).toString();
                getSeatsAvailable(mFloor);}
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
//        mListView = (ListView)findViewById(R.id.seatlist);
//        FloorViewFragment floorViewFragment = new FloorViewFragment();
//        getSupportFragmentManager().beginTransaction().add(android.R.id.content,floorViewFragment).commit();


    }

    @Override
    public void onResume() {
        super.onResume();
        setProgressBarIndeterminateVisibility(true);
        //getSeatsAvailable(defaultFloor);
    }

    private void getSeatsAvailable(final String position) {
        ParseQuery<ParseUser> query = ParseUser.getQuery();

        query.whereEqualTo(ParseConstants.KEY_FLOOR,String.valueOf(position));
        query.whereEqualTo(ParseConstants.KEY_OFFSTATUS,Boolean.TRUE);
        query.addAscendingOrder(ParseConstants.KEY_SEATNUMBER);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> team_members, ParseException e) {
                setProgressBarIndeterminateVisibility(false);

                if (e == null) {
                    mAvailableUsers = team_members;

                    List<Floor> seats = new ArrayList<Floor>();
                    int i =0;
                    for(ParseUser user : mAvailableUsers) {
                        Floor seat = new Floor();
                        seat.setFloor(position);
                        seat.setUsername(user.getUsername());
                        seat.setSeatno(user.get(ParseConstants.KEY_SEATNUMBER).toString());

                        seats.add(seat);
                        i++;
                    }


                    if (getListView().getAdapter() == null) {
                        FloorAdapter adapter = new FloorAdapter(getListView().getContext(), seats);
                        getListView().setAdapter(adapter);
                    }
                    else {
                        ((FloorAdapter)getListView().getAdapter()).refill(seats);
                    }
                }
                else {
                    // Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(FloorStatusActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_floor_status, menu);
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
}
