package com.teamnamenotfoundexception.hoteller.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.adapters.DCAdapter;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    RecyclerView myrecycle;
    RelativeLayout empty,nempty;
    Toolbar tools;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tools = (Toolbar) findViewById(R.id.cartTools);
        empty = (RelativeLayout) findViewById(R.id.empty);
        nempty = (RelativeLayout) findViewById(R.id.nempty);
        setSupportActionBar(tools);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CartManager cartManager = CartManager.get(getApplicationContext());

        ArrayList<DishItem> dishItems = cartManager.getCartItems();

        if (dishItems.isEmpty()){
            empty.setVisibility(View.VISIBLE);
            nempty.setVisibility(View.GONE);
        }else {
            nempty.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
            myrecycle = (RecyclerView) findViewById(R.id.cartRecycle);
            DCAdapter myadapter = new DCAdapter(getApplicationContext(), dishItems);
            LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
            myrecycle.setLayoutManager(llm);
            myrecycle.setItemAnimator(new DefaultItemAnimator());
            myrecycle.setAdapter(myadapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkout(View v){
        startActivity(new Intent(getApplicationContext(),BillActivity.class));
    }
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
