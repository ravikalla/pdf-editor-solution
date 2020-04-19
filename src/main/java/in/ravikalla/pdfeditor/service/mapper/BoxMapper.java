package in.ravikalla.pdfeditor.service.mapper;


import in.ravikalla.pdfeditor.domain.*;
import in.ravikalla.pdfeditor.service.dto.BoxDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Box} and its DTO {@link BoxDTO}.
 */
@Mapper(componentModel = "spring", uses = {FileInfoMapper.class})
public interface BoxMapper extends EntityMapper<BoxDTO, Box> {

    @Mapping(source = "fileInfo.id", target = "fileInfoId")
    BoxDTO toDto(Box box);

    @Mapping(source = "fileInfoId", target = "fileInfo")
    Box toEntity(BoxDTO boxDTO);

    default Box fromId(Long id) {
        if (id == null) {
            return null;
        }
        Box box = new Box();
        box.setId(id);
        return box;
    }
}
