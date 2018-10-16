package project.mad.com.discountshop.contract;

import java.util.ArrayList;

import project.mad.com.discountshop.data.Shop;

public interface IDiscountShopPresenter {

    /**
     * from shop fragment, user can input these shop information and save to database
     * @param name shop name
     * @param discount shop discount
     * @param date discount expiry date
     */
    void input(String name, Integer discount, String date);

    /**
     * used to save shop list to firebase
     * @return shop
     */
    ArrayList<Shop> getFirebaseData();

    /**
     * check if expiry
     * @param date expiry date
     */
    void checkDate(String date);

}
