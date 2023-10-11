package app.repositories;

import app.models.Offer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("OFFERS.INMEMORY")
public class OffersRepositoryMock implements OffersRepository<Offer> {

    private List<Offer> offers;

    public OffersRepositoryMock() {
        final int STARTING_ID = 3000, AMOUNT_OF_OFFERS = 7;

        this.offers = new ArrayList<>(AMOUNT_OF_OFFERS);

        for (int i = 0; i < AMOUNT_OF_OFFERS; i++) {
            this.offers.add(Offer.createSampleOffer(STARTING_ID + i));
        }
    }

    @Override
    public List<Offer> findAll() {
        return offers;
    }
}
