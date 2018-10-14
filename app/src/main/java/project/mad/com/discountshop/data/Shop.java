package project.mad.com.discountshop.data;
/**
 * Shop model
 * get and set shop data
 */
public class Shop {
    private String name, date;
    private int discount;

    public Shop() {
    }

    /**
     * Shop constructor
     * initialize variables
     * @param name
     * @param discount
     * @param date
     */
    public Shop(String name, int discount, String date) {
        this.name = name;
        this.discount = discount;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}


