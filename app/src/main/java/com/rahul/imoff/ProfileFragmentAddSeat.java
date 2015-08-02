package com.rahul.imoff;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.parse.Parse;
import com.parse.ParseUser;


public class ProfileFragmentAddSeat extends Fragment {


    protected Spinner mSpinner;
    protected EditText mEditText;
    protected Button btn;
    private static final String TAG = "AddSeat";
    protected Boolean intDisplay = true;
    private String mFloor;
    // TextView teamName;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_floor_seat, container, false);


        mSpinner = (Spinner)view.findViewById(R.id.spinner2);
        mEditText = (EditText) view.findViewById(R.id.editText4);

        btn = (Button) view.findViewById(R.id.buttonFloor);

        mSpinner = (Spinner)view.findViewById(R.id.spinner2);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                if(intDisplay){
                    intDisplay = false;
                }else{
                    mFloor = parent.getItemAtPosition(pos).toString();
                    }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

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

                ParseUser mCurrentUser = ParseUser.getCurrentUser();
                String text = mEditText.getText().toString();
                if(text.equals(null)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Please Enter Seat number!")
                            .setTitle(R.string.login_error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                   mCurrentUser.put(ParseConstants.KEY_SEATNUMBER,text);
                    mCurrentUser.put(ParseConstants.KEY_FLOOR,mFloor);
                    mCurrentUser.saveInBackground();
               Intent intent = new Intent(getActivity(),ProfilePicture.class);
                startActivity(intent);
            }
            }
        });

        return view;
    }

}