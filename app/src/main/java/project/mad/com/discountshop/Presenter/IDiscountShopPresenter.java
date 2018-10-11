package project.mad.com.discountshop.Presenter;

import java.util.ArrayList;

import project.mad.com.discountshop.Shop;

public interface IDiscountShopPresenter {

    void input(String name, Integer discount, Integer countdown);

    ArrayList<Shop> getFirebaseData();
}
