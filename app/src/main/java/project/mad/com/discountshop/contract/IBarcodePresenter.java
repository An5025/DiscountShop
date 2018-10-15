package project.mad.com.discountshop.contract;

public interface IBarcodePresenter {

    /**
     * if user scan this barcode, the user can input barcode related information to database
     * @param barcode product barcode
     * @param name product name
     * @param brand product brand
     * @param capacity product capacity
     */
    void input(String barcode, String name, String brand, String capacity);

    /**
     * search barcode from database, see does it exist
     * @param barcode barcode value
     */
    void searchBarcode(String barcode);
}
