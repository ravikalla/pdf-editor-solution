package in.ravikalla.pdfeditor.service.mapper;


import in.ravikalla.pdfeditor.domain.*;
import in.ravikalla.pdfeditor.service.dto.FileInfoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FileInfo} and its DTO {@link FileInfoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FileInfoMapper extends EntityMapper<FileInfoDTO, FileInfo> {


    @Mapping(target = "boxes", ignore = true)
    @Mapping(target = "removeBox", ignore = true)
    FileInfo toEntity(FileInfoDTO fileInfoDTO);

    default FileInfo fromId(Long id) {
        if (id == null) {
            return null;
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(id);
        return fileInfo;
    }
}
