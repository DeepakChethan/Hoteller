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

public class DatabaseHelper {


    private static final String DB_NAME = "restaurante.sqlite";
    public static final int VERSION = 1;

    private static final String TABLE_DISHES = "dishes";
    private static final String COLUMN_DISH_NAME = "dish_name";
    private static final String COLUMN_DISH_TYPE = "type";
    private static final String COLUMN_DISH_DESCRIPTION = "description";
    private static final String COLUMN_DISH_PRICE = "price";
    private static final String COLUMN_DISH_IMAGE_PATH = "image_path";




}
