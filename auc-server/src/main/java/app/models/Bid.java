package app.models;

import jakarta.persistence.*;

@Entity
public class Bid {

    @Id
    @SequenceGenerator(name = "bids_id", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bids_id")
    private long id;

    private double value;

    @ManyToOne
    private Offer offer;

    public Bid(long id, double value) {
        this.id = id;
        this.value = value;
    }

    public Bid() {
    }

    /**
     * Associates this bid with an offer, if the offer is not already associated.
     * 
     * @param offer the offer to associate with
     * @return true if the offer was associated, false if the offer was already
     *         associated
     */
    public boolean associateOffer(Offer offer) {
        if (this.offer == null) {
            this.offer = offer;
            return true;
        }

        return false;
    }

    public static Bid createSampleBid(Offer offer) {
        // Random value between 4000 and 10000.
        int randomValue = (int) (Math.random() * (10000 - 4000 + 1) + 4000);

        return new Bid(0, randomValue);
    }

    public long getId() {
        return id;
    }

    public double getValue() {
        return value;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

}
