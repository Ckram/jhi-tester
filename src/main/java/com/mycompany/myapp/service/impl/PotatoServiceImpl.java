package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.PotatoService;
import com.mycompany.myapp.domain.Potato;
import com.mycompany.myapp.repository.PotatoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Potato}.
 */
@Service
@Transactional
public class PotatoServiceImpl implements PotatoService {

    private final Logger log = LoggerFactory.getLogger(PotatoServiceImpl.class);

    private final PotatoRepository potatoRepository;

    public PotatoServiceImpl(PotatoRepository potatoRepository) {
        this.potatoRepository = potatoRepository;
    }

    /**
     * Save a potato.
     *
     * @param potato the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Potato save(Potato potato) {
        log.debug("Request to save Potato : {}", potato);
        return potatoRepository.save(potato);
    }

    /**
     * Get all the potatoes.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Potato> findAll() {
        log.debug("Request to get all Potatoes");
        return potatoRepository.findAll();
    }


    /**
     * Get one potato by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Potato> findOne(Long id) {
        log.debug("Request to get Potato : {}", id);
        return potatoRepository.findById(id);
    }

    /**
     * Delete the potato by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Potato : {}", id);
        potatoRepository.deleteById(id);
    }
}
