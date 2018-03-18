package com.teamnamenotfoundexception.hoteller.Activities;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnamenotfoundexception.hoteller.adapters.BillAdapter;
import com.teamnamenotfoundexception.hoteller.adapters.DCAdapter;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishRepository;
import com.teamnamenotfoundexception.hoteller.Login.LoginActivity;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private static ArrayList<DishItem> dishItems;
    private static CartManager mCartManager;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private static DCAdapter mDCAdapter;
    private static DishRepository mDishRepository ;
    private Notification notification;



    @Override
    protected void onResume() {
        super.onResume();
        mDCAdapter.setData(mDishRepository.getDishItemsList());
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


        notification = new Notification(R.id.cart,"Cart Items",1000);
        notification.number = 200;

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


        dishItems = new ArrayList<>(mDishRepository.getDishItemsList());

        // The recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        //RecyclerView.LayoutManager lop = new GridLayoutManager(getApplicationContext(),2);
        llm = new LinearLayoutManager(this.getApplicationContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mDCAdapter = new DCAdapter(MainActivity.this, dishItems);
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
    protected void onPostResume() {
        super.onPostResume();
        System.out.println("resuming now");
        mDCAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newText = newText.toLowerCase();
                ArrayList<DishItem> newList = new ArrayList<>();
                dishItems = mDishRepository.getDishItemsList();
                for(DishItem dishItem: dishItems){
                    String name = dishItem.getDishName().toLowerCase();
                    String cat = dishItem.getDishType().toLowerCase();
                    Log.i("dc",name+" "+newText);
                    if (name.contains(newText) || cat.contains(newText)){
                        newList.add(dishItem);
                    }
                    mDCAdapter.setFilter(newList);
                    return true;
                }
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.cart){
            startActivity(new Intent(getApplicationContext(),CartActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Activity activity = this;
        if (id == R.id.menu) {
            if (activity instanceof MainActivity) return true;
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        } else if (id == R.id.favs) {
            if (activity instanceof FavoriteActivity) return true;
            startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
        } else if (id == R.id.cart) {
            if (activity instanceof CartActivity) return true;
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

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
