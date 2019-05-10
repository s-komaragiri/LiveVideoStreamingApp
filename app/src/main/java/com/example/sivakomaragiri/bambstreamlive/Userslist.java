package com.example.sivakomaragiri.bambstreamlive;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.sivakomaragiri.bambstreamlive.beans.FollowList;
import com.example.sivakomaragiri.bambstreamlive.beans.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Userslist extends AppCompatActivity implements UsersAdapter.FollowInterface {

    ArrayList<User> users;
    ListView listView;

    ArrayList<FollowList> userFollowers;

    ArrayList<String> userFollowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userslist);

        listView = findViewById(R.id.listView_users);

        final String currentUid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

      /*  FirebaseDatabase.getInstance().getReference().child("test").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value=dataSnapshot.child("value").getValue(String.class).toString();
                System.out.println(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */

       FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    User value = child.getValue(User.class);
                    if (!value.getUserid().equals(currentUid)) {
                        users.add(value);
                    }
                }

                setUserAdapter(users,userFollowers);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    FirebaseDatabase.getInstance().getReference().child("Ifollow").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userFollowers = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    FollowList value = child.getValue(FollowList.class);
                        userFollowers.add(value);
                }

                setUserAdapter(users,userFollowers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = users.get(i);

               // FirebaseDatabase.getInstance().getReference().child("Ifollow").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
            }
        });

    }

    public void setUserAdapter(ArrayList<User> userslist,ArrayList<FollowList> userFollowing) {
        UsersAdapter adapter = new UsersAdapter(this, R.layout.list_userlist, userslist,userFollowing);
        listView.setAdapter(adapter);
    }


    @Override
    public void followUpdate(String userid) {
        Boolean isDelete = false;
        Log.d("demo", userid);
        FollowList followList = new FollowList();
        followList.setUserid(userid);
        for (int i = 0; i < userFollowers.size(); i++) {
            if (userFollowers.get(i).getUserid().equals(userid)) {
                isDelete = true;
            }
        }

        if (!isDelete) {
            FirebaseDatabase.getInstance().getReference().child("Ifollow").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(userid).setValue(followList);
        } else {
            FirebaseDatabase.getInstance().getReference().child("Ifollow").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(userid).removeValue();
        }
    }

}
