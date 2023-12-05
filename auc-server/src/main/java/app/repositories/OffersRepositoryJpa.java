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
public class OffersRepositoryJpa implements EntityRepository<Offer> {

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

    /**
     * Find all offers given a certain query.
     * @param jpqlQuery the name of the namedQuery to be executed
     * @param params The parameters to be correctly added to the query
     * @return a list of offers
     */
    @Override
    public List<Offer> findByQuery(String jpqlQuery, Object... params) {
        TypedQuery<Offer> query = entityManager.createNamedQuery(jpqlQuery, Offer.class);

        for (int i = 0; i < params.length; i++) {
            //set all params to the correct ordinal position.
            //Ordinal parameter position start counting from 1
            query.setParameter(i + 1, params[0]);
        }
        return query.getResultList();
    }
}
