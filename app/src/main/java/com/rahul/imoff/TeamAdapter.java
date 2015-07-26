package com.rahul.imoff;

import android.content.Context;
import android.graphics.Bitmap;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

/**
 * Created by Apple on 20/05/15.
 */
public class TeamAdapter extends ArrayAdapter<ParseUser> {

    protected Context mContext;
    protected List<ParseUser> mTeamMembers;

    public TeamAdapter(Context context, List<ParseUser> team) {
        super(context, R.layout.team_item, team);
        mContext = context;
        mTeamMembers = team;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.team_item, null);
            holder = new ViewHolder();
//            holder.profileImageView = (ImageView) convertView.findViewById(R.id.profilePic);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabel);
            holder.statusLabel = (ImageView) convertView.findViewById(R.id.statusLabel);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ParseUser member = mTeamMembers.get(position);

        Date createdAt = member.getCreatedAt();

        holder.nameLabel.setText(member.getUsername());


//        ParseQuery<ParseUser> query = ParseUser.getQuery();
//        query.whereEqualTo(ParseConstants.KEY_USERID, member.getObjectId());
//
//        query.getInBackground("svjq1hEE48",
//                new GetCallback<ParseUser>() {
//
//                    @Override
//                    public void done(ParseUser user,
//                                     ParseException e) {
//                        // TODO Auto-generated method stub
//
//                        // Locate the column named "ImageName" and set
//                        // the string
//
//                        ParseFile fileObject = (ParseFile) user.get(ParseConstants.KEY_PROFILE_PHOTO);
//
//                        fileObject
//                                .getDataInBackground(new GetDataCallback() {
//
//                                    public void done(byte[] data,
//                                                     ParseException e) {
//                                        if (e == null) {
//                                            Log.d("test",
//                                                    "We've got data in data.");
//                                            // Decode the Byte[] into
//                                            // Bitmap
//                                            Bitmap bmp = BitmapFactory
//                                                    .decodeByteArray(
//                                                            data, 0,
//                                                            data.length);
//
//                                            // Get the ImageView from
//                                            // main.xml
//                                            holder.profileImageView.setImageBitmap(new ParseFile(member.get(ParseConstants.KEY_PROFILE_PHOTO)));
//
//                                            // Set the Bitmap into the
//                                            // ImageView
//
//
//
//                                        }
//                                    }
//                                }
//                    }
//
//
//                }

        if (member.get(ParseConstants.KEY_OFFSTATUS).equals(Boolean.TRUE)) {
            holder.statusLabel.setImageResource(R.drawable.ic_person_outline_black_36dp);
        } else {
            holder.statusLabel.setImageResource(R.drawable.ic_person_black_36dp);
        }
//
//        ParseFile parseFile = member.getParseFile(ParseConstants.KEY_PROFILE_PHOTO);
//        Uri fileUri = Uri.parse(parseFile.getUrl());
//        Picasso.with(getContext()).load(fileUri.toString()).into(holder.profileImageView);


//        byte[] bytes = (byte[])(member.get(ParseConstants.KEY_PROFILE_PHOTO));
//
//        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0,bytes.length);
//        holder.profileImageView.setImageBitmap((Bitmap) member.get(ParseConstants.KEY_PROFILE_PHOTO));

        return convertView;
    }

    private static class ViewHolder {
        //ImageView profileImageView;
        TextView nameLabel;
        ImageView statusLabel;
    }

    public void refill(List<ParseUser> members) {
        mTeamMembers.clear();
        mTeamMembers.addAll(members);
        notifyDataSetChanged();
    }
}
