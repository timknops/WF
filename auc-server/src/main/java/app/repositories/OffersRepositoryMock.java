package app.repositories;

import app.models.Offer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("OFFERS.INMEMORY")
public class OffersRepositoryMock implements OffersRepository<Offer> {

    private List<Offer> offers;
    private final long STARTING_ID = 3000;
    private long currentId = STARTING_ID;

    public OffersRepositoryMock() {
        final int AMOUNT_OF_OFFERS = 7;

        this.offers = new ArrayList<>(AMOUNT_OF_OFFERS);

        for (int i = 0; i < AMOUNT_OF_OFFERS; i++) {
            currentId++;
            this.offers.add(Offer.createSampleOffer(currentId));
        }
    }

    @Override
    public List<Offer> findAll() {
        return offers;
    }

    @Override
    public Offer findById(long id) {
        return offers.stream().filter(offer -> offer.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Offer save(Offer item) {
        int index = offers.indexOf(item);
        if (index != -1) {
            offers.set(index, item);
        } else {
            offers.add(item);
        }
        return item;
    }

    @Override
    public Offer deleteById(long id) {
        Offer toBeDeleted = this.findById(id);
        if (toBeDeleted != null) {
            offers.remove(toBeDeleted);
        }
        return toBeDeleted;
    }
}
