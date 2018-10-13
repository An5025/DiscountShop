package project.mad.com.discountshop.presenter;

import java.util.ArrayList;

import project.mad.com.discountshop.Shop;

public interface IDiscountShopPresenter {

    void input(String name, Integer discount, String date);

    ArrayList<Shop> getFirebaseData();
}
