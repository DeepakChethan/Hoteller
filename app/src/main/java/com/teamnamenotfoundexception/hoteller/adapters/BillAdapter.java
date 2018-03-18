package com.teamnamenotfoundexception.hoteller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;

/**
 * Created by deepakchethan on 3/13/18.
 */

public class BillAdapter extends ArrayAdapter<DishItem> {
    private Activity context;
    ArrayList<DishItem> billDishItems;

    public BillAdapter(@NonNull Activity context,ArrayList<DishItem> billDishItems, int resource) {
        super(context, resource, billDishItems);
        this.context = context;
        this.billDishItems = billDishItems;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        ViewHolder viewHolder;
        if (v == null){
            v = context.getLayoutInflater().inflate(R.layout.billdishe,parent,false);
            viewHolder = new ViewHolder(v);
            v.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) v.getTag();
        }
        DishItem dishItem = billDishItems.get(position);
        if (dishItem== null) return v;

        viewHolder.title.setText(dishItem.getDishName());
        viewHolder.priceQuant.setText(dishItem.getQuantity()+" X "+dishItem.getPrice());
        viewHolder.total.setText(dishItem.getTotalPrice()+" Rs");
        return v;
    }
    class ViewHolder{
        TextView title,priceQuant,total;
        ViewHolder(View v){

            title = (TextView)v.findViewById(R.id.itemTitle);
            priceQuant = (TextView) v.findViewById(R.id.itemPriceQuant);
            total = (TextView) v.findViewById(R.id.itemCost);
        }
    }
    public interface inter{
        public int dialoog(DishItem dishItem);
    }
}
