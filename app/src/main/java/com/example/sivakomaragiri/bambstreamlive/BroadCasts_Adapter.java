package com.example.sivakomaragiri.bambstreamlive;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sivakomaragiri.bambstreamlive.beans.FollowList;
import com.example.sivakomaragiri.bambstreamlive.beans.User;
import com.example.sivakomaragiri.bambstreamlive.beans.User;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BroadCasts_Adapter extends ArrayAdapter<User> {

    List<User> mailsArrayList;
    ArrayList<FollowList> userFollowers;
    Context context;


    public BroadCasts_Adapter(@NonNull Context context, int resource, @NonNull List<User> objects, ArrayList<FollowList> userFollowers) {
        super(context, resource, objects);
        this.mailsArrayList = objects;
        this.context = context;
        this.userFollowers = userFollowers;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        User broadcast = getItem(position);
        ViewHolder viewHolder = null;


                    if (null == convertView) {

                        convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_broadcast, parent, false);

                        viewHolder = new ViewHolder();

                        viewHolder.textView_userName = convertView.findViewById(R.id.textView_username);
                        viewHolder.imageViewLive = convertView.findViewById(R.id.imageView_videoStatus);
                        viewHolder.textView_timeStamp = convertView.findViewById(R.id.textView_lastseen);

                        convertView.setTag(viewHolder);


                    } else {

                        viewHolder = (ViewHolder) convertView.getTag();

                    }
                    viewHolder.textView_userName.setText(broadcast.getUserMail());

                    if(broadcast.getLastEdited()==0){
                        viewHolder.textView_timeStamp.setText("Never seen");
                    }else {

                        Date messageDate = new Date(broadcast.getLastEdited());

                        PrettyTime prettyTime = new PrettyTime();

                        String datetime = prettyTime.format(messageDate);

                        viewHolder.textView_timeStamp.setText("Seen "+datetime);
                    }

                    if (broadcast.isLive()) {
                        Drawable d = ContextCompat.getDrawable(context, android.R.drawable.presence_video_online);
                        viewHolder.imageViewLive.setImageDrawable(d);
                    } else {
                        Drawable d = ContextCompat.getDrawable(context, android.R.drawable.presence_video_away);
                        viewHolder.imageViewLive.setImageDrawable(d);
                    }


        return convertView;

    }


    public static class ViewHolder {

        TextView textView_userName;
        TextView textView_msgPreview;
        TextView textView_timeStamp;
        ImageView imageViewLive;

        public ViewHolder() {


        }

    }
}

