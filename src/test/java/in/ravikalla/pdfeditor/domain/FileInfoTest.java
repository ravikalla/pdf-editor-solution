package in.ravikalla.pdfeditor.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import in.ravikalla.pdfeditor.web.rest.TestUtil;

public class FileInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileInfo.class);
        FileInfo fileInfo1 = new FileInfo();
        fileInfo1.setId(1L);
        FileInfo fileInfo2 = new FileInfo();
        fileInfo2.setId(fileInfo1.getId());
        assertThat(fileInfo1).isEqualTo(fileInfo2);
        fileInfo2.setId(2L);
        assertThat(fileInfo1).isNotEqualTo(fileInfo2);
        fileInfo1.setId(null);
        assertThat(fileInfo1).isNotEqualTo(fileInfo2);
    }
}
