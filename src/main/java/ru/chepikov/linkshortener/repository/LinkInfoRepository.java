package ru.chepikov.linkshortener.repository;

import ru.chepikov.linkshortener.model.LinkInfo;

import java.util.List;
import java.util.Optional;

public interface LinkInfoRepository {

    Optional<LinkInfo> findByShortLink(String shortLink);

    LinkInfo saveShortLink(LinkInfo linkInfo);

    void deleteShortLinkById(String id);

    List<LinkInfo> findAllShortLink();
}
