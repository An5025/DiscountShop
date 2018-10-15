package project.mad.com.discountshop.contract;

/**
 * IProductView
 * provide methods for product fragments
 * show input messages
 */
public interface ISaveDataView {

    void showValidationError();
    void inputSuccess();
    void inputError();
    void inputInvalid();
    void databaseError();
    void notExist();

    /**
     * barcode model, saved in barcode fragment
     * @param name product name
     * @param brand product brand
     * @param capacity product capacity
     */
    void showModelData(String name, String brand, String capacity);
}
