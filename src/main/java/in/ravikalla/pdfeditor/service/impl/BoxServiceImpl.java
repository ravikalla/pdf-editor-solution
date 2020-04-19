package in.ravikalla.pdfeditor.service.impl;

import in.ravikalla.pdfeditor.service.BoxService;
import in.ravikalla.pdfeditor.domain.Box;
import in.ravikalla.pdfeditor.repository.BoxRepository;
import in.ravikalla.pdfeditor.service.dto.BoxDTO;
import in.ravikalla.pdfeditor.service.mapper.BoxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Box}.
 */
@Service
@Transactional
public class BoxServiceImpl implements BoxService {

    private final Logger log = LoggerFactory.getLogger(BoxServiceImpl.class);

    private final BoxRepository boxRepository;

    private final BoxMapper boxMapper;

    public BoxServiceImpl(BoxRepository boxRepository, BoxMapper boxMapper) {
        this.boxRepository = boxRepository;
        this.boxMapper = boxMapper;
    }

    /**
     * Save a box.
     *
     * @param boxDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public BoxDTO save(BoxDTO boxDTO) {
        log.debug("Request to save Box : {}", boxDTO);
        Box box = boxMapper.toEntity(boxDTO);
        box = boxRepository.save(box);
        return boxMapper.toDto(box);
    }

    /**
     * Get all the boxes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<BoxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Boxes");
        return boxRepository.findAll(pageable)
            .map(boxMapper::toDto);
    }

    /**
     * Get one box by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<BoxDTO> findOne(Long id) {
        log.debug("Request to get Box : {}", id);
        return boxRepository.findById(id)
            .map(boxMapper::toDto);
    }

    /**
     * Delete the box by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Box : {}", id);
        boxRepository.deleteById(id);
    }
}
