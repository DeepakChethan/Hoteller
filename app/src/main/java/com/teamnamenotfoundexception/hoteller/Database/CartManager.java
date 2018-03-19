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
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;

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

    private ArrayList<Integer> mFavoriteList ;

    private int mTotalOrderPrice = 0;


    private CartManager(Context context) {

        mAppContext = context;

        mAuth = null;

        mUser = null;

        mFavoriteList = new ArrayList<>();

        if (mUser != null) Log.i("i", mUser.getEmail());

        mCartItems = new ArrayList<>();

        mFavItems = new ArrayList<>();

        mFirebaseDatabase = null;

        mFirebaseHelper = new FirebaseHelper(mAppContext);

    }

    public void setFavoriteList(ArrayList<Integer> arrayList) {
        this.mFavoriteList = arrayList;
    }


    public static CartManager get(Context c) {

        if (mCartManager == null) {
            mCartManager = new CartManager(c);
            Log.i("i", "Cart Manager initialized");
        }
        return mCartManager;
    }

    public void initializeFavoriteList() {
        try {
            mFirebaseHelper.fetchFavoriteList(mUser);
        } catch(Exception e) {
            Log.i("hkkgja", "error fetching favs");
        }
    }

    public void addToFavorites(DishItem item) {

        try {
            CartManager.get(mAppContext).getFavoriteIdList().add(item.getDishId());
            mFirebaseHelper.updateFavoriteList(mFavoriteList, mUser);
            MainActivity.notifyMe();
        } catch(Exception e) {
            Log.i("error", "cannot update favorite list");
        }
    }

    public void removeFromFavorites(DishItem item) {
            try {
                CartManager.get(mAppContext).getFavoriteIdList().remove(new Integer(item.getDishId()));
                mFirebaseHelper.updateFavoriteList(mFavoriteList, mUser);
                System.out.println("removing fav" + item.getDishName());
                MainActivity.notifyMe();
            } catch(Exception e) {
                Log.i("error", "cannto update after removing");

            }

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
        ArrayList<DishItem> favoritedItems = new ArrayList<DishItem>();
        ArrayList<DishItem> alldishlist = DishRepository.get(mAppContext).getDishItemsList();
        for(int i = 0; i < alldishlist.size(); i++) {
            if(alldishlist.get(i).isDishFav() == 1) {
                favoritedItems.add(alldishlist.get(i));
            }
        }
        return favoritedItems;
    }

    public ArrayList<Integer> getFavoriteIdList() {
        return mFavoriteList;
    }

    public ArrayList<DishItem> getCartItems() {
        return mCartItems;
    }

    public void addDishToCart(DishItem item) {
        mCartItems.add(item);
        mTotalOrderPrice += item.getTotalPrice();
        Log.i("cart ", "item added with price " + item.getPrice() +  " with total price " + item.getTotalPrice() + " " + mTotalOrderPrice);
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
        item.setQuantity(0);
    }
    public double getFinalTotalOrderPrice(float tax){
        return mTotalOrderPrice + (mTotalOrderPrice*tax);
    }

    public int getTotalOrderPrice() {
        return mTotalOrderPrice;
    }

    public void  setUser(FirebaseUser user) {
        this.mUser = user;
    }

    public void setCartManagerToNull() {
        mCartManager = null;
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

    public void setCartItemsList(ArrayList<DishItem> cartItemsList) {
        this.mCartItems = cartItemsList;
    }

    public void resetCartItems() {
        for(int i = 0; i < mCartItems.size(); i++) {
            DishItem dishItem = mCartItems.get(i);
            dishItem.setQuantity(0);
            dishItem.setIsCart(0);
        }
        mCartItems = new ArrayList<>();
        mTotalOrderPrice = 0;
    }

}
