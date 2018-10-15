package project.mad.com.discountshop.contract;

/**
 * ISaveDataView
 * provide methods for barcode, shop, product fragments
 * show input messages
 */
public interface IBarcodeView {

    void showValidationError();
    void inputSuccess();
    void inputError();
    void inputInvalid();
    void exist();
    void databaseError();
}
