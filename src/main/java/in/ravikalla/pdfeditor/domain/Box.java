package in.ravikalla.pdfeditor.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;
import java.time.ZonedDateTime;

/**
 * A Box.
 */
@Entity
@Table(name = "box")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Box implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alias")
    private String alias;

    @NotNull
    @Min(value = 0)
    @Max(value = 10000)
    @Column(name = "x_1", nullable = false)
    private Integer x1;

    @NotNull
    @Min(value = 0)
    @Max(value = 10000)
    @Column(name = "y_1", nullable = false)
    private Integer y1;

    @Min(value = 0)
    @Max(value = 10000)
    @Column(name = "x_2")
    private Integer x2;

    @Min(value = 0)
    @Max(value = 10000)
    @Column(name = "y_2")
    private Integer y2;

    @NotNull
    @Column(name = "page_number", nullable = false)
    private Integer pageNumber;

    @Column(name = "uploaddate")
    private ZonedDateTime uploaddate;

    @ManyToOne
    @JsonIgnoreProperties("boxes")
    private FileInfo fileInfo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public Box alias(String alias) {
        this.alias = alias;
        return this;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getx1() {
        return x1;
    }

    public Box x1(Integer x1) {
        this.x1 = x1;
        return this;
    }

    public void setx1(Integer x1) {
        this.x1 = x1;
    }

    public Integer gety1() {
        return y1;
    }

    public Box y1(Integer y1) {
        this.y1 = y1;
        return this;
    }

    public void sety1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getx2() {
        return x2;
    }

    public Box x2(Integer x2) {
        this.x2 = x2;
        return this;
    }

    public void setx2(Integer x2) {
        this.x2 = x2;
    }

    public Integer gety2() {
        return y2;
    }

    public Box y2(Integer y2) {
        this.y2 = y2;
        return this;
    }

    public void sety2(Integer y2) {
        this.y2 = y2;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public Box pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ZonedDateTime getUploaddate() {
        return uploaddate;
    }

    public Box uploaddate(ZonedDateTime uploaddate) {
        this.uploaddate = uploaddate;
        return this;
    }

    public void setUploaddate(ZonedDateTime uploaddate) {
        this.uploaddate = uploaddate;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    public Box fileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
        return this;
    }

    public void setFileInfo(FileInfo fileInfo) {
        this.fileInfo = fileInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Box)) {
            return false;
        }
        return id != null && id.equals(((Box) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Box{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", x1=" + getx1() +
            ", y1=" + gety1() +
            ", x2=" + getx2() +
            ", y2=" + gety2() +
            ", pageNumber=" + getPageNumber() +
            ", uploaddate='" + getUploaddate() + "'" +
            "}";
    }
}
