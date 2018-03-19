package com.teamnamenotfoundexception.hoteller.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by sagar on 3/13/18.
 */

public class DishRepository  {

    public static final String TAG = "DishRepository";



    private static DishRepository mDishRepository = null;

    private static DatabaseHelper mDatabaseHelper ;

    private static ArrayList<DishItem> mDishItemsList = null;

    private Context mAppContext;

    private SharedPreferences mSharedPref;


    private DishRepository(Context context) {

        mAppContext = context;
        mDatabaseHelper = new DatabaseHelper(mAppContext);
        mDishItemsList = new ArrayList<>();
        mSharedPref = mAppContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }


    public static void setDishRepository(DishRepository dishRepository) {
        mDishRepository = dishRepository;
    }

    public void insertAllDishItems() {

        Log.i("i", "in inserting function");


      //  String currentUser = CartManager.get(mAppContext).getUser().getEmail();

        boolean isInsertedBefore = mSharedPref.getString("inserted_before", "0").equals("0") ? false: true;

        if(!isInsertedBefore) {

            mDatabaseHelper.insertDishItem(new DishItem(1001,"Biryani", "NonVeg", 120,  "The traditional Indian cuisine that will melt your heart","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcwzS_HDWdb7Tep3QDHaDhZlJnQyo4rIKfp8qPzGmgLGbNUS_WhQ"));
            mDatabaseHelper.insertDishItem(new DishItem(1002,"Mango", "Veg", 130,  "The traditional Indian cuisine that will melt your heart","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTz_fqsFMZryqgtRCiBagoDqWvuhz5qdXzr2QVREc6CnE9RUaw6"));
            mDatabaseHelper.insertDishItem(new DishItem(1003,"Pakoda", "Veg", 135,  "The traditional Indian cuisine that will melt your heart","https://i.ytimg.com/vi/a03U45jFxOI/maxresdefault.jpg"));
            SharedPreferences.Editor editor = mSharedPref.edit();
            editor.putString("inserted_before", "1");
            editor.apply();
            editor.commit();
        }
        else {
            Log.i("i", "it was inserted already");
        }
    }

    public void initializeDishItemsList() {
        if(DishRepository.get(mAppContext).getDishItemsList().size() == 0) {

            DatabaseHelper.DishItemCursor mDishItemCursor = this.mDatabaseHelper.getAllDishItems();

            Log.i("i", "database helper inited");


            if (mDishItemCursor.getCount() != 0) {
                Log.i("i", "mDishcursor size" + mDishItemCursor.getCount());
                mDishItemCursor.moveToFirst();
                while (!mDishItemCursor.isAfterLast()) {
                    DishItem dishItem = mDishItemCursor.getDishItem();
                    Log.i("i", dishItem.getImagePath());
                    mDishItemsList.add(dishItem);
                    mDishItemCursor.moveToNext();
                }
            }
            mDishItemCursor.close();
        }
    }

    public static DishRepository  get(Context c) {
        if(mDishRepository == null) {
            mDishRepository = new DishRepository(c);
            Log.i("i", "dish repo initialized");
        }
        return mDishRepository;
    }


    public ArrayList<DishItem> getDishItemsList() {
        return mDishItemsList;
    }

}
