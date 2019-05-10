package com.example.sivakomaragiri.bambstreamlive;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sivakomaragiri.bambstreamlive.beans.FollowList;
import com.example.sivakomaragiri.bambstreamlive.beans.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UsersAdapter extends ArrayAdapter<User> {

    List<User> mailsArrayList;
    ArrayList<FollowList> userFollowing;
    Context context;
    public static String LABEL_FOLLOW="Follow";
    public static String LABEL_FOLLOWING="Following";
    ViewHolder viewHolder = null;
    FollowInterface followInterface=null;


    public UsersAdapter(@NonNull Context context, int resource, @NonNull List<User> objects, ArrayList<FollowList> userFollowing) {
        super(context, resource, objects);
        this.context=context;
        this.mailsArrayList = objects;
        followInterface= (FollowInterface) context;
        this.userFollowing=userFollowing;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final User user = getItem(position);



        if (null == convertView) {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_userlist, parent, false);

            viewHolder = new ViewHolder();

            viewHolder.textView_userName = convertView.findViewById(R.id.textView_user);
            viewHolder.followButton=convertView.findViewById(R.id.button_follow);

            //viewHolder.followButton.setFocusable(false);

            //viewHolder.followButton.setFocusableInTouchMode(false);


            convertView.setTag(viewHolder);


        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }



        viewHolder.textView_userName.setText(user.getUserMail());

        //viewHolder.followButton.setText(LABEL_FOLLOW);

        if(userFollowing!=null) {

            for (int i = 0; i < userFollowing.size(); i++) {

                if (userFollowing.get(i).getUserid().equals(user.getUserid())) {
                    viewHolder.followButton.setText(LABEL_FOLLOWING);
                }

            }
        }

        viewHolder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                followInterface.followUpdate(user.getUserid());
            }
        });


        return convertView;

    }


    public static class ViewHolder {

        TextView textView_userName;
        Button followButton;

        public ViewHolder() {


        }

    }

    public interface FollowInterface{
        public void followUpdate(String userid);
    }
}
