package in.ravikalla.pdfeditor.web.rest;

import in.ravikalla.pdfeditor.PdfEditorApp;
import in.ravikalla.pdfeditor.domain.FileInfo;
import in.ravikalla.pdfeditor.repository.FileInfoRepository;
import in.ravikalla.pdfeditor.service.FileInfoService;
import in.ravikalla.pdfeditor.service.dto.FileInfoDTO;
import in.ravikalla.pdfeditor.service.mapper.FileInfoMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
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

import in.ravikalla.pdfeditor.domain.enumeration.FileType;
/**
 * Integration tests for the {@link FileInfoResource} REST controller.
 */
@SpringBootTest(classes = PdfEditorApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FileInfoResourceIT {

    private static final byte[] DEFAULT_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FILE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_NOTES = "BBBBBBBBBB";

    private static final FileType DEFAULT_FILE_TYPE = FileType.PDF;
    private static final FileType UPDATED_FILE_TYPE = FileType.JPEG;

    private static final ZonedDateTime DEFAULT_UPLOADDATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_UPLOADDATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private FileInfoRepository fileInfoRepository;

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFileInfoMockMvc;

    private FileInfo fileInfo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileInfo createEntity(EntityManager em) {
        FileInfo fileInfo = new FileInfo()
            .file(DEFAULT_FILE)
            .fileContentType(DEFAULT_FILE_CONTENT_TYPE)
            .notes(DEFAULT_NOTES)
            .fileType(DEFAULT_FILE_TYPE)
            .uploaddate(DEFAULT_UPLOADDATE);
        return fileInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileInfo createUpdatedEntity(EntityManager em) {
        FileInfo fileInfo = new FileInfo()
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .notes(UPDATED_NOTES)
            .fileType(UPDATED_FILE_TYPE)
            .uploaddate(UPDATED_UPLOADDATE);
        return fileInfo;
    }

    @BeforeEach
    public void initTest() {
        fileInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileInfo() throws Exception {
        int databaseSizeBeforeCreate = fileInfoRepository.findAll().size();

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);
        restFileInfoMockMvc.perform(post("/api/file-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeCreate + 1);
        FileInfo testFileInfo = fileInfoList.get(fileInfoList.size() - 1);
        assertThat(testFileInfo.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testFileInfo.getFileContentType()).isEqualTo(DEFAULT_FILE_CONTENT_TYPE);
        assertThat(testFileInfo.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testFileInfo.getFileType()).isEqualTo(DEFAULT_FILE_TYPE);
        assertThat(testFileInfo.getUploaddate()).isEqualTo(DEFAULT_UPLOADDATE);
    }

    @Test
    @Transactional
    public void createFileInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileInfoRepository.findAll().size();

        // Create the FileInfo with an existing ID
        fileInfo.setId(1L);
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileInfoMockMvc.perform(post("/api/file-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFileInfos() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        // Get all the fileInfoList
        restFileInfoMockMvc.perform(get("/api/file-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileContentType").value(hasItem(DEFAULT_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(Base64Utils.encodeToString(DEFAULT_FILE))))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].fileType").value(hasItem(DEFAULT_FILE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].uploaddate").value(hasItem(sameInstant(DEFAULT_UPLOADDATE))));
    }
    
    @Test
    @Transactional
    public void getFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        // Get the fileInfo
        restFileInfoMockMvc.perform(get("/api/file-infos/{id}", fileInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fileInfo.getId().intValue()))
            .andExpect(jsonPath("$.fileContentType").value(DEFAULT_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.file").value(Base64Utils.encodeToString(DEFAULT_FILE)))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.fileType").value(DEFAULT_FILE_TYPE.toString()))
            .andExpect(jsonPath("$.uploaddate").value(sameInstant(DEFAULT_UPLOADDATE)));
    }

    @Test
    @Transactional
    public void getNonExistingFileInfo() throws Exception {
        // Get the fileInfo
        restFileInfoMockMvc.perform(get("/api/file-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();

        // Update the fileInfo
        FileInfo updatedFileInfo = fileInfoRepository.findById(fileInfo.getId()).get();
        // Disconnect from session so that the updates on updatedFileInfo are not directly saved in db
        em.detach(updatedFileInfo);
        updatedFileInfo
            .file(UPDATED_FILE)
            .fileContentType(UPDATED_FILE_CONTENT_TYPE)
            .notes(UPDATED_NOTES)
            .fileType(UPDATED_FILE_TYPE)
            .uploaddate(UPDATED_UPLOADDATE);
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(updatedFileInfo);

        restFileInfoMockMvc.perform(put("/api/file-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isOk());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
        FileInfo testFileInfo = fileInfoList.get(fileInfoList.size() - 1);
        assertThat(testFileInfo.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testFileInfo.getFileContentType()).isEqualTo(UPDATED_FILE_CONTENT_TYPE);
        assertThat(testFileInfo.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testFileInfo.getFileType()).isEqualTo(UPDATED_FILE_TYPE);
        assertThat(testFileInfo.getUploaddate()).isEqualTo(UPDATED_UPLOADDATE);
    }

    @Test
    @Transactional
    public void updateNonExistingFileInfo() throws Exception {
        int databaseSizeBeforeUpdate = fileInfoRepository.findAll().size();

        // Create the FileInfo
        FileInfoDTO fileInfoDTO = fileInfoMapper.toDto(fileInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileInfoMockMvc.perform(put("/api/file-infos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(fileInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileInfo in the database
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFileInfo() throws Exception {
        // Initialize the database
        fileInfoRepository.saveAndFlush(fileInfo);

        int databaseSizeBeforeDelete = fileInfoRepository.findAll().size();

        // Delete the fileInfo
        restFileInfoMockMvc.perform(delete("/api/file-infos/{id}", fileInfo.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FileInfo> fileInfoList = fileInfoRepository.findAll();
        assertThat(fileInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
