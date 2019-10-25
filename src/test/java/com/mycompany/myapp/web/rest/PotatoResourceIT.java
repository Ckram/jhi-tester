package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TesterApp;
import com.mycompany.myapp.domain.Potato;
import com.mycompany.myapp.repository.PotatoRepository;
import com.mycompany.myapp.service.PotatoService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.mycompany.myapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PotatoResource} REST controller.
 */
@SpringBootTest(classes = TesterApp.class)
public class PotatoResourceIT {

    private static final String DEFAULT_SHAPE = "AAAAAAAAAA";
    private static final String UPDATED_SHAPE = "BBBBBBBBBB";

    private static final String DEFAULT_SIZE = "AAAAAAAAAA";
    private static final String UPDATED_SIZE = "BBBBBBBBBB";

    @Autowired
    private PotatoRepository potatoRepository;

    @Autowired
    private PotatoService potatoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPotatoMockMvc;

    private Potato potato;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PotatoResource potatoResource = new PotatoResource(potatoService);
        this.restPotatoMockMvc = MockMvcBuilders.standaloneSetup(potatoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Potato createEntity(EntityManager em) {
        Potato potato = new Potato()
            .shape(DEFAULT_SHAPE)
            .size(DEFAULT_SIZE);
        return potato;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Potato createUpdatedEntity(EntityManager em) {
        Potato potato = new Potato()
            .shape(UPDATED_SHAPE)
            .size(UPDATED_SIZE);
        return potato;
    }

    @BeforeEach
    public void initTest() {
        potato = createEntity(em);
    }

    @Test
    @Transactional
    public void createPotato() throws Exception {
        int databaseSizeBeforeCreate = potatoRepository.findAll().size();

        // Create the Potato
        restPotatoMockMvc.perform(post("/api/potatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(potato)))
            .andExpect(status().isCreated());

        // Validate the Potato in the database
        List<Potato> potatoList = potatoRepository.findAll();
        assertThat(potatoList).hasSize(databaseSizeBeforeCreate + 1);
        Potato testPotato = potatoList.get(potatoList.size() - 1);
        assertThat(testPotato.getShape()).isEqualTo(DEFAULT_SHAPE);
        assertThat(testPotato.getSize()).isEqualTo(DEFAULT_SIZE);
    }

    @Test
    @Transactional
    public void createPotatoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = potatoRepository.findAll().size();

        // Create the Potato with an existing ID
        potato.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPotatoMockMvc.perform(post("/api/potatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(potato)))
            .andExpect(status().isBadRequest());

        // Validate the Potato in the database
        List<Potato> potatoList = potatoRepository.findAll();
        assertThat(potatoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPotatoes() throws Exception {
        // Initialize the database
        potatoRepository.saveAndFlush(potato);

        // Get all the potatoList
        restPotatoMockMvc.perform(get("/api/potatoes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(potato.getId().intValue())))
            .andExpect(jsonPath("$.[*].shape").value(hasItem(DEFAULT_SHAPE)))
            .andExpect(jsonPath("$.[*].size").value(hasItem(DEFAULT_SIZE)));
    }
    
    @Test
    @Transactional
    public void getPotato() throws Exception {
        // Initialize the database
        potatoRepository.saveAndFlush(potato);

        // Get the potato
        restPotatoMockMvc.perform(get("/api/potatoes/{id}", potato.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(potato.getId().intValue()))
            .andExpect(jsonPath("$.shape").value(DEFAULT_SHAPE))
            .andExpect(jsonPath("$.size").value(DEFAULT_SIZE));
    }

    @Test
    @Transactional
    public void getNonExistingPotato() throws Exception {
        // Get the potato
        restPotatoMockMvc.perform(get("/api/potatoes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePotato() throws Exception {
        // Initialize the database
        potatoService.save(potato);

        int databaseSizeBeforeUpdate = potatoRepository.findAll().size();

        // Update the potato
        Potato updatedPotato = potatoRepository.findById(potato.getId()).get();
        // Disconnect from session so that the updates on updatedPotato are not directly saved in db
        em.detach(updatedPotato);
        updatedPotato
            .shape(UPDATED_SHAPE)
            .size(UPDATED_SIZE);

        restPotatoMockMvc.perform(put("/api/potatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPotato)))
            .andExpect(status().isOk());

        // Validate the Potato in the database
        List<Potato> potatoList = potatoRepository.findAll();
        assertThat(potatoList).hasSize(databaseSizeBeforeUpdate);
        Potato testPotato = potatoList.get(potatoList.size() - 1);
        assertThat(testPotato.getShape()).isEqualTo(UPDATED_SHAPE);
        assertThat(testPotato.getSize()).isEqualTo(UPDATED_SIZE);
    }

    @Test
    @Transactional
    public void updateNonExistingPotato() throws Exception {
        int databaseSizeBeforeUpdate = potatoRepository.findAll().size();

        // Create the Potato

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPotatoMockMvc.perform(put("/api/potatoes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(potato)))
            .andExpect(status().isBadRequest());

        // Validate the Potato in the database
        List<Potato> potatoList = potatoRepository.findAll();
        assertThat(potatoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePotato() throws Exception {
        // Initialize the database
        potatoService.save(potato);

        int databaseSizeBeforeDelete = potatoRepository.findAll().size();

        // Delete the potato
        restPotatoMockMvc.perform(delete("/api/potatoes/{id}", potato.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Potato> potatoList = potatoRepository.findAll();
        assertThat(potatoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Potato.class);
        Potato potato1 = new Potato();
        potato1.setId(1L);
        Potato potato2 = new Potato();
        potato2.setId(potato1.getId());
        assertThat(potato1).isEqualTo(potato2);
        potato2.setId(2L);
        assertThat(potato1).isNotEqualTo(potato2);
        potato1.setId(null);
        assertThat(potato1).isNotEqualTo(potato2);
    }
}
