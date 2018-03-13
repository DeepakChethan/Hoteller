package com.teamnamenotfoundexception.hoteller.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.R;

public class DescriptionActivity extends AppCompatActivity {

    private ImageView dishImg;
    private TextView description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DishItem dishItem = (DishItem) getIntent().getSerializableExtra("OBJ");
        if (dishItem == null) return;
        getSupportActionBar().setTitle(dishItem.getDishName());
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        description = (TextView) findViewById(R.id.dishDescription);
        dishImg = (ImageView) findViewById(R.id.dishImageCo);
        Glide.with(getApplicationContext()).load(dishItem.getImagePath()).into(dishImg);
        description.setText(dishItem.getDescription());

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(dishItem.isDishFav() == 0?R.drawable.ic_favorite_black_24dp:R.drawable.ic_favorite_red_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartManager cartManager = CartManager.get(getApplicationContext());
                if(dishItem.isDishFav() == 0){
                            dishItem.setDishFav(1);
                            cartManager.addDishToFav(dishItem);
                            fab.setImageResource(R.drawable.ic_favorite_red_24dp);
                        }else{
                            dishItem.setDishFav(0);
                            cartManager.removeDishFromFav(dishItem);
                            fab.setImageResource(R.drawable.ic_favorite_black_24dp);
                        }


            }
        });
    }
}
