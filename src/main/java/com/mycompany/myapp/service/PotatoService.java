package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Potato;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Potato}.
 */
public interface PotatoService {

    /**
     * Save a potato.
     *
     * @param potato the entity to save.
     * @return the persisted entity.
     */
    Potato save(Potato potato);

    /**
     * Get all the potatoes.
     *
     * @return the list of entities.
     */
    List<Potato> findAll();


    /**
     * Get the "id" potato.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Potato> findOne(Long id);

    /**
     * Delete the "id" potato.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
