package app.repositories;

import app.models.Bid;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("BIDS.JPA")
@Transactional
@Primary
public class BidsRepositoryJpa implements EntityRepository<Bid> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Bid> findAll() {
        TypedQuery<Bid> query = entityManager.createQuery("SELECT b FROM Bid b", Bid.class);
        return query.getResultList();
    }

    @Override
    public Bid findById(long id) {
        return entityManager.find(Bid.class, id);
    }

    @Override
    public Bid save(Bid item) {
        return entityManager.merge(item);
    }

    @Override
    public Bid deleteById(long id) {
        Bid toBeDeleted = this.findById(id);
        entityManager.remove(toBeDeleted);
        return toBeDeleted;
    }
}