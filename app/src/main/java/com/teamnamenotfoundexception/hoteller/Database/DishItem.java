package com.teamnamenotfoundexception.hoteller.Database;

/**
 * Created by sagar on 3/13/18.
 */

public class DishItem {

    public String mDishName;
    public String mDishType;
    public int mPrice;
    public int mQuantity;
    public String mDescription;
    public String mImagePath;
    public int mTotalPrice;


    public DishItem() {

    }

    public DishItem(String dishName, String dishType, int cost, int quantity, String description, String imagePath) {
        this.mDishName = dishName;
        this.mDishType = dishType;
        this.mPrice = cost;
        this.mQuantity = quantity;
        this.mDescription = description;
        this.mImagePath = imagePath ;

    }

    public void setQuantity (int quantity) {
        this.mQuantity = quantity ;
        this.mPrice = quantity * mPrice;
    }

    public int getQuantity() {
        return mQuantity ;
    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String imagePath) {
        this.mImagePath = imagePath;
    }


    public int getTotalPrice() {
        return mTotalPrice ;
    }


    public String getDishName() {
        return mDishName;
    }

    public String getDishType() {
        return mDishType;
    }

    public int getPrice() {
        return mPrice;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDishName(String mDishName) {
        this.mDishName = mDishName;
    }

    public void setDishType(String mDishType) {
        this.mDishType = mDishType;
    }

    public void setCost(int price) {
        this.mPrice = price;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getCostForQuantity(int quantity) {
        return (mPrice * quantity);
    }


}
