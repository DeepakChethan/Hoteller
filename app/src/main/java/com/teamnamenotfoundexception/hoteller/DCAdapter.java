package com.teamnamenotfoundexception.hoteller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamnamenotfoundexception.hoteller.Activities.DescriptionActivity;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.Database.DishRepository;

import java.util.List;

/**
 * Created by deepakchethan on 3/12/18.
 */

public class DCAdapter extends RecyclerView.Adapter<DCAdapter.ViewHolder>{

    private Context context;
    private List<DishItem> dishItems;

    public DCAdapter(Context mcontext, List<DishItem> mdishItems) {
        context = mcontext;
        dishItems = mdishItems;

    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishentry,parent,false);

        return new ViewHolder(v);
    }

<<<<<<< HEAD


=======
    public void setData(List<DishItem> items){
        dishItems = items;
    }
    public void updateUI(){
        DCAdapter adapter = DCAdapter.this;
        DishRepository dishRepository = DishRepository.get(context);
        adapter.setData(dishRepository.getDishItemsList());
        adapter.notifyDataSetChanged();
    }
>>>>>>> 9331303330a19b2ca572bbf6132059ec48f9cc82
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


        Log.i("favorite list onbind", CartManager.get(context).getFavoriteIdList().size() + "");
        final DishItem dishItem = dishItems.get(position);

        holder.dishTitle.setText(dishItem.getDishName());
        holder.dishCat.setText(dishItem.getDishType());
        holder.dishCost.setText(dishItem.getPrice()+" Rs");
        Glide.with(context).load(dishItem.getImagePath()).into(holder.dishImage);
        boolean isFavorite = CartManager.get(context).getFavoriteIdList().contains(dishItem.getDishId()) ? true : false;
        if(isFavorite) {
            dishItem.setDishFav(1);
        } else {
            dishItem.setDishFav(0);
        }
        holder.cartBtn.setImageResource( dishItem.getIsCart() == 1  ? R.drawable.ic_shopping_cart_red_24dp:R.drawable.ic_shopping_cart_black_24dp);
        holder.heartBtn.setImageResource(isFavorite == false ? R.drawable.ic_favorite_black_24dp:R.drawable.ic_favorite_red_24dp);


        holder.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager cartManager = CartManager.get(context);
                if (dishItem.getIsCart() == 0){
                    dishItem.setIsCart(1);
                    cartManager.addDishToCart(dishItem);
                    holder.cartBtn.setImageResource(R.drawable.ic_shopping_cart_red_24dp);
                } else {
                    dishItem.setIsCart(0);
                    cartManager.removeDishFromCart(dishItem);
                    holder.cartBtn.setImageResource(R.drawable.ic_shopping_cart_black_24dp);
                }

            }

        });

        holder.heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager cartManager = CartManager.get(context);

                if(dishItem.isDishFav() == 0){
                    dishItem.setDishFav(1);
                    cartManager.addToFavorites(dishItem);
                    holder.heartBtn.setImageResource(R.drawable.ic_favorite_red_24dp);
                } else {
                    dishItem.setDishFav(0);
                    cartManager.removeFromFavorites(dishItem);
                    holder.heartBtn.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
                System.out.println(cartManager.getFavoriteIdList().size());

            }
        });

    }




    @Override
    public int getItemCount() {
        return dishItems.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView dishImage;
        public TextView dishCat, dishTitle,dishCost;
        public ImageButton cartBtn, heartBtn;
        public ViewHolder(View itemView) {
            super(itemView);

            dishImage = (ImageView)  itemView.findViewById(R.id.food_image);
            dishTitle = (TextView) itemView.findViewById(R.id.dish_title);
            dishCost = (TextView) itemView.findViewById(R.id.dish_cost);
            dishCat = (TextView) itemView.findViewById(R.id.dish_category);
            cartBtn = (ImageButton) itemView.findViewById(R.id.cartBtn);
            heartBtn = (ImageButton) itemView.findViewById(R.id.favBtn);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context,DescriptionActivity.class);
            intent.putExtra("OBJ",dishItems.get(getPosition()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
