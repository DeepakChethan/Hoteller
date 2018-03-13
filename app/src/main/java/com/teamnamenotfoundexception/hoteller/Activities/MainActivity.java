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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnamenotfoundexception.hoteller.DCAdapter;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishRepository;
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
    private CartManager mCartManager;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private static DCAdapter mDCAdapter;

    private DishRepository mDishRepository ;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        mCartManager = CartManager.get(getApplicationContext());
        mDishRepository = DishRepository.get(getApplicationContext());

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        if (mUser == null) {
            mCartManager.setCartManagerToNull();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return;
        } else {
            CartManager.get(getApplicationContext()).setAuth(FirebaseAuth.getInstance());
            CartManager.get(getApplicationContext()).setUser(FirebaseAuth.getInstance().getCurrentUser());
            CartManager.get(getApplicationContext()).setFirebaseDatabase(FirebaseDatabase.getInstance());
            DishRepository.get(getApplicationContext()).initializeDishItemsList();
            CartManager.get(getApplicationContext()).initializeFavoriteList();
        }
        Log.i("i", "staying here only");
//        Log.i("i", mUser.getEmail());


        dishItems = mDishRepository.getDishItemsList();

   /*    dishItems = new ArrayList<>();
        dishItems.add(new DishItem(1,"Dosa","Tiffin",20,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));
        dishItems.add(new DishItem(2,"Dosa","Dinner",20,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));
        dishItems.add(new DishItem(3,"Dosa","Lunch",20,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));
        dishItems.add(new DishItem(4,"Dosa","Drink",20,"This is nice","https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcSJMXVFN37IhEBdpCBi6hprdsuw61C1ToRahYkkqDShUxBcu0jUFqPzMDxE"));

*/

        // The recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        //RecyclerView.LayoutManager lop = new GridLayoutManager(getApplicationContext(),2);
        llm = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mDCAdapter = new DCAdapter(getApplicationContext(), dishItems);
        recyclerView.setAdapter(mDCAdapter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public static void notifyMe() {
        Log.i("notifed called", "notifed called");
        if(mDCAdapter != null) {
            mDCAdapter.notifyDataSetChanged();
            System.out.println("notifying");
        }
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
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        } else if (id == R.id.favs) {
            startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
        } else if (id == R.id.cart) {
            startActivity(new Intent(getApplicationContext(),CartActivity.class));
        }  else
         if (id == R.id.logout) {
             try {

                 mAuth.signOut();
                 mCartManager.setAuth(null);
                 mCartManager.setFirebaseDatabase(null);
                 mCartManager.setUser(null);
                 mCartManager.setCartManagerToNull();
                 DishRepository.setDishRepository(null);

             } catch(Exception e) {
                 Toast.makeText(getApplicationContext(), "trouble logging you out, check your connection", Toast.LENGTH_SHORT).show();
             }

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {



        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
