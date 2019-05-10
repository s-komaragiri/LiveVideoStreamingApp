package com.example.sivakomaragiri.bambstreamlive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sivakomaragiri.bambstreamlive.beans.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail;
    EditText editTextPassword;
    Button buttonLogin;
    Button buttonRegister;

    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        editTextEmail=findViewById(R.id.editText_email);
        editTextPassword=findViewById(R.id.editText_password);
        buttonLogin=findViewById(R.id.email_sign_in_button);
        buttonRegister=findViewById(R.id.button_register);

        mAuth = FirebaseAuth.getInstance();


        if(FirebaseAuth.getInstance().getCurrentUser() != null){

            Intent threadActivity = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(threadActivity);
            finish();
            return;
        }


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.length()>0&&password.length()>0) {

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {


                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                }


            }

        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if(email.length()>0&&password.length()>0) {

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                pushNewUser (task.getResult().getUser());

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void pushNewUser(FirebaseUser firebaseuser) {

        User user = new User();

        user.setUserid(firebaseuser.getUid());
        user.setUserMail(firebaseuser.getEmail());
        user.setLive(false);
        user.setLastEdited(Long.parseLong("0"));

        FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseuser.getUid()).setValue(user);

    }

}
