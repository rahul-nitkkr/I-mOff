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

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by Apple on 20/05/15.
 */
public class FloorAdapter extends ArrayAdapter<Floor> {

    protected Context mContext;
    protected List<Floor> mSeats;

    public FloorAdapter(Context context, List<Floor> seats) {
        super(context, R.layout.floor_item, seats);
        mContext = context;
        mSeats = seats;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.floor_item, null);
            holder = new ViewHolder();
//            holder.profileImageView = (ImageView) convertView.findViewById(R.id.profilePicFloor);
            holder.nameLabel = (TextView) convertView.findViewById(R.id.nameLabelFloor);
            holder.statusLabel = (ImageView) convertView.findViewById(R.id.statusLabelFloor);
            holder.seatLabel = (TextView) convertView.findViewById(R.id.seatLabelFloor);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Floor seat = mSeats.get(position);



        holder.nameLabel.setText(seat.getUsername());
        holder.seatLabel.setText(seat.getSeatno());


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



        holder.statusLabel.setImageResource(R.drawable.ic_event_seat_black_24dp);
//
//        ParseFile parseFile = member.getParseFile(ParseConstants.KEY_PROFILE_PHOTO);
//        Uri fileUri = Uri.parse(parseFile.getUrl());
//        Picasso.with(getContext()).load(fileUri.toString()).into(holder.profileImageView);


//        byte[] bytes = (byte[])(member.get(ParseConstants.KEY_PROFILE_PHOTO));
//
//        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0,bytes.length);
//        holder.profileImageView.setImageBitmap((Bitmap) member.get(ParseConstants.KEY_PROFILE_PHOTO));`

        return convertView;
    }

    private static class ViewHolder {
        //ImageView profileImageView;
        TextView nameLabel;
        ImageView statusLabel;
        TextView seatLabel;
    }

    public void refill(List<Floor> seats) {
        mSeats.clear();
        mSeats.addAll(seats);
        notifyDataSetChanged();
    }
}
