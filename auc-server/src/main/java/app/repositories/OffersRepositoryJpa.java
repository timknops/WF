package app.repositories;

import app.models.Offer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("AUTHORS.JPA")
@Transactional
@Primary
public class OffersRepositoryJpa implements OffersRepository<Offer>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Offer> findAll() {
        TypedQuery<Offer> query = entityManager.createQuery("SELECT o FROM Offer o", Offer.class);
        return query.getResultList();
    }

    @Override
    public Offer findById(long id) {
        return entityManager.find(Offer.class, id);
    }

    @Override
    public Offer save(Offer item) {
        return entityManager.merge(item);
    }

    @Override
    public Offer deleteById(long id) {
        Offer toBeDeleted = this.findById(id);
        entityManager.remove(toBeDeleted);
        return toBeDeleted;
    }
}
