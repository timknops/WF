package app.repositories;

import java.util.List;


public interface OffersRepository<E> {

    List<E> findAll();
}
