package com.teamnamenotfoundexception.hoteller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.teamnamenotfoundexception.hoteller.DCAdapter;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Login.LoginActivity;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private ArrayList<DishItem> dishItems;
    private FirebaseAuth auth;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        auth= FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
        dishItems = new ArrayList<>();
        dishItems.add(new DishItem(1,"Dosa","Nice",20,1,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));
        dishItems.add(new DishItem(2,"Dosa","Nice",20,1,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));
        dishItems.add(new DishItem(3,"Dosa","Nice",20,1,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));
        dishItems.add(new DishItem(4,"Dosa","Nice",20,1,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));

        // The recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        //RecyclerView.LayoutManager lop = new GridLayoutManager(getApplicationContext(),2);
        llm = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        DCAdapter adapter = new DCAdapter(getApplicationContext(),dishItems);
        recyclerView.setAdapter(adapter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
            //TODO implement the search
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu) {

        } else if (id == R.id.favs) {

        } else if (id == R.id.cart) {
            startActivity(new Intent(getApplicationContext(),CartActivity.class));
        }  else
         if (id == R.id.logout) {

//           // CartManager.get(getApplication()).getAuth().signOut();
//
//            if(CartManager.get(getApplicationContext()).getAuth().getCurrentUser() == null) {
//                Log.i("after signing out", "i have logged out");
//            }

            CartManager.get(getApplication()).setAuth(null);
          //  Log.i("before logout", CartManager.get(getApplicationContext()).getUser().getEmail());
            CartManager.get(getApplicationContext()).setUser(null);
            CartManager.get(getApplicationContext()).setFirebaseDatabase(null);

//            if(CartManager.get(getApplicationContext()).getUser() == null) {
//                Log.i("i", "successfully set it");
//            } else {
//                Log.i("i", "not set it to null");
//            }
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {

            // TODO logout of the app

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
