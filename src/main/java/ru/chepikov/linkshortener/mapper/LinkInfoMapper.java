package ru.chepikov.linkshortener.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.chepikov.linkshortener.dto.CreateShortLinkRequest;
import ru.chepikov.linkshortener.dto.LinkInfoResponse;
import ru.chepikov.linkshortener.model.LinkInfo;

@Component
@Mapper(componentModel = "spring")
public interface LinkInfoMapper {

    LinkInfo fromCreateRequest(CreateShortLinkRequest request);

    LinkInfoResponse toResponse(LinkInfo linkInfo);
}
