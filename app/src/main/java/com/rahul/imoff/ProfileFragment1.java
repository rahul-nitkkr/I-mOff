package com.rahul.imoff;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.ParseUser;


public class ProfileFragment1 extends Fragment {

    private static final String TAG = "FirstFragment";
    Boolean isMgr  = Boolean.FALSE;
   // TextView teamName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile1, container, false);

        final ParseUser parseuser = ParseUser.getCurrentUser();

        TextView tv = (TextView) view.findViewById(R.id.tv);
        Button btn = (Button) view.findViewById(R.id.btn);
        Switch mySwitch  = (Switch) view.findViewById(R.id.switch1);
    //    teamName = (TextView)view.findViewById(R.id.teamName);

        tv.setText("Hello " + parseuser.getUsername());


        mySwitch.setChecked(false);
        //attach a listener to check for changes in state
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                   isMgr = Boolean.TRUE;
                   Log.i(TAG,isMgr.toString());
                    //parseuser.put(ParseConstants.KEY_ISMANAGER, isMgr);
                    //parseuser.saveInBackground();
                }else{
                    isMgr = Boolean.FALSE;
                    Log.i(TAG,isMgr.toString());
                   // parseuser.put(ParseConstants.KEY_ISMANAGER, isMgr);
                   // parseuser.saveInBackground();
                }

            }
        });



        //get the team name here or in teh next fragment


        //check the current state before we display the screen
//        if(mySwitch.isChecked()){
//            parseuser.put(ParseConstants.KEY_ISMANAGER, isMgr);
//        }
//        else {
//            parseuser.put(ParseConstants.KEY_ISMANAGER, isMgr);
//        }





        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                FragmentTransaction trans = getFragmentManager()
                        .beginTransaction();
                parseuser.put(ParseConstants.KEY_TEAM,"VENTURE");
                parseuser.put(ParseConstants.KEY_ISMANAGER, isMgr);
                Log.i(TAG,"ParseUser is MANAGER" + parseuser.get(ParseConstants.KEY_ISMANAGER));
                parseuser.saveInBackground();
				/*
				 * IMPORTANT: We use the "root frame" defined in
				 * "root_fragment.xml" as the reference to replace fragment
				 */
              if(isMgr){
                trans.replace(R.id.root_frame, new ProfileFragment2());}
                else{
                    trans.replace(R.id.root_frame, new ProfileAddManagerFragment());
                }

				/*
				 * IMPORTANT: The following lines allow us to add the fragment
				 * to the stack and return to it later, by pressing back
				 */
                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                trans.addToBackStack(null);

                trans.commit();
            }
        });

        return view;
    }

}