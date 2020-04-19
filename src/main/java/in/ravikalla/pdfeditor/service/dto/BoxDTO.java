package in.ravikalla.pdfeditor.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link in.ravikalla.pdfeditor.domain.Box} entity.
 */
public class BoxDTO implements Serializable {
    
    private Long id;

    private String alias;

    @NotNull
    @Min(value = 0)
    @Max(value = 10000)
    private Integer x1;

    @NotNull
    @Min(value = 0)
    @Max(value = 10000)
    private Integer y1;

    @Min(value = 0)
    @Max(value = 10000)
    private Integer x2;

    @Min(value = 0)
    @Max(value = 10000)
    private Integer y2;

    @NotNull
    private Integer pageNumber;

    private ZonedDateTime uploaddate;


    private Long fileInfoId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getx1() {
        return x1;
    }

    public void setx1(Integer x1) {
        this.x1 = x1;
    }

    public Integer gety1() {
        return y1;
    }

    public void sety1(Integer y1) {
        this.y1 = y1;
    }

    public Integer getx2() {
        return x2;
    }

    public void setx2(Integer x2) {
        this.x2 = x2;
    }

    public Integer gety2() {
        return y2;
    }

    public void sety2(Integer y2) {
        this.y2 = y2;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public ZonedDateTime getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(ZonedDateTime uploaddate) {
        this.uploaddate = uploaddate;
    }

    public Long getFileInfoId() {
        return fileInfoId;
    }

    public void setFileInfoId(Long fileInfoId) {
        this.fileInfoId = fileInfoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BoxDTO boxDTO = (BoxDTO) o;
        if (boxDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), boxDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "BoxDTO{" +
            "id=" + getId() +
            ", alias='" + getAlias() + "'" +
            ", x1=" + getx1() +
            ", y1=" + gety1() +
            ", x2=" + getx2() +
            ", y2=" + gety2() +
            ", pageNumber=" + getPageNumber() +
            ", uploaddate='" + getUploaddate() + "'" +
            ", fileInfoId=" + getFileInfoId() +
            "}";
    }
}
