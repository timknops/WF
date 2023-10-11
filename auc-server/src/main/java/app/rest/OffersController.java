package app.rest;

import app.models.Offer;
import app.repositories.OffersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/offers")
public class OffersController {

    @Autowired
    OffersRepository<Offer> offersRepo;

    @GetMapping(path = "/test", produces = "application/json")
    public List<Offer> getTestOffers() {
        return List.of(Offer.createSampleOffer(3000), new Offer(3001), new Offer("Titel"));
    }

    @GetMapping(path = "", produces = "application/json")
    public List<Offer> getAllOffers() {
        return this.offersRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public Offer getOffer(@PathVariable long id) {
        Offer offer = offersRepo.findById(id);

        return offer;
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
        Offer newOffer = offersRepo.save(offer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOffer.getId()).toUri();
        return ResponseEntity.created(location).body(newOffer);
    }

    @PutMapping(path="{id}", produces = {"application/json"})
    public ResponseEntity<Offer> updateOffer(@PathVariable long id, @RequestBody Offer offer) {
        Offer updatedOffer = offersRepo.save(offer);
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping(path = "{id}", produces = {"application/json"})
    public ResponseEntity<Offer> deleteOffer(@PathVariable long id) {
        Offer deletedOffer = offersRepo.deleteById(id);
        return ResponseEntity.ok(deletedOffer);
    }
}
