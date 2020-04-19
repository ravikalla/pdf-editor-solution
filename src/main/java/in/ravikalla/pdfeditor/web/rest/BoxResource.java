package in.ravikalla.pdfeditor.web.rest;

import in.ravikalla.pdfeditor.service.BoxService;
import in.ravikalla.pdfeditor.web.rest.errors.BadRequestAlertException;
import in.ravikalla.pdfeditor.service.dto.BoxDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link in.ravikalla.pdfeditor.domain.Box}.
 */
@RestController
@RequestMapping("/api")
public class BoxResource {

    private final Logger log = LoggerFactory.getLogger(BoxResource.class);

    private static final String ENTITY_NAME = "box";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final BoxService boxService;

    public BoxResource(BoxService boxService) {
        this.boxService = boxService;
    }

    /**
     * {@code POST  /boxes} : Create a new box.
     *
     * @param boxDTO the boxDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new boxDTO, or with status {@code 400 (Bad Request)} if the box has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/boxes")
    public ResponseEntity<BoxDTO> createBox(@Valid @RequestBody BoxDTO boxDTO) throws URISyntaxException {
        log.debug("REST request to save Box : {}", boxDTO);
        if (boxDTO.getId() != null) {
            throw new BadRequestAlertException("A new box cannot already have an ID", ENTITY_NAME, "idexists");
        }
        BoxDTO result = boxService.save(boxDTO);
        return ResponseEntity.created(new URI("/api/boxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /boxes} : Updates an existing box.
     *
     * @param boxDTO the boxDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated boxDTO,
     * or with status {@code 400 (Bad Request)} if the boxDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the boxDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/boxes")
    public ResponseEntity<BoxDTO> updateBox(@Valid @RequestBody BoxDTO boxDTO) throws URISyntaxException {
        log.debug("REST request to update Box : {}", boxDTO);
        if (boxDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        BoxDTO result = boxService.save(boxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, boxDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /boxes} : get all the boxes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of boxes in body.
     */
    @GetMapping("/boxes")
    public ResponseEntity<List<BoxDTO>> getAllBoxes(Pageable pageable) {
        log.debug("REST request to get a page of Boxes");
        Page<BoxDTO> page = boxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /boxes/:id} : get the "id" box.
     *
     * @param id the id of the boxDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the boxDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/boxes/{id}")
    public ResponseEntity<BoxDTO> getBox(@PathVariable Long id) {
        log.debug("REST request to get Box : {}", id);
        Optional<BoxDTO> boxDTO = boxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(boxDTO);
    }

    /**
     * {@code DELETE  /boxes/:id} : delete the "id" box.
     *
     * @param id the id of the boxDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/boxes/{id}")
    public ResponseEntity<Void> deleteBox(@PathVariable Long id) {
        log.debug("REST request to delete Box : {}", id);
        boxService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
