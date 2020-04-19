package in.ravikalla.pdfeditor.service;

import in.ravikalla.pdfeditor.service.dto.BoxDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link in.ravikalla.pdfeditor.domain.Box}.
 */
public interface BoxService {

    /**
     * Save a box.
     *
     * @param boxDTO the entity to save.
     * @return the persisted entity.
     */
    BoxDTO save(BoxDTO boxDTO);

    /**
     * Get all the boxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BoxDTO> findAll(Pageable pageable);

    /**
     * Get the "id" box.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BoxDTO> findOne(Long id);

    /**
     * Delete the "id" box.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
