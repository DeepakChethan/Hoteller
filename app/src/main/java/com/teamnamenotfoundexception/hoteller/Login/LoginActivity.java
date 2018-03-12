package com.teamnamenotfoundexception.hoteller.Login;

import android.content.Intent;
import android.net.ConnectivityManager;
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

        email = groupId.getText().toString();
        password = groupPass.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        if ( email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Fill this thing up!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(!isNetworkAvailableAndConnected()) {
            Toast.makeText(getApplicationContext(),"top up first", Toast.LENGTH_SHORT).show();
            return ;
        }
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Check your creds!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            StatusManager.get(getApplicationContext()).setAuth(FirebaseAuth.getInstance());
                            StatusManager.get(getApplicationContext()).setUser(FirebaseAuth.getInstance().getCurrentUser());
                            StatusManager.get(getApplicationContext()).setFirebaseDatabase(FirebaseDatabase.getInstance());
                            startActivity(new Intent(getApplicationContext(), BufferActivity.class));
                            Log.i("i", "logging in");
                            finish();
                        }
                    }
                });
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && cm.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    public void onSignUpButtonClicked(View v) {
        startActivity(new Intent(this, SignupActivity.class));
    }
}

