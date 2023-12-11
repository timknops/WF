package app.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Bid {

    @Id
    @SequenceGenerator(name = "bids_id", initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bids_id")
    @JsonView(ViewClasses.FindAll.class)
    private long id;

    @JsonView(ViewClasses.FindAll.class)
    private double value;

    @ManyToOne
    @JsonIncludeProperties(value = {"id", "title", "status"})
    private Offer offer;

    @ManyToOne
    @JsonIncludeProperties(value = {"id","name"})
    @JsonView(ViewClasses.FindAll.class)
    private Account madeBy;

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
            offer.associateBid(this);
            return true;
        }

        return false;
    }

    public boolean associateAccount(Account account) {
        if (this.madeBy == null) {
            this.madeBy = account;

            return true;
        }
        return false;
    }

    public static Bid createSampleBid() {
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

    public Account getMadeBy() {
        return madeBy;
    }

    public void setMadeBy(Account madeBy) {
        this.madeBy = madeBy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid bid)) return false;
        return getId() == bid.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
