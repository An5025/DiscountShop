package project.mad.com.discountshop.contract;

public interface IDiscountPresenter {

    /**
     * from product fragment, user can input these product information and save to database
     * @param name product name
     * @param brand product brand
     * @param capacity product capacity
     * @param discount product discount
     * @param date discount expiry date
     */
    void input(String name, String brand, String capacity, Integer discount, String date);

    /**
     * scan product barcode, if exist, product name, brand and capacity will be filled
     * @param barcode
     */
    void searchBarcode(String barcode);
}
