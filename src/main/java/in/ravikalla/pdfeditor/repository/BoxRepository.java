package in.ravikalla.pdfeditor.repository;

import in.ravikalla.pdfeditor.domain.Box;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Box entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BoxRepository extends JpaRepository<Box, Long> {
}
