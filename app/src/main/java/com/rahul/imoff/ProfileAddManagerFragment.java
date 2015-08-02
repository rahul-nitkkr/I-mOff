package com.rahul.imoff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import android.app.AlertDialog;

import android.util.Log;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;


public class ProfileAddManagerFragment extends Fragment {

    public static final String TAG = ProfileAddManagerFragment.class.getSimpleName();

    protected ParseRelation<ParseUser> mManager;
    protected ParseUser mCurrentUser;
    protected List<ParseUser> mManagers;
    protected GridView mGridView;
    protected List<ParseUser> mUsers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater
                .inflate(R.layout.fragment_profile_team, container, false);


        mGridView = (GridView)view.findViewById(R.id.managerGrid);

        TextView emptyTextView = (TextView)view.findViewById(android.R.id.empty);
        mGridView.setEmptyView(emptyTextView);
        mGridView.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        mGridView.setOnItemClickListener(mOnItemClickListener);


        Button btn = (Button) view.findViewById(R.id.btn);

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//                FragmentTransaction trans = getFragmentManager()
//                        .beginTransaction();
//                //trans.replace(R.id.root_frame, new StaticFragment());
//                trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                trans.addToBackStack(null);
//                trans.commit();

                Intent intent = new Intent(getActivity(),ProfilePicture.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mCurrentUser = ParseUser.getCurrentUser();
        mManager = mCurrentUser.getRelation(ParseConstants.KEY_MANAGER);

        getActivity().setProgressBarIndeterminateVisibility(true);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereExists(ParseConstants.KEY_USERNAME);

        query.whereEqualTo(ParseConstants.KEY_ISMANAGER,Boolean.TRUE);

       // query.whereNotEqualTo(ParseConstants.KEY_USERNAME,mCurrentUser.getUsername());
        query.addAscendingOrder(ParseConstants.KEY_USERNAME);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> team_members, ParseException e) {
                getActivity().setProgressBarIndeterminateVisibility(false);

                if (e == null) {
                    mManagers = team_members;

                    String[] usernames = new String[mManagers.size()];
                    int i = 0;
                    for(ParseUser user : mManagers) {
                        usernames[i] = user.getUsername();
                        i++;
                    }
                    if (mGridView.getAdapter() == null) {
                        UserAdapter adapter = new UserAdapter(getActivity(), mManagers);
                        mGridView.setAdapter(adapter);
                    }
                    else {
                        ((UserAdapter)mGridView.getAdapter()).refill(mManagers);
                    }
                }
                else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.error_title)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
    protected AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            ImageView checkImageView = (ImageView)view.findViewById(R.id.checkImageView);

            if (mGridView.isItemChecked(position)) {
                // add the friend
                mManager.add(mManagers.get(position));
                checkImageView.setVisibility(View.VISIBLE);
            }
            else {
                // remove the friend
                mManager.remove(mManagers.get(position));
                checkImageView.setVisibility(View.INVISIBLE);
            }

            mCurrentUser.put(ParseConstants.KEY_OFFSTATUS,Boolean.FALSE);
            mCurrentUser.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            });

        }
    };

}


