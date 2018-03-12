package com.teamnamenotfoundexception.hoteller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;

import java.util.List;

/**
 * Created by deepakchethan on 3/12/18.
 */

public class DCAdapter extends RecyclerView.Adapter<DCAdapter.ViewHolder> {

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

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DishItem dishItem = dishItems.get(position);
        holder.dishTitle.setText(dishItem.getDishName());
        holder.dishCat.setText(dishItem.getDishType());
        holder.dishCost.setText(dishItem.getPrice()+" ");
        Glide.with(context).load(dishItem.getImagePath()).into(holder.dishImage);
    }

    @Override
    public int getItemCount() {
        return dishItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView dishImage;
        public TextView dishCat, dishTitle,dishCost;
        public ViewHolder(View itemView) {
            super(itemView);
            dishImage = (ImageView)  itemView.findViewById(R.id.food_image);
            dishTitle = (TextView) itemView.findViewById(R.id.dish_title);
            dishCost = (TextView) itemView.findViewById(R.id.dish_cost);
            dishCat = (TextView) itemView.findViewById(R.id.dish_category);
        }
    }
}
