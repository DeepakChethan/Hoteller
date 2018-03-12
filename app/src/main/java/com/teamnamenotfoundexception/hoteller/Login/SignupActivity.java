package com.teamnamenotfoundexception.hoteller.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.R;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "TAG" ;
    private EditText email,pass;
    private Button signIn,signUp;
    private String email_text,pass_text;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = (EditText) findViewById(R.id.semail);
        pass = (EditText) findViewById(R.id.spass);
        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.sprogress);
    }

    @Override
    public void onClick(View v) {

    }
    public void onSignUpButtonClicked(View v) {

        progressBar.setVisibility(View.VISIBLE);
        email_text = email.getText().toString();
        pass_text = pass.getText().toString();
        if (email_text.isEmpty() && pass_text.isEmpty()){
            Toast.makeText(getApplicationContext(),"All fields are mandatory!",Toast.LENGTH_SHORT).show();
            return;
        }
        auth.createUserWithEmailAndPassword(email_text,pass_text).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Something went wrong!",
                            Toast.LENGTH_SHORT).show();
                    Log.i(TAG, "onComplete: "+task);
                } else {
                    Toast.makeText(getApplicationContext(), "Successfully signed up, login to continue", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    Log.i("i", "logging in");
                    finish();
                }

            }
        });

    }

}

