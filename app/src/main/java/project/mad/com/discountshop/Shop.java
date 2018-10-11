package project.mad.com.discountshop;

public class Shop {
    private String name;
    private int discount, countdown, votes;

    public Shop(){}

    public Shop(String name, int discount, int countdown, int votes) {
        this.name = name;
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
