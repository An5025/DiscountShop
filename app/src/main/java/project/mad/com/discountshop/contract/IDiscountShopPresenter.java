package project.mad.com.discountshop.contract;

import java.util.ArrayList;

import project.mad.com.discountshop.data.Shop;

public interface IDiscountShopPresenter {

    void input(String name, Integer discount, String date);

    ArrayList<Shop> getFirebaseData();
}
