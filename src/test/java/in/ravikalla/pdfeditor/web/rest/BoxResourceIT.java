package in.ravikalla.pdfeditor.web.rest;

import in.ravikalla.pdfeditor.PdfEditorApp;
import in.ravikalla.pdfeditor.domain.Box;
import in.ravikalla.pdfeditor.repository.BoxRepository;
import in.ravikalla.pdfeditor.service.BoxService;
import in.ravikalla.pdfeditor.service.dto.BoxDTO;
import in.ravikalla.pdfeditor.service.mapper.BoxMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static in.ravikalla.pdfeditor.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BoxResource} REST controller.
 */
@SpringBootTest(classes = PdfEditorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BoxResourceIT {

    private static final String DEFAULT_ALIAS = "AAAAAAAAAA";
    private static final String UPDATED_ALIAS = "BBBBBBBBBB";

    private static final Integer DEFAULT_X_1 = 0;
    private static final Integer UPDATED_X_1 = 1;

    private static final Integer DEFAULT_Y_1 = 0;
    private static final Integer UPDATED_Y_1 = 1;

    private static final Integer DEFAULT_X_2 = 0;
    private static final Integer UPDATED_X_2 = 1;

    private static final Integer DEFAULT_Y_2 = 0;
    private static final Integer UPDATED_Y_2 = 1;

    private static final Integer DEFAULT_PAGE_NUMBER = 1;
    private static final Integer UPDATED_PAGE_NUMBER = 2;

    private static final ZonedDateTime DEFAULT_UPLOADDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPLOADDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private BoxRepository boxRepository;

    @Autowired
    private BoxMapper boxMapper;

    @Autowired
    private BoxService boxService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBoxMockMvc;

    private Box box;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Box createEntity(EntityManager em) {
        Box box = new Box()
            .alias(DEFAULT_ALIAS)
            .x1(DEFAULT_X_1)
            .y1(DEFAULT_Y_1)
            .x2(DEFAULT_X_2)
            .y2(DEFAULT_Y_2)
            .pageNumber(DEFAULT_PAGE_NUMBER)
            .uploaddate(DEFAULT_UPLOADDATE);
        return box;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Box createUpdatedEntity(EntityManager em) {
        Box box = new Box()
            .alias(UPDATED_ALIAS)
            .x1(UPDATED_X_1)
            .y1(UPDATED_Y_1)
            .x2(UPDATED_X_2)
            .y2(UPDATED_Y_2)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .uploaddate(UPDATED_UPLOADDATE);
        return box;
    }

    @BeforeEach
    public void initTest() {
        box = createEntity(em);
    }

    @Test
    @Transactional
    public void createBox() throws Exception {
        int databaseSizeBeforeCreate = boxRepository.findAll().size();

        // Create the Box
        BoxDTO boxDTO = boxMapper.toDto(box);
        restBoxMockMvc.perform(post("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isCreated());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeCreate + 1);
        Box testBox = boxList.get(boxList.size() - 1);
        assertThat(testBox.getAlias()).isEqualTo(DEFAULT_ALIAS);
        assertThat(testBox.getx1()).isEqualTo(DEFAULT_X_1);
        assertThat(testBox.gety1()).isEqualTo(DEFAULT_Y_1);
        assertThat(testBox.getx2()).isEqualTo(DEFAULT_X_2);
        assertThat(testBox.gety2()).isEqualTo(DEFAULT_Y_2);
        assertThat(testBox.getPageNumber()).isEqualTo(DEFAULT_PAGE_NUMBER);
        assertThat(testBox.getUploaddate()).isEqualTo(DEFAULT_UPLOADDATE);
    }

    @Test
    @Transactional
    public void createBoxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = boxRepository.findAll().size();

        // Create the Box with an existing ID
        box.setId(1L);
        BoxDTO boxDTO = boxMapper.toDto(box);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBoxMockMvc.perform(post("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkx1IsRequired() throws Exception {
        int databaseSizeBeforeTest = boxRepository.findAll().size();
        // set the field null
        box.setx1(null);

        // Create the Box, which fails.
        BoxDTO boxDTO = boxMapper.toDto(box);

        restBoxMockMvc.perform(post("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isBadRequest());

        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checky1IsRequired() throws Exception {
        int databaseSizeBeforeTest = boxRepository.findAll().size();
        // set the field null
        box.sety1(null);

        // Create the Box, which fails.
        BoxDTO boxDTO = boxMapper.toDto(box);

        restBoxMockMvc.perform(post("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isBadRequest());

        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPageNumberIsRequired() throws Exception {
        int databaseSizeBeforeTest = boxRepository.findAll().size();
        // set the field null
        box.setPageNumber(null);

        // Create the Box, which fails.
        BoxDTO boxDTO = boxMapper.toDto(box);

        restBoxMockMvc.perform(post("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isBadRequest());

        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBoxes() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        // Get all the boxList
        restBoxMockMvc.perform(get("/api/boxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(box.getId().intValue())))
            .andExpect(jsonPath("$.[*].alias").value(hasItem(DEFAULT_ALIAS)))
            .andExpect(jsonPath("$.[*].x1").value(hasItem(DEFAULT_X_1)))
            .andExpect(jsonPath("$.[*].y1").value(hasItem(DEFAULT_Y_1)))
            .andExpect(jsonPath("$.[*].x2").value(hasItem(DEFAULT_X_2)))
            .andExpect(jsonPath("$.[*].y2").value(hasItem(DEFAULT_Y_2)))
            .andExpect(jsonPath("$.[*].pageNumber").value(hasItem(DEFAULT_PAGE_NUMBER)))
            .andExpect(jsonPath("$.[*].uploaddate").value(hasItem(sameInstant(DEFAULT_UPLOADDATE))));
    }
    
    @Test
    @Transactional
    public void getBox() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        // Get the box
        restBoxMockMvc.perform(get("/api/boxes/{id}", box.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(box.getId().intValue()))
            .andExpect(jsonPath("$.alias").value(DEFAULT_ALIAS))
            .andExpect(jsonPath("$.x1").value(DEFAULT_X_1))
            .andExpect(jsonPath("$.y1").value(DEFAULT_Y_1))
            .andExpect(jsonPath("$.x2").value(DEFAULT_X_2))
            .andExpect(jsonPath("$.y2").value(DEFAULT_Y_2))
            .andExpect(jsonPath("$.pageNumber").value(DEFAULT_PAGE_NUMBER))
            .andExpect(jsonPath("$.uploaddate").value(sameInstant(DEFAULT_UPLOADDATE)));
    }

    @Test
    @Transactional
    public void getNonExistingBox() throws Exception {
        // Get the box
        restBoxMockMvc.perform(get("/api/boxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBox() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        int databaseSizeBeforeUpdate = boxRepository.findAll().size();

        // Update the box
        Box updatedBox = boxRepository.findById(box.getId()).get();
        // Disconnect from session so that the updates on updatedBox are not directly saved in db
        em.detach(updatedBox);
        updatedBox
            .alias(UPDATED_ALIAS)
            .x1(UPDATED_X_1)
            .y1(UPDATED_Y_1)
            .x2(UPDATED_X_2)
            .y2(UPDATED_Y_2)
            .pageNumber(UPDATED_PAGE_NUMBER)
            .uploaddate(UPDATED_UPLOADDATE);
        BoxDTO boxDTO = boxMapper.toDto(updatedBox);

        restBoxMockMvc.perform(put("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isOk());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeUpdate);
        Box testBox = boxList.get(boxList.size() - 1);
        assertThat(testBox.getAlias()).isEqualTo(UPDATED_ALIAS);
        assertThat(testBox.getx1()).isEqualTo(UPDATED_X_1);
        assertThat(testBox.gety1()).isEqualTo(UPDATED_Y_1);
        assertThat(testBox.getx2()).isEqualTo(UPDATED_X_2);
        assertThat(testBox.gety2()).isEqualTo(UPDATED_Y_2);
        assertThat(testBox.getPageNumber()).isEqualTo(UPDATED_PAGE_NUMBER);
        assertThat(testBox.getUploaddate()).isEqualTo(UPDATED_UPLOADDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingBox() throws Exception {
        int databaseSizeBeforeUpdate = boxRepository.findAll().size();

        // Create the Box
        BoxDTO boxDTO = boxMapper.toDto(box);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBoxMockMvc.perform(put("/api/boxes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(boxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Box in the database
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBox() throws Exception {
        // Initialize the database
        boxRepository.saveAndFlush(box);

        int databaseSizeBeforeDelete = boxRepository.findAll().size();

        // Delete the box
        restBoxMockMvc.perform(delete("/api/boxes/{id}", box.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Box> boxList = boxRepository.findAll();
        assertThat(boxList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
