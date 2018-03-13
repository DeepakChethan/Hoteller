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

import com.teamnamenotfoundexception.hoteller.DCAdapter;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    Toolbar tools;

    RecyclerView myrecycle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        tools = (Toolbar) findViewById(R.id.favTools);
        setSupportActionBar(tools);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CartManager cartManager = CartManager.get(getApplicationContext());
        myrecycle = (RecyclerView) findViewById(R.id.favRecycle);
        DCAdapter myadapter = new DCAdapter(getApplicationContext(),cartManager.getFavItems());
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        myrecycle.setLayoutManager(llm);
        myrecycle.setItemAnimator(new DefaultItemAnimator());
        myrecycle.setAdapter(myadapter);
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    public void buyAll(View v){
        startActivity(new Intent(getApplicationContext(),BillActivity.class));
    }
}
