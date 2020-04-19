package in.ravikalla.pdfeditor.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class FileInfoMapperTest {

    private FileInfoMapper fileInfoMapper;

    @BeforeEach
    public void setUp() {
        fileInfoMapper = new FileInfoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(fileInfoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(fileInfoMapper.fromId(null)).isNull();
    }
}
