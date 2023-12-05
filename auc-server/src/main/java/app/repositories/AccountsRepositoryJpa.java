package app.repositories;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import app.models.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Primary
@Transactional
@Repository("USERS.JPA")
public class AccountsRepositoryJpa implements EntityRepository<Account> {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Account> findAll() {
        return entityManager.createQuery("SELECT a FROM Account a", Account.class).getResultList();
    }

    @Override
    public Account findById(long id) {
        return entityManager.find(Account.class, id);
    }

    @Override
    public Account save(Account item) {
        return entityManager.merge(item);
    }

    @Override
    public Account deleteById(long id) {
        Account toBeDeleted = this.findById(id);
        entityManager.remove(toBeDeleted);
        return toBeDeleted;
    }

    public List<Account> findByQuery(String jpqlName, Object... params) {
        TypedQuery<Account> query = this.entityManager.createNamedQuery(jpqlName, Account.class);

        // Set the parameters.
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]);
        }

        return query.getResultList();
    }

    public List<Account> findByEmail(String email) {
        return entityManager.createQuery("SELECT a FROM Account a WHERE a.email = :email", Account.class)
                .setParameter("email", email).getResultList();
    }

}
