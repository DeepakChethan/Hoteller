package com.teamnamenotfoundexception.hoteller.Activities;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.teamnamenotfoundexception.hoteller.TutorialActivity;
import com.teamnamenotfoundexception.hoteller.Database.UpdateNotificationCount;
import com.teamnamenotfoundexception.hoteller.adapters.BillAdapter;
import com.teamnamenotfoundexception.hoteller.adapters.DCAdapter;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishRepository;
import com.teamnamenotfoundexception.hoteller.Login.LoginActivity;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, UpdateNotificationCount {

    private RecyclerView recyclerView;
    private LinearLayoutManager llm;
    private static ArrayList<DishItem> dishItems;
    private static CartManager mCartManager;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private static DCAdapter mDCAdapter;
    private static DishRepository mDishRepository ;
    private TextView notiCount;
    private ProgressBar progressBar;

    int cartCount;

    @Override
    public void updateNotiCount() {
        updateCount(CartManager.get(getApplicationContext()).getCartItems().size());
    }

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
        CartManager.get(getApplicationContext()).setListenerInterface(this);

        SharedPreferences sp = getSharedPreferences("tut",MODE_PRIVATE);
        Boolean sc = sp.getBoolean("show",false);

        if (!sc){
            sp.edit().putBoolean("show",true).apply();
            Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Toast.makeText(getApplicationContext(),sp.getBoolean("show",false)+" So taking you there",Toast.LENGTH_SHORT).show();
        }


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

        progressBar = (ProgressBar) findViewById(R.id.tempProgress);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);

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


    public void updateCount(final int new_hot_number) {
        cartCount = new_hot_number;
        if (notiCount == null) return;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (new_hot_number == 0)
                    notiCount.setVisibility(View.INVISIBLE);
                else {
                    notiCount.setVisibility(View.VISIBLE);
                    notiCount.setText(Integer.toString(new_hot_number));
                }
            }
        });
    }


    static abstract class MyMenuItemStuffListener implements View.OnClickListener {
        private String hint;
        private View view;

        MyMenuItemStuffListener(View view, String hint) {
            this.view = view;
            this.hint = hint;
            view.setOnClickListener(this);

        }

        @Override abstract public void onClick(View v);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        //MenuItem menuItem1= menu.findItem(R.id.dcart);
        final MenuItem menuItem1 =  menu.findItem(R.id.dcart);
        View dopeCart = (View) menuItem1.getActionView();
        if(dopeCart == null) {
            Log.i("hey", "hey null mate");

        } else {

            notiCount = dopeCart.findViewById(R.id.notiCount);
            updateCount(CartManager.get(getApplicationContext()).getCartItems().size());

            new MyMenuItemStuffListener( dopeCart, "hint") {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent);
                }
            };
        }

        android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(menuItem);

        searchView.setOnQueryTextListener(new android.support.v7.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String newText) {
                return false;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == ""){
                    mDCAdapter.setData(mDishRepository.getDishItemsList());
                    return true;

                }
                newText = newText.toLowerCase();
                ArrayList<DishItem> newList = new ArrayList<>();
                dishItems = mDishRepository.getDishItemsList();
                System.out.println("size is" + dishItems.size());
                for(int i = 0; i < dishItems.size(); i++){
                    DishItem dishItem = dishItems.get(i);
                    String name = dishItem.getDishName().toLowerCase();
                    String cat = dishItem.getDishType().toLowerCase();
                    Log.i("dc",name+" "+newText);
                    if (name.contains(newText) || cat.equals(newText)) {
                       // if(CartManager.get(getApplicationContext()).getFavoriteIdList().contains(dishItem.getDishId())) {
                       //     dishItem.setDishFav(1);
                       // }
                        newList.add(dishItem);
                    }
                    mDCAdapter.setData(newList);
                }
                return true ;
            }





        });
        searchView.setOnCloseListener(new android.support.v7.widget.SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                mDCAdapter.setData(mDishRepository.getDishItemsList());
                System.out.println("oncloselistener");
                return false;
            }
        });
        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();
        Activity activity = this;
        if (id == R.id.menu) {
            if (activity instanceof MainActivity) return true;
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        } else if (id == R.id.help){
            startActivity(new Intent(getApplicationContext(),TutorialActivity.class));
        }
        else if (id == R.id.favs) {
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
