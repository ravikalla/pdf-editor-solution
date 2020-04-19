package in.ravikalla.pdfeditor.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import in.ravikalla.pdfeditor.domain.enumeration.FileType;

/**
 * A DTO for the {@link in.ravikalla.pdfeditor.domain.FileInfo} entity.
 */
public class FileInfoDTO implements Serializable {
    
    private Long id;

    
    @Lob
    private byte[] file;

    private String fileContentType;
    private String notes;

    private FileType fileType;

    private ZonedDateTime uploaddate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public FileType getFileType() {
        return fileType;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public ZonedDateTime getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(ZonedDateTime uploaddate) {
        this.uploaddate = uploaddate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FileInfoDTO fileInfoDTO = (FileInfoDTO) o;
        if (fileInfoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fileInfoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FileInfoDTO{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", notes='" + getNotes() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", uploaddate='" + getUploaddate() + "'" +
            "}";
    }
}
