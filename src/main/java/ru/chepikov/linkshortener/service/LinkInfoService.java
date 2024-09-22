package ru.chepikov.linkshortener.service;

import ru.chepikov.linkshortener.dto.CreateShortLinkRequest;
import ru.chepikov.linkshortener.dto.CreateShortLinkResponse;
import ru.chepikov.linkshortener.model.LinkInfo;

import java.util.List;

public interface LinkInfoService {

    CreateShortLinkResponse createLinkInfo(CreateShortLinkRequest request);

    LinkInfo getByShortLink(String shortLink);

    void deleteById(String id);

    List<CreateShortLinkResponse> findAll();
}
