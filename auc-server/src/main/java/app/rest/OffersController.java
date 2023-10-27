package app.rest;

import app.exceptions.PreConditionFailedException;
import app.exceptions.ResourceNotFoundException;
import app.models.Offer;
import app.models.ViewClasses;
import app.repositories.OffersRepository;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @JsonView(ViewClasses.Summary.class)
    @GetMapping(path = "/summary", produces = "application/json")
    public List<Offer> getSummaryOfAllOffers() {
        return this.offersRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public Offer getOffer(@PathVariable long id) throws ResourceNotFoundException {
        Offer offer = offersRepo.findById(id);

        if (offer == null) {
            throw new ResourceNotFoundException("Offer with id " + id + " not found");
        }

        return offer;
    }

    @PostMapping(path = "", produces = "application/json")
    public ResponseEntity<Offer> addOffer(@RequestBody Offer offer) {
        Offer newOffer = offersRepo.save(offer);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newOffer.getId()).toUri();
        return ResponseEntity.created(location).body(newOffer);
    }

    @PutMapping(path="{id}", produces = {"application/json"})
    public ResponseEntity<Offer> updateOffer(@PathVariable long id, @RequestBody Offer offer) throws PreConditionFailedException {
        if (id != offer.getId()) {
            throw new PreConditionFailedException("Id in path and body do not match");
        }

        Offer updatedOffer = offersRepo.save(offer);

        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping(path = "{id}", produces = {"application/json"})
    public ResponseEntity<Offer> deleteOffer(@PathVariable long id) throws ResourceNotFoundException {
        Offer deletedOffer = offersRepo.deleteById(id);

        if (deletedOffer == null) {
            throw new ResourceNotFoundException("Cannot delete offer with id " + id + ". Offer not found.");
        }

        return ResponseEntity.ok(deletedOffer);
    }
}
