package app.models;

public class Offer {

    enum Status {
        NEW, FOR_SALE, SOLD, PAID, DELIVERED, CLOSED, EXPIRED, WITHDRAWN
    }

    private int id;
    private String title;
    private String status;
    private String description;
    private String sellDate;
    private int valueHighestBid;

    public Offer(int id, String title, String status, String description, String sellDate, int valueHighestBid) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.description = description;
        this.sellDate = sellDate;
        this.valueHighestBid = valueHighestBid;
    }



}
