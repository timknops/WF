package app.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;



@NamedQueries({
        @NamedQuery(
                name ="Offer_find_by_status",
                query = "SELECT o FROM Offer o WHERE o.status = ?1 "
        ),
        @NamedQuery(
                name = "Offer_find_by_title",
                query = "SELECT o FROM Offer o WHERE o.title LIKE CONCAT('%', ?1, '%') "
        ),
        @NamedQuery(
                name= "Offer_find_by_status_and_minBidValue",
                query = "SELECT o FROM Offer o WHERE o.status = ?1 AND o.valueHighestBid >= ?2"
        )
})
/**
 * Modal for an offer
 *
 * @author Julian Kruithof
 */
@Entity
public class Offer {
    // a list of random offer titles
    private static final String[] TITLES = { "Lamp",
            "Clock",
            "Cabinet",
            "Toolbox",
            "Laptop",
            "Bicycle",
            "Car",
            "Couch" };

    // a list of random descriptions
    private static final String[] DESCRIPTIONS = {
            "A nice modern lamp",
            "A antique analoge clock with winding mechanism",
            "A big closet for hanging and stacking clothes",
            "A toolbox, containing screwdrivers hamers and storage for nails and screws",
            "A Asus tuf gaming laptop with 1 year uses",
            "A learning bicycle for children",
            "A ford focus, from 2017",
            "A rustic couch with laying area",
    };

    @JsonView(ViewClasses.Summary.class)
    @Id
    @SequenceGenerator(name = "offers_id", initialValue = 3000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offers_id")
    private long id = 0;

    @JsonView(ViewClasses.Summary.class)
    private String title;

    @Enumerated(EnumType.STRING)
    @JsonView(ViewClasses.Summary.class)
    private Status status;

    private String description;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", shape = JsonFormat.Shape.STRING, timezone = "Europe/Amsterdam")
    private LocalDateTime sellDate;

    private int valueHighestBid;

    @OneToMany(mappedBy = "offer")
    @JsonManagedReference
    private List<Bid> bids = new ArrayList<>();

    public Offer(long id) {
        this.id = id;
    }

    public Offer(String title) {
        this.title = title;
    }

    /**
     * Needed for springboot to initialise its own instances of the offer class, by
     * using getters and setters
     */
    public Offer() {
    }

    /**
     * Associates this offer with the given bid, if the bid is not already
     * associated and only if the bid is higher than the current highest bid
     * 
     * @param bid the bid to associate with
     * @return true if the bid was associated, false if the bid was already
     */
    public boolean associateBid(Bid bid) {
        if (this.bids.contains(bid)) {
            return false;
        }

        if (bid.getValue() > this.valueHighestBid) {
            this.valueHighestBid = (int) bid.getValue();
        }

        this.bids.add(bid);
        bid.associateOffer(this);
        return true;
    }

    /**
     * Dissociates this offer with the given bid, if the bid is associated.
     * 
     * @param bid the bid to dissociate with
     * @return true if the bid was dissociated, false if the bid was not associated
     */
    public boolean dissociateBid(Bid bid) {
        if (this.bids.contains(bid)) {
            this.bids.remove(bid);
            return true;
        }

        return false;
    }

    /**
     * create a random sample offer
     *
     * @param id the id of the offer
     * @return a sample offer
     */
    public static Offer createSampleOffer(long id) {
        Offer offer = new Offer(id);
        int index = valueBetween(0, TITLES.length);
        offer.setTitle(TITLES[index]);
        offer.setDescription(DESCRIPTIONS[index]);
        offer.setStatus(Status.values()[valueBetween(0, Status.values().length)]);

        LocalDateTime today = LocalDateTime.now();
        LocalDateTime previousMonth = today.minusMonths(1);
        LocalDateTime nextMonth = today.plusMonths(1);

        if (offer.getStatus() == Status.EXPIRED || offer.getStatus() == Status.CLOSED
                || offer.getStatus() == Status.SOLD) {
            offer.setSellDate(randomDate(previousMonth, today));
        } else {
            offer.setSellDate(randomDate(today, nextMonth));
        }

        if (offer.getStatus() == Status.NEW) {
            offer.setValueHighestBid(0);
        } else {
            offer.setValueHighestBid(valueBetween(5, 3000));
        }

        return offer;
    }

    /**
     * get a random date between the start date and end Date
     *
     * @param start - the earliest date the function can return
     * @param end   - the latest date the function can return
     * @return random date between the start and criteria
     *         info found at
     * @link <a href="https://www.baeldung.com/java-convert-epoch-localdate">...</a>
     */
    private static LocalDateTime randomDate(LocalDateTime start, LocalDateTime end) {
        // get epoch seconds of start and end date
        long startEpochSeconds = start.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpochSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond();

        // random value of between the start and end epoch seconds
        long valueBetween = valueBetween(startEpochSeconds, endEpochSeconds);

        // create a LocalDateTime object from the epoch seconds
        Instant dateInstant = Instant.ofEpochSecond(valueBetween);
        return dateInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param min the mininum value to be returned
     * @param max the maximum value to be returned
     * @param <E> the type of which the random value is chosen, this could be Long,
     *            Integer or Double, could expand
     * @return a random value between min and max
     * @throws IllegalArgumentException info for number class
     * @link <a href=
     *       "https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html">...</a>
     */
    private static <E extends Number> E valueBetween(E min, E max) throws IllegalArgumentException {
        double value = Math.random() * (max.doubleValue() - min.doubleValue()) + min.doubleValue();

        // because of the generic the value which is currently a double should be
        // converted to the correct Wrapper class
        // i.e. Long, Double or Integer and not int, double or long
        // later it could check for Short and Byte etc. not needed for now
        if (min instanceof Double) {
            return (E) Double.valueOf(value);
        } else if (min instanceof Integer) {
            return (E) Integer.valueOf((int) value);
        } else if (min instanceof Long) {
            return (E) Long.valueOf((long) value);
        } else {
            throw new IllegalArgumentException(
                    "argument type is not numeric. Make sure the function uses Long, Double or Integer");
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDateTime sellDate) {
        this.sellDate = sellDate;
    }

    public int getValueHighestBid() {
        return valueHighestBid;
    }

    public void setValueHighestBid(int valueHighestBid) {
        this.valueHighestBid = valueHighestBid;
    }

    public List<Bid> getBids() {
        return this.bids;
    }

    public void setBids(List<Bid> newBids) {
        this.bids = newBids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Offer offer = (Offer) o;
        return id == offer.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // enum of different statuses an offer can have
    public enum Status {
        NEW,
        FOR_SALE,
        SOLD,
        PAID,
        DELIVERED,
        CLOSED,
        EXPIRED,
        WITHDRAWN
    }
}
