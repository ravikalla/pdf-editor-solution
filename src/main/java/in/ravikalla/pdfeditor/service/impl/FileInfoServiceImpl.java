package in.ravikalla.pdfeditor.service.impl;

import in.ravikalla.pdfeditor.service.FileInfoService;
import in.ravikalla.pdfeditor.domain.FileInfo;
import in.ravikalla.pdfeditor.repository.FileInfoRepository;
import in.ravikalla.pdfeditor.service.dto.FileInfoDTO;
import in.ravikalla.pdfeditor.service.mapper.FileInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FileInfo}.
 */
@Service
@Transactional
public class FileInfoServiceImpl implements FileInfoService {

    private final Logger log = LoggerFactory.getLogger(FileInfoServiceImpl.class);

    private final FileInfoRepository fileInfoRepository;

    private final FileInfoMapper fileInfoMapper;

    public FileInfoServiceImpl(FileInfoRepository fileInfoRepository, FileInfoMapper fileInfoMapper) {
        this.fileInfoRepository = fileInfoRepository;
        this.fileInfoMapper = fileInfoMapper;
    }

    /**
     * Save a fileInfo.
     *
     * @param fileInfoDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FileInfoDTO save(FileInfoDTO fileInfoDTO) {
        log.debug("Request to save FileInfo : {}", fileInfoDTO);
        FileInfo fileInfo = fileInfoMapper.toEntity(fileInfoDTO);
        fileInfo = fileInfoRepository.save(fileInfo);
        return fileInfoMapper.toDto(fileInfo);
    }

    /**
     * Get all the fileInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileInfos");
        return fileInfoRepository.findAll(pageable)
            .map(fileInfoMapper::toDto);
    }

    /**
     * Get one fileInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileInfoDTO> findOne(Long id) {
        log.debug("Request to get FileInfo : {}", id);
        return fileInfoRepository.findById(id)
            .map(fileInfoMapper::toDto);
    }

    /**
     * Delete the fileInfo by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileInfo : {}", id);
        fileInfoRepository.deleteById(id);
    }
}
