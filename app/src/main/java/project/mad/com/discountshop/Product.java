package project.mad.com.discountshop;

public class Product extends Shop {
    private String name, brand, capacity;
    private int discount, countdown, votes;

    public Product() {
    }

    public Product(String name, String brand, String capacity, int discount, int countdown, int votes) {
        this.name = name;
        this.brand = brand;
        this.capacity = capacity;
        this.discount = discount;
        this.countdown = countdown;
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

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
