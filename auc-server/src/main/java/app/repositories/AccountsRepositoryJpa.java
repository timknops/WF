package app.repositories;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import app.models.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
}
