package com.teamnamenotfoundexception.hoteller.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
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
import com.google.firebase.database.FirebaseDatabase;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishRepository;
import com.teamnamenotfoundexception.hoteller.R;
import com.teamnamenotfoundexception.hoteller.TutorialActivity;

public class LoginActivity extends AppCompatActivity{

    private EditText email,pass;
    private Button signIn,signUp;
    private String email_text,pass_text;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp = getSharedPreferences("tut",getApplicationContext().MODE_PRIVATE);
        Boolean sc = sp.getBoolean("show",false);

        if (!sc){
            sp.edit().putBoolean("show",true).apply();
            Intent intent = new Intent(this, TutorialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),sp.getBoolean("show",false)+" So taking you there",Toast.LENGTH_SHORT).show();
            finish();
            return ;
        }
        email = (EditText) findViewById(R.id.email);
        pass = (EditText) findViewById(R.id.pass);
        signIn = (Button) findViewById(R.id.lloginBtn);
        signUp = (Button) findViewById(R.id.lsignUpButton);
        mAuth = FirebaseAuth.getInstance();
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoginButtonClicked(v);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignUpButtonClicked(v);
            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress);
        signIn = (Button) findViewById(R.id.lloginBtn);
        signUp = (Button) findViewById(R.id.lsignUpButton);
    }


    public void onLoginButtonClicked(View v) {
        signIn.setEnabled(false);
        signUp.setEnabled(false);
        email_text = email.getText().toString();
        pass_text= pass.getText().toString();
        progressBar.setVisibility(View.VISIBLE);
        if(!isNetworkAvailableAndConnected()) {
            Toast.makeText(getApplicationContext(), "get a connection to internet", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            signIn.setEnabled(true);
            signUp.setEnabled(true);
            return;
        } else if ( email_text.isEmpty() || pass_text.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Fill this thing up!",Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.INVISIBLE);
            signIn.setEnabled(true);
            signUp.setEnabled(true);
            return;
        }


        mAuth.signInWithEmailAndPassword(email_text, pass_text)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Check your creds!",
                                    Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);

                        } else {
                            CartManager.get(getApplicationContext()).setAuth(FirebaseAuth.getInstance());
                            CartManager.get(getApplicationContext()).setUser(FirebaseAuth.getInstance().getCurrentUser());
                            CartManager.get(getApplicationContext()).setFirebaseDatabase(FirebaseDatabase.getInstance());
                            DishRepository.get(getApplicationContext()).insertAllDishItems();
                            DishRepository.get(getApplicationContext()).initializeDishItemsList();
                            CartManager.get(getApplicationContext()).initializeFavoriteList();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            Log.i("i", "logging in");
                            progressBar.setVisibility(View.INVISIBLE);
                            signIn.setEnabled(true);
                            signUp.setEnabled(true);
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
        Toast.makeText(getApplicationContext(),"Working!",Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

