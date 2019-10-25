package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.domain.Potato;
import com.mycompany.myapp.service.PotatoService;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.Potato}.
 */
@RestController
@RequestMapping("/api")
public class PotatoResource {

    private final Logger log = LoggerFactory.getLogger(PotatoResource.class);

    private static final String ENTITY_NAME = "potato";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PotatoService potatoService;

    public PotatoResource(PotatoService potatoService) {
        this.potatoService = potatoService;
    }

    /**
     * {@code POST  /potatoes} : Create a new potato.
     *
     * @param potato the potato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new potato, or with status {@code 400 (Bad Request)} if the potato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/potatoes")
    public ResponseEntity<Potato> createPotato(@RequestBody Potato potato) throws URISyntaxException {
        log.debug("REST request to save Potato : {}", potato);
        if (potato.getId() != null) {
            throw new BadRequestAlertException("A new potato cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Potato result = potatoService.save(potato);
        return ResponseEntity.created(new URI("/api/potatoes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /potatoes} : Updates an existing potato.
     *
     * @param potato the potato to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated potato,
     * or with status {@code 400 (Bad Request)} if the potato is not valid,
     * or with status {@code 500 (Internal Server Error)} if the potato couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/potatoes")
    public ResponseEntity<Potato> updatePotato(@RequestBody Potato potato) throws URISyntaxException {
        log.debug("REST request to update Potato : {}", potato);
        if (potato.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Potato result = potatoService.save(potato);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, potato.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /potatoes} : get all the potatoes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of potatoes in body.
     */
    @GetMapping("/potatoes")
    public List<Potato> getAllPotatoes() {
        log.debug("REST request to get all Potatoes");
        return potatoService.findAll();
    }

    /**
     * {@code GET  /potatoes/:id} : get the "id" potato.
     *
     * @param id the id of the potato to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the potato, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/potatoes/{id}")
    public ResponseEntity<Potato> getPotato(@PathVariable Long id) {
        log.debug("REST request to get Potato : {}", id);
        Optional<Potato> potato = potatoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(potato);
    }

    /**
     * {@code DELETE  /potatoes/:id} : delete the "id" potato.
     *
     * @param id the id of the potato to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/potatoes/{id}")
    public ResponseEntity<Void> deletePotato(@PathVariable Long id) {
        log.debug("REST request to delete Potato : {}", id);
        potatoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
