package project.mad.com.discountshop.contract;

/**
 * ISaveDataView
 * provide methods for barcode, shop, product fragments
 * show input messages
 */
public interface ISaveDataView {

    void showValidationError();
    void inputSuccess();
    void inputError();
    void inputInvalid();
    void databaseError();
}
