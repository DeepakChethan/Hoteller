package com.teamnamenotfoundexception.hoteller.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText email,pass;
    private Button signIn,signUp;
    private String email_text,pass_text;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {

    }

    public void onLoginButtonClicked(View v) {
        Toast.makeText(this, "we will do this", Toast.LENGTH_SHORT).show();
    }

    public void onSignUpButtonClicked(View v) {
        startActivity(new Intent(this, SignupActivity.class));
    }
}

