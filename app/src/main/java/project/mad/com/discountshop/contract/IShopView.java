package project.mad.com.discountshop.contract;

/**
 * IShopView
 * provide methods for shop fragments
 * show input messages
 */
public interface IShopView {

    void showValidationError();
    void inputSuccess();
    void inputError();
    void inputInvalid();
    void databaseError();
}
