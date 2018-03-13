package com.teamnamenotfoundexception.hoteller.Database;

/**
 * Created by sagar on 3/13/18.
 */

public class DishItem {

    public int mDishId;
    public String mDishName;
    public String mDishType;
    public int mPrice;
    public int mQuantity;
    public String mDescription;
    public String mImagePath;
    public int mTotalPrice;
    public int isFav;
    public int isCart;


    public DishItem() {

    }



    public DishItem(int dishId, String dishName, String dishType, int price, int quantity, String description, String imagePath) {
        this.mDishId = dishId;
        this.mDishName = dishName;
        this.mDishType = dishType;
        this.mPrice = price;
        this.mQuantity = quantity;
        this.mDescription = description;
        this.mImagePath = imagePath ;
        this.isFav = 0;
        this.isCart = 0;

    }

    public int getIsCart(){return isCart;}
    public void setIsCart(int a) {isCart = a;};


    public int getDishId() {
        return mDishId;
    }

    public void setDishId(int dishId) {
        mDishId = dishId;
    }

    public void setQuantity (int quantity) {
        this.mQuantity = quantity ;
        this.mPrice = quantity * mPrice;
    }
    public int isDishFav() { return isFav;};

    public void setDishFav(int a){ isFav = a;}
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

    public void setPrice(int price) {
        this.mPrice = price;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getCostForQuantity(int quantity) {
        return (mPrice * quantity);
    }


}
