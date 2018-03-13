package com.teamnamenotfoundexception.hoteller.Database;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by sagar on 3/13/18.
 */

public class FirebaseHelper {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference ;

    FirebaseHelper() {
        mFirebaseDatabase = null;
    }

    public void setFirebaseDatabase(FirebaseDatabase firebaseDatabase) {
        mFirebaseDatabase = firebaseDatabase;
        if(firebaseDatabase != null)
            mDatabaseReference = mFirebaseDatabase.getReference("CustomerData/" );
    }


    public void placeOrder(ArrayList<DishItem> itemsInCart, FirebaseUser user) {

        Log.i("order", "placeOrder called");

        OrderObject orderObject= new OrderObject(itemsInCart);

        String emailIdSplit[] = user.getEmail().split("@");

        String emailId = emailIdSplit[0];


        try {
            mDatabaseReference.child(user.getUid()).child(emailId).child("orders").push().setValue(orderObject);
        } catch(Exception e) {
            Log.i("orderError", "error in placing the order");
        }

    }

    public void makeFavorite(DishItem dishItem, FirebaseUser user) {

        Log.i("favorite", "make favorite caleld");

        int itemId = dishItem.getDishId();

        String emailIdSplit[] = user.getEmail().split("@");
        String emailId = emailIdSplit[0];

        FavoriteObject favoriteObject = new FavoriteObject(itemId);

        try {

            mDatabaseReference.child(user.getUid()).child(emailId).child("favorites").push().setValue(favoriteObject);

        } catch(Exception e) {

            Log.i("favoriteError", "error in placing the order");

        }

    }


    class FavoriteObject {
        public int itemId;
        public FavoriteObject() {

        }

        public FavoriteObject(int dishItemId) {
            itemId = dishItemId;
        }
    }

    class OrderObject {

        public UUID orderId;
        public ArrayList<DishItem> orderedItems ;

        public OrderObject() {
           orderId = UUID.randomUUID();
           orderedItems = new ArrayList<>();
        }

        public OrderObject (ArrayList<DishItem> orderedItems) {
            orderId = UUID.randomUUID();
            this.orderedItems = orderedItems;
        }

    }

}
