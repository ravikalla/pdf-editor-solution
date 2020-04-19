package in.ravikalla.pdfeditor.service;

import in.ravikalla.pdfeditor.service.dto.FileInfoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link in.ravikalla.pdfeditor.domain.FileInfo}.
 */
public interface FileInfoService {

    /**
     * Save a fileInfo.
     *
     * @param fileInfoDTO the entity to save.
     * @return the persisted entity.
     */
    FileInfoDTO save(FileInfoDTO fileInfoDTO);

    /**
     * Get all the fileInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FileInfoDTO> findAll(Pageable pageable);

    /**
     * Get the "id" fileInfo.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileInfoDTO> findOne(Long id);

    /**
     * Delete the "id" fileInfo.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
