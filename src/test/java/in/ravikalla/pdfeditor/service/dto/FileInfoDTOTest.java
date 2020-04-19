package in.ravikalla.pdfeditor.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import in.ravikalla.pdfeditor.web.rest.TestUtil;

public class FileInfoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileInfoDTO.class);
        FileInfoDTO fileInfoDTO1 = new FileInfoDTO();
        fileInfoDTO1.setId(1L);
        FileInfoDTO fileInfoDTO2 = new FileInfoDTO();
        assertThat(fileInfoDTO1).isNotEqualTo(fileInfoDTO2);
        fileInfoDTO2.setId(fileInfoDTO1.getId());
        assertThat(fileInfoDTO1).isEqualTo(fileInfoDTO2);
        fileInfoDTO2.setId(2L);
        assertThat(fileInfoDTO1).isNotEqualTo(fileInfoDTO2);
        fileInfoDTO1.setId(null);
        assertThat(fileInfoDTO1).isNotEqualTo(fileInfoDTO2);
    }
}
