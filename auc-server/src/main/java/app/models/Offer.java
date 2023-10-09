package app.models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;


/**
 * Modal for an offer
 *
 * @author Julian Kruithof
 */
public class Offer {
    // a list of random offer titles
    private static final String[] TITLES = {"Lamp",
            "Clock",
            "Cabinet",
            "Toolbox",
            "Laptop",
            "Bicycle",
            "Car",
            "Couch"};

    //a list of random descriptions
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
    private long id = 0;
    private String title;
    private Status status;
    private String description;
    private LocalDateTime sellDate;
    private int valueHighestBid;

    public Offer(long id) {
        this.id = id;
    }

    public Offer(String title) {
        this.title = title;
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

        if (offer.getStatus() == Status.EXPIRED || offer.getStatus() == Status.CLOSED || offer.getStatus() == Status.SOLD) {
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
     * info found at
     * @link <a href="https://www.baeldung.com/java-convert-epoch-localdate">...</a>
     */
    private static LocalDateTime randomDate(LocalDateTime start, LocalDateTime end) {
        //get epoch seconds of start and end date
        long startEpochSeconds = start.atZone(ZoneId.systemDefault()).toEpochSecond();
        long endEpochSeconds = end.atZone(ZoneId.systemDefault()).toEpochSecond();

        //random value of between the start and end epoch seconds
        long valueBetween = valueBetween(startEpochSeconds, endEpochSeconds);

        //create a LocalDateTime object from the epoch seconds
        Instant dateInstant = Instant.ofEpochSecond(valueBetween);
        return dateInstant.atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * @param min the mininum value to be returned
     * @param max the maximum value to be returned
     * @param <E> the type of which the random value is chosen, this could be Long, Integer or Double, could expand
     * @return a random value between min and max
     * @throws IllegalArgumentException info for number class
     * @link <a href="https://docs.oracle.com/javase/8/docs/api/java/lang/Number.html">...</a>
     */
    private static <E extends Number> E valueBetween(E min, E max) throws IllegalArgumentException {
        double value = Math.random() * (max.doubleValue() - min.doubleValue()) + min.doubleValue();

        // because of the generic the value which is currently a double should be converted to the correct Wrapper class
        //i.e. Long, Double or Integer and not int, double or long
        // later it could check for Short and Byte etc. not needed for now
        if (min instanceof Double) {
            return (E) Double.valueOf(value);
        } else if (min instanceof Integer) {
            return (E) Integer.valueOf((int) value);
        } else if (min instanceof Long) {
            return (E) Long.valueOf((long) value);
        } else {
            throw new IllegalArgumentException("argument type is not numeric. Make sure the function uses Long, Double or Integer");
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id == offer.id &&
                valueHighestBid == offer.valueHighestBid &&
                title.equals(offer.title) &&
                status == offer.status &&
                description.equals(offer.description) &&
                sellDate.isEqual(offer.sellDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, status, description, sellDate, valueHighestBid);
    }

    //enum of different statuses an offer can have
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
