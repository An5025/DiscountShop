package project.mad.com.discountshop.data;

/**
 * Product model
 * get and set product data
 */
public class Product extends Shop {
    private String name, brand, capacity, date;
    private int discount, votes;

    public Product() {
    }

    /**
     * product constructor
     * initialize variables
     * @param name
     * @param brand
     * @param capacity
     * @param discount
     * @param date
     * @param votes
     */
    public Product(String name, String brand, String capacity, int discount, String date, int votes) {
        this.name = name;
        this.brand = brand;
        this.capacity = capacity;
        this.discount = discount;
        this.date = date;
        this.votes = votes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public String getDate() {
        return date;
    }

    @Override
    public void setDate(String date) {
        this.date = date;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
