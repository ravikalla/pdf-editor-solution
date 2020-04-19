package in.ravikalla.pdfeditor.repository;

import in.ravikalla.pdfeditor.domain.FileInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the FileInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {
}
