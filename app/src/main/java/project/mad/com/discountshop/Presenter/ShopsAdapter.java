package project.mad.com.discountshop.Presenter;

import android.content.Context;

import java.util.List;

import project.mad.com.discountshop.Shop;
import project.mad.com.discountshop.View.IShopsRecyclerView;

public class ShopsAdapter{
    List<Shop> mShopsArray;
    private Context mContext;

    public ShopsAdapter(List<Shop> shopsArray, Context context) {
        mShopsArray = shopsArray;
        mContext = context;
    }

    public void onBindShopData(int position, IShopsRecyclerView shopData){
        Shop shop = mShopsArray.get(position);
        shopData.setShopName(shop.getName());
        shopData.setShopCountdown(shop.getCountdown());
        shopData.setShopDiscount(shop.getDiscount());
        shopData.setShopVotes(shop.getVotes());
    }

    public int getShopCount(){
        return mShopsArray.size();
    }
}
