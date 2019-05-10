package com.example.sivakomaragiri.bambstreamlive;

import android.content.Intent;
import android.graphics.Camera;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sivakomaragiri.bambstreamlive.beans.FollowList;
import com.example.sivakomaragiri.bambstreamlive.beans.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<User> broadcastArrayList;
    ArrayList<FollowList> userFollowers;
    String email;

    Camera mCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        listView = findViewById(R.id.listView_broadcastsList);


        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                broadcastArrayList = new ArrayList<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    User value = child.getValue(User.class);
                    broadcastArrayList.add(value);
                }

                setUserAdapter(broadcastArrayList,userFollowers);
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

                setUserAdapter(broadcastArrayList,userFollowers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedUser= userFollowers.get(i).getUserid();
                FirebaseDatabase.getInstance().getReference().child("Users").child(selectedUser).child("userMail").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        email=dataSnapshot.getValue(String.class);

                        Intent playerIntent = new Intent(HomeActivity.this,PlayerActivity.class);
                        playerIntent.putExtra("AUTHOR_NAME",email);
                        startActivity(playerIntent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                //FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child()
            }
        });

    }

    public void setUserAdapter(ArrayList<User> list,ArrayList<FollowList> userFollowers){

        ArrayList<User> subUser = null;

        if(userFollowers!=null&&broadcastArrayList!=null) {

                subUser = new ArrayList<>();
                for(int i=0;i<userFollowers.size();i++){
                    String uid = userFollowers.get(i).getUserid();
                    for(int j=0;j<broadcastArrayList.size();j++){
                        if(broadcastArrayList.get(j).getUserid().equals(uid)){
                        subUser.add(broadcastArrayList.get(j));
                        }
                    }
                }


                BroadCasts_Adapter adapter = new BroadCasts_Adapter(this, R.layout.list_broadcast, subUser, userFollowers);
                listView.setAdapter(adapter);
            }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newmail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item_follow_list:
                Intent intent_compose = new Intent(HomeActivity.this,Userslist.class);
                startActivity(intent_compose);
                return true;
            case R.id.item_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent_logout = new Intent(this, LoginActivity.class);
                startActivity(intent_logout);
                finishAffinity();
                return true;
            case R.id.media_route_menu_item:
                Intent intent_broadcast = new Intent(this, MainActivity.class);
                startActivity(intent_broadcast);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
