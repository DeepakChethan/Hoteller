package com.teamnamenotfoundexception.hoteller.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.github.javiersantos.materialstyleddialogs.enums.Style;
import com.github.zagum.switchicon.SwitchIconView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.shawnlin.numberpicker.NumberPicker;
import com.teamnamenotfoundexception.hoteller.Activities.CartActivity;
import com.teamnamenotfoundexception.hoteller.Activities.FavoriteActivity;
import com.teamnamenotfoundexception.hoteller.Activities.MainActivity;
import com.teamnamenotfoundexception.hoteller.Database.CartManager;
import com.teamnamenotfoundexception.hoteller.Database.DishItem;
import com.teamnamenotfoundexception.hoteller.R;

import java.util.ArrayList;
import java.util.List;

public class DCAdapter extends RecyclerView.Adapter<DCAdapter.ViewHolder> {

    private Context context;
    private List<DishItem> dishItems;
    public static int chosenQuantity=1;
    private Activity activity;

    public DCAdapter(Context mcontext, List<DishItem> mdishItems) {
        context = mcontext;
        dishItems = mdishItems;
        activity = (Activity) mcontext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dishentrycart, parent, false);
        return new ViewHolder(v);
    }

    public void setFilter(ArrayList<DishItem> newList){
        dishItems = new ArrayList<>();
        dishItems.addAll(newList);
        notifyDataSetChanged();
    }
    public void setData(List<DishItem> items){
        dishItems = new ArrayList<>();
       // System.out.println("size is " + items.size());
        dishItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {


       // Log.i("favorite list onbind", CartManager.get(context).getFavoriteIdList().size() + "");
        final DishItem dishItem = dishItems.get(position);
        holder.setIsRecyclable(false);
       Log.i("price", "price of dishitem" + dishItem.getDishName() + " " + dishItem.getPrice());
        holder.dishTitle.setText(dishItem.getDishName());
        holder.dishCat.setText(dishItem.getDishType());
        holder.dishCost.setText(dishItem.getPrice()+"");
        Glide.with(context).load(dishItem.getImagePath()).into(holder.dishImage);
        if (context instanceof CartActivity){
            holder.x.setVisibility(View.VISIBLE);
            holder.dishCount.setVisibility(View.VISIBLE);
            holder.dishCount.setText(dishItem.getQuantity()+"");
        }else {
            holder.x.setVisibility(View.GONE);
            holder.dishCount.setVisibility(View.GONE);
        }

        boolean isFavorite = CartManager.get(context).getFavoriteIdList().contains(dishItem.getDishId()) ? true : false;

        if(isFavorite) {
            dishItem.setDishFav(1);
        } else {
            dishItem.setDishFav(0);
        }

        if (dishItem.getIsFav() == 1) holder.heartBtn.setIconEnabled(true);
        if (dishItem.getIsCart()== 1) holder.cartBtn.setIconEnabled(true);


        holder.heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager cartManager = CartManager.get(context);

                if(dishItem.isDishFav() == 0) {
                    dishItem.setDishFav(1);
                    cartManager.addToFavorites(dishItem);
                    if (activity instanceof FavoriteActivity) {
                        setData(cartManager.getFavItems());
                    }
                    holder.heartBtn.setIconEnabled(true,true);
                    StyleableToast.makeText(context,dishItem.getDishName()+"is added to favorites!",R.style.cart_add).show();
                } else {
                    dishItem.setDishFav(0);
                    cartManager.removeFromFavorites(dishItem);
                   if (activity instanceof FavoriteActivity) {
                       setData(cartManager.getFavItems());
                   }
                    holder.heartBtn.setIconEnabled(false,true);
                    StyleableToast.makeText(context,dishItem.getDishName()+" is removed from favorites!",R.style.cart_rm).show();
                }
                System.out.println("size of favorite list " + cartManager.getFavoriteIdList().size());
            }
        });

        holder.cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartManager cartManager = CartManager.get(context);
                if(dishItem.getIsCart() == 0){
                    dishItem.setIsCart(1);
                    dishItem.setQuantity(1);
                    Log.i("cart clicked", "cart clicked on item" + dishItem.getPrice());
                    cartManager.addDishToCart(dishItem);
                    holder.cartBtn.setIconEnabled(true,true);
                    if (activity instanceof CartActivity) {
                        setData(cartManager.getCartItems());
                    }
                    StyleableToast.makeText(context,dishItem.getDishName()+" is added to cart!",R.style.love_add).show();
                } else {
                    cartManager.removeDishFromCart(dishItem);
                    dishItem.setIsCart(0);
                    dishItem.setQuantity(0);
                    holder.cartBtn.setIconEnabled(false,true);
                    if (activity instanceof CartActivity) {
                        setData(cartManager.getCartItems());
                    }
                    StyleableToast.makeText(context,dishItem.getDishName()+ "is removed from cart!",R.style.love_rm).show();
                }
                System.out.println(cartManager.getFavoriteIdList().size());

            }
        });

    }

    @Override
    public int getItemCount() {

        return dishItems.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        public ImageView dishImage;
        public TextView dishCat, dishTitle,dishCost,dishCount,x;
        public SwitchIconView cartBtn, heartBtn;
        public ViewHolder(final View itemView) {
            super(itemView);
            x = (TextView) itemView.findViewById(R.id.x);
            dishCount = (TextView) itemView.findViewById(R.id.dishCount);
            dishImage = (ImageView)  itemView.findViewById(R.id.food_image);
            dishTitle = (TextView) itemView.findViewById(R.id.dish_title);
            dishCost = (TextView) itemView.findViewById(R.id.dish_cost);
            dishCat = (TextView) itemView.findViewById(R.id.dish_category);
            heartBtn = (SwitchIconView) itemView.findViewById(R.id.favBtn);
            cartBtn = (SwitchIconView) itemView.findViewById(R.id.cartBtn);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(final View v) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.food_description_dialog,null);
            TextView des = (TextView)  view.findViewById(R.id.dishDescription);
            NumberPicker numberPicker = view.findViewById(R.id.dishCount);
            final DishItem dishItem = dishItems.get(getLayoutPosition());
            final CartManager cartManager = CartManager.get(context);


            new MaterialStyledDialog.Builder(context)
                    .setTitle(dishItem.getDishName())
                    .setStyle(Style.HEADER_WITH_TITLE)
                    .setPositiveText("Order")
                    .setCustomView(view,5,5,5,0)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            if(dishItem.getIsCart() == 1) {
                                cartManager.removeDishFromCart(dishItem);
                                dishItem.setIsCart(0);
                            }
                            dishItem.setQuantity(DCAdapter.chosenQuantity);
                            DCAdapter.chosenQuantity = 1;
                            if(dishItem.getIsCart() == 0) {
                                cartManager.addDishToCart(dishItem);
                                dishItem.setIsCart(1);
                            }
                            cartBtn.setIconEnabled(true);

                            dialog.dismiss();

                        }
                    })
                    .setNegativeText("Cancel")
                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            DCAdapter.chosenQuantity = 1;
                            dialog.dismiss();
                        }
                    })
                    .show();

            des.setText(dishItem.getDescription());
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    DCAdapter.chosenQuantity = newVal;
                    Log.i("adding to quantity ", " the current quantity" + dishItem.getQuantity() + " " + dishItem.getTotalPrice());
                }
            });

            //Toast.makeText(context,"This is working",Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onLongClick(View v) {
            //TODO put some dope dialogue here
            Toast.makeText(context,"don't long press me!",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
