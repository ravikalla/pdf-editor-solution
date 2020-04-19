package in.ravikalla.pdfeditor.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import in.ravikalla.pdfeditor.domain.enumeration.FileType;

/**
 * A FileInfo.
 */
@Entity
@Table(name = "file_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Lob
    @Column(name = "file", nullable = false)
    private byte[] file;

    @Column(name = "file_content_type", nullable = false)
    private String fileContentType;

    @Column(name = "notes")
    private String notes;

    @Enumerated(EnumType.STRING)
    @Column(name = "file_type")
    private FileType fileType;

    @Column(name = "uploaddate")
    private ZonedDateTime uploaddate;

    @OneToMany(mappedBy = "fileInfo")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Box> boxes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getFile() {
        return file;
    }

    public FileInfo file(byte[] file) {
        this.file = file;
        return this;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public FileInfo fileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
        return this;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }

    public String getNotes() {
        return notes;
    }

    public FileInfo notes(String notes) {
        this.notes = notes;
        return this;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public FileType getFileType() {
        return fileType;
    }

    public FileInfo fileType(FileType fileType) {
        this.fileType = fileType;
        return this;
    }

    public void setFileType(FileType fileType) {
        this.fileType = fileType;
    }

    public ZonedDateTime getUploaddate() {
        return uploaddate;
    }

    public FileInfo uploaddate(ZonedDateTime uploaddate) {
        this.uploaddate = uploaddate;
        return this;
    }

    public void setUploaddate(ZonedDateTime uploaddate) {
        this.uploaddate = uploaddate;
    }

    public Set<Box> getBoxes() {
        return boxes;
    }

    public FileInfo boxes(Set<Box> boxes) {
        this.boxes = boxes;
        return this;
    }

    public FileInfo addBox(Box box) {
        this.boxes.add(box);
        box.setFileInfo(this);
        return this;
    }

    public FileInfo removeBox(Box box) {
        this.boxes.remove(box);
        box.setFileInfo(null);
        return this;
    }

    public void setBoxes(Set<Box> boxes) {
        this.boxes = boxes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileInfo)) {
            return false;
        }
        return id != null && id.equals(((FileInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
            "id=" + getId() +
            ", file='" + getFile() + "'" +
            ", fileContentType='" + getFileContentType() + "'" +
            ", notes='" + getNotes() + "'" +
            ", fileType='" + getFileType() + "'" +
            ", uploaddate='" + getUploaddate() + "'" +
            "}";
    }
}
