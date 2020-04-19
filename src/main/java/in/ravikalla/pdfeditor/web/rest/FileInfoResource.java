package in.ravikalla.pdfeditor.web.rest;

import in.ravikalla.pdfeditor.service.FileInfoService;
import in.ravikalla.pdfeditor.web.rest.errors.BadRequestAlertException;
import in.ravikalla.pdfeditor.service.dto.FileInfoDTO;

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
 * REST controller for managing {@link in.ravikalla.pdfeditor.domain.FileInfo}.
 */
@RestController
@RequestMapping("/api")
public class FileInfoResource {

    private final Logger log = LoggerFactory.getLogger(FileInfoResource.class);

    private static final String ENTITY_NAME = "fileInfo";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileInfoService fileInfoService;

    public FileInfoResource(FileInfoService fileInfoService) {
        this.fileInfoService = fileInfoService;
    }

    /**
     * {@code POST  /file-infos} : Create a new fileInfo.
     *
     * @param fileInfoDTO the fileInfoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileInfoDTO, or with status {@code 400 (Bad Request)} if the fileInfo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-infos")
    public ResponseEntity<FileInfoDTO> createFileInfo(@Valid @RequestBody FileInfoDTO fileInfoDTO) throws URISyntaxException {
        log.debug("REST request to save FileInfo : {}", fileInfoDTO);
        if (fileInfoDTO.getId() != null) {
            throw new BadRequestAlertException("A new fileInfo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileInfoDTO result = fileInfoService.save(fileInfoDTO);
        return ResponseEntity.created(new URI("/api/file-infos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-infos} : Updates an existing fileInfo.
     *
     * @param fileInfoDTO the fileInfoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileInfoDTO,
     * or with status {@code 400 (Bad Request)} if the fileInfoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileInfoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-infos")
    public ResponseEntity<FileInfoDTO> updateFileInfo(@Valid @RequestBody FileInfoDTO fileInfoDTO) throws URISyntaxException {
        log.debug("REST request to update FileInfo : {}", fileInfoDTO);
        if (fileInfoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileInfoDTO result = fileInfoService.save(fileInfoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fileInfoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-infos} : get all the fileInfos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileInfos in body.
     */
    @GetMapping("/file-infos")
    public ResponseEntity<List<FileInfoDTO>> getAllFileInfos(Pageable pageable) {
        log.debug("REST request to get a page of FileInfos");
        Page<FileInfoDTO> page = fileInfoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-infos/:id} : get the "id" fileInfo.
     *
     * @param id the id of the fileInfoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileInfoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-infos/{id}")
    public ResponseEntity<FileInfoDTO> getFileInfo(@PathVariable Long id) {
        log.debug("REST request to get FileInfo : {}", id);
        Optional<FileInfoDTO> fileInfoDTO = fileInfoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileInfoDTO);
    }

    /**
     * {@code DELETE  /file-infos/:id} : delete the "id" fileInfo.
     *
     * @param id the id of the fileInfoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-infos/{id}")
    public ResponseEntity<Void> deleteFileInfo(@PathVariable Long id) {
        log.debug("REST request to delete FileInfo : {}", id);
        fileInfoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
