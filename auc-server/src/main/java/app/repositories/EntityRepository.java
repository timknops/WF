package app.repositories;

import java.util.List;

public interface EntityRepository<E> {
    /**
     * ind all available items

     * 
     * @return a list of items
     */
    List<E> findAll();

    /**
     * Find a specific item in the list
     * 
     * @param id id of the item to be searched
     * @return the item which was found, or null if the item doesn't exist.
     */
    E findById(long id);

    /**
     * Update the properties of the item, or insert a new item if the item doesn't
     * exist
     * 
     * @param item the item to be updated or inserted
     * @return the new item
     */
    E save(E item);

    /**
     * Delete an item out of the list
     * 
     * @param id the id of the item to be deleted
     * @return the item which was deleted, or null if the item didn't exists
     */
    E deleteById(long id);

    /**
     * Find items for a certain query
     * @param jpqlQuery the name of the namedQuery to be executed
     * @param params The parameters to be correctly added to the query
     * 
     *      */
    List<E> findByQuery(String jpqlQuery, Object ...params);

}
