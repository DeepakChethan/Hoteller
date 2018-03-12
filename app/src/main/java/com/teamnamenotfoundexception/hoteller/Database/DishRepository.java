package com.teamnamenotfoundexception.hoteller.Database;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by sagar on 3/13/18.
 */

public class DishRepository  {

    public static final String TAG = "DishRepository";

    private static DatabaseHelper mDatabasehelper;

    private DishRepository mDishRepository = null;

    private DatabaseHelper mDatabaseHelper = null;

    private Context mAppContext;

    private SharedPreferences mSharedPref;


    private DishRepository(Context context) {
        mAppContext = context;
        mDatabasehelper = new DatabaseHelper(mAppContext);
        mSharedPref = mAppContext.getSharedPreferences("user_data", Context.MODE_PRIVATE);
    }


    public DishRepository  get(Context c) {
        if(mDishRepository == null) {
            mDishRepository = new DishRepository(c);
        }
        return mDishRepository;
    }

    public ArrayList<DishItem> getAllDishes() {
        DatabaseHelper.DishItemCursor mQuestionCursor = mDatabaseHelper.getAllDishItems();
        ArrayList<DishItem> dishItemsList = new ArrayList<>();
        if(mQuestionCursor.getCount() > 0) {
            for(int i = 0; i < mQuestionCursor.getCount(); i++) {
                DishItem dishItem = mQuestionCursor.getDishItem();
                dishItemsList.add(dishItem);
                mQuestionCursor.moveToNext();
            }
        }
        mQuestionCursor.close();
        return dishItemsList;
    }

}
