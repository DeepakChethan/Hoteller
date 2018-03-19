package com.teamnamenotfoundexception.hoteller.Database;





import android.app.IntentService;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



/**
 * Created by sagar on 3/13/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {



    private static final String DB_NAME = "restaurante.sqlite";
    public static final int VERSION = 1;

    private static final String TABLE_DISHES = "dishes";
    private static final String COLUMN_DISH_UUID = "dish_uuid";
    private static final String COLUMN_DISH_NAME = "dish_name";
    private static final String COLUMN_DISH_TYPE = "dish_type";
    private static final String COLUMN_DISH_DESCRIPTION = "description";
    private static final String COLUMN_DISH_PRICE = "price";
    private static final String COLUMN_DISH_IMAGE_PATH = "image_path";

    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null,  VERSION);
        Log.i("i", "database helper inited");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table dishes (" + "dish_id integer primary key autoincrement, dish_name varchar(1000), dish_type varchar(1000), dish_uuid int, description varchar(2000), price int, image_path varchar(2000))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public long  insertQuestion(DishItem dishItem) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DISH_NAME, dishItem.getDishName());
        cv.put(COLUMN_DISH_TYPE, dishItem.getDishType());
        cv.put(COLUMN_DISH_PRICE, dishItem.getPrice());
        cv.put(COLUMN_DISH_IMAGE_PATH, dishItem.getImagePath());
        cv.put(COLUMN_DISH_DESCRIPTION, dishItem.getDescription());

        Log.i("inserted", "inserted the dish");
        return getReadableDatabase().insert(TABLE_DISHES, null, cv);
    }

    public void deleteAllRows() {
        getWritableDatabase().delete(TABLE_DISHES, null, null);
    }


    public DishItemCursor getAllDishItems() {
        Cursor wrapped = getReadableDatabase().query(TABLE_DISHES, null, null, null, null, null, null);
        return new DishItemCursor(wrapped);
    }

    public long insertDishItem(DishItem dishItem) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DISH_NAME, dishItem.getDishName());
        cv.put(COLUMN_DISH_TYPE, dishItem.getDishType());
        cv.put(COLUMN_DISH_DESCRIPTION, dishItem.getDescription());
        cv.put(COLUMN_DISH_PRICE, dishItem.getPrice());
        cv.put(COLUMN_DISH_UUID, dishItem.getDishId());
        cv.put(COLUMN_DISH_IMAGE_PATH, dishItem.getImagePath());
        Log.i("inserted", "inserted mate");
        return getReadableDatabase().insert(TABLE_DISHES, null, cv);

    }



    public static class DishItemCursor extends CursorWrapper {

        public DishItemCursor(Cursor c) {

            super(c);

        }


        DishItem createNewDishItem(int dishId, String dishName, String dishType, String description, int price, String imagePath) {


            DishItem dishItem = new DishItem();
            dishItem.setDishId(dishId);
            dishItem.setDishName(dishName);
            dishItem.setDishType(dishType);
            dishItem.setDescription(description);
            dishItem.setPrice(price);
            dishItem.setImagePath(imagePath);
            return dishItem;

        }


        public DishItem getDishItem() {

            int dishItemId = getInt(getColumnIndex((COLUMN_DISH_UUID)));
            String dishItemName = getString(getColumnIndex(COLUMN_DISH_NAME));
            String dishItemType = getString(getColumnIndex((COLUMN_DISH_TYPE)));
            String description = getString(getColumnIndex(COLUMN_DISH_DESCRIPTION));
            int price = getInt(getColumnIndex(COLUMN_DISH_PRICE));
            String imagePath = getString(getColumnIndex(COLUMN_DISH_IMAGE_PATH));
            DishItem dishItem = createNewDishItem(dishItemId, dishItemName, dishItemType, description, price, imagePath);
            Log.i("i", "returning getDishItem");
            return dishItem;


        }
    }



}
