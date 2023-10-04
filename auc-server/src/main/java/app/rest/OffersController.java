package app.rest;

import app.models.Offer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/offers")
public class OffersController {

    @GetMapping(path = "/test", produces = "application/json")
    public List<Offer> getTestOffers() {
        return List.of(Offer.createSampleOffer(3000), new Offer(3001), new Offer("Titel"));
    }

}
