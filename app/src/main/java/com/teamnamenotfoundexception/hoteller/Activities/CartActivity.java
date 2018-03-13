package com.teamnamenotfoundexception.hoteller.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.teamnamenotfoundexception.hoteller.DCAdapter;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView myrecycle;
    ArrayList<DishItem> cartItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        cartItems= new ArrayList<>();
        myrecycle = (RecyclerView) findViewById(R.id.cartRecycle);
        DCAdapter myadapter = new DCAdapter(getApplicationContext(),cartItems);
        myrecycle = (RecyclerView) findViewById(R.id.recycle);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        myrecycle.setLayoutManager(llm);
        myrecycle.setItemAnimator(new DefaultItemAnimator());
        myrecycle.setAdapter(myadapter);
    }
}
