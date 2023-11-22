package app;

import app.models.Bid;
import app.models.Offer;
import app.repositories.OffersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Random;

@SpringBootApplication
public class AucServerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(AucServerApplication.class, args);
    }

    @Autowired
    OffersRepository<Offer> offersRepo;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        this.createSampleOffers();
        this.createSampleBids();
    }

    private void createSampleOffers() {
        List<Offer> offerList = offersRepo.findAll();
        final int AMOUNT_OF_OFFERS = 7;

        if (!offerList.isEmpty())
            return;

        for (int i = 0; i < AMOUNT_OF_OFFERS; i++) {
            Offer offer = Offer.createSampleOffer(0);
            offersRepo.save(offer);
        }
    }

    @Autowired
    OffersRepository<Bid> bidsRepo;

    private static Random randomizer = new Random();

    private void createSampleBids() {
        List<Bid> bidsList = this.bidsRepo.findAll();

        if (!bidsList.isEmpty()) {
            return;
        }

        List<Offer> offersList = this.offersRepo.findAll();
        for (int i = 0; i < 15; i++) {
            Bid savedBid = this.bidsRepo.save(Bid.createSampleBid());
            Offer randomOffer = offersList.get(randomizer.nextInt(offersList.size()));
            savedBid.associateOffer(randomOffer);
        }

    }
}
