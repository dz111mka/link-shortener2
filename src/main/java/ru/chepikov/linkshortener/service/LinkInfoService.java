package ru.chepikov.linkshortener.service;

import ru.chepikov.linkshortener.dto.CreateShortLinkRequest;
import ru.chepikov.linkshortener.dto.FilterLinkInfoRequest;
import ru.chepikov.linkshortener.dto.LinkInfoResponse;
import ru.chepikov.linkshortener.model.LinkInfo;

import java.util.List;
import java.util.UUID;

public interface LinkInfoService {

    LinkInfoResponse createLinkInfo(CreateShortLinkRequest request);

    LinkInfo getByShortLink(String shortLink);

    void deleteById(UUID id);

    List<LinkInfoResponse> findByFilter(FilterLinkInfoRequest request);

    LinkInfoResponse updateByShortLink(CreateShortLinkRequest request);

}
