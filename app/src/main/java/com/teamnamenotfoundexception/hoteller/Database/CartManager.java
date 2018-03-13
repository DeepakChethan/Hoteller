package com.teamnamenotfoundexception.hoteller.Database;

/**
 * Created by sagar on 3/13/18.
 */

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class CartManager {

    public static final String TAG = "CartManager";

    private FirebaseAuth mAuth = null;

    private FirebaseUser mUser = null;

    private static FirebaseDatabase mFirebaseDatabase = null;

    private static FirebaseHelper mFirebaseHelper = null;

    private ArrayList<DishItem> mCartItems;

    private ArrayList<DishItem> mFavItems;

    private Context mAppContext;

    private static CartManager mCartManager = null;

    private int mTotalOrderPrice = 0;


    private CartManager(Context context) {

        mAppContext = context;

        mAuth = null;

        mUser = null;

        if (mUser != null) Log.i("i", mUser.getEmail());

        mCartItems = new ArrayList<>();

        mFavItems = new ArrayList<>();

        mFirebaseDatabase = null;

        mFirebaseHelper = new FirebaseHelper();

    }


    public static CartManager get(Context c) {

        if (mCartManager == null) {
            mCartManager = new CartManager(c);
            Log.i("i", "Cart Manager initialized");
        }
        return mCartManager;
    }

    public void placeOrder() {
        if (mCartItems == null || mCartItems.size() == 0) {
            Toast.makeText(mAppContext, "Your order doesn't have any items. try adding some items from our delicacies, prepared just for you :) ", Toast.LENGTH_LONG).show();
            return;
        }
        mFirebaseHelper.placeOrder(mCartItems, mUser);
        Toast.makeText(mAppContext, "Your order placed successfully, you can enjoy in few days", Toast.LENGTH_LONG).show();
    }

    public ArrayList<DishItem> getFavItems() {
        return mFavItems;
    }

    public ArrayList<DishItem> getCartItems() {
        return mCartItems;
    }

    public void addDishToCart(DishItem item) {
        mCartItems.add(item);
        mTotalOrderPrice += item.getTotalPrice();
    }

    public void addDishToFav(DishItem item) {
        mFavItems.add(item);
    }

    public void removeDishFromFav(DishItem item) {
        mFavItems.remove(item);
    }

    public void removeDishFromCart(DishItem item) {
        mCartItems.remove(item);
        mTotalOrderPrice -= item.getTotalPrice();
    }
    public double getFinalTotalOrderPrice(float tax){
        return mTotalOrderPrice+(mTotalOrderPrice*tax);
    }

    public int getTotalOrderPrice() {
        return mTotalOrderPrice;
    }

    public void  setUser(FirebaseUser user) {
        this.mUser = user;
    }

    public void setAuth(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
       this.mFirebaseDatabase = firebaseDatabase;
       mFirebaseHelper.setFirebaseDatabase(mFirebaseDatabase);
    }

    public FirebaseAuth getAuth() {
        return mAuth;
    }

    public FirebaseUser getUser() {
        return mUser;
    }

    public FirebaseDatabase getFirebaseDatabase() {
        return mFirebaseDatabase;
    }

}
