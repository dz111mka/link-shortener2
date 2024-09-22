package ru.chepikov.linkshortener.repository.impl;

import org.springframework.stereotype.Repository;
import ru.chepikov.linkshortener.model.LinkInfo;
import ru.chepikov.linkshortener.repository.LinkInfoRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class LinkInfoRepositoryImpl implements LinkInfoRepository {

    Map<String, LinkInfo> storage = new ConcurrentHashMap<>();

    @Override
    public Optional<LinkInfo> findByShortLink(String shortLink) {
        return Optional.ofNullable(storage.get(shortLink));
    }

    @Override
    public LinkInfo saveShortLink(LinkInfo linkInfo) {
        linkInfo.setId(UUID.randomUUID());
        storage.put(linkInfo.getShortLink(), linkInfo);
        return linkInfo;
    }

    @Override
    public void deleteShortLinkById(String id) {
        for (Map.Entry<String, LinkInfo> it : storage.entrySet()) {
            if (id.equals(it.getValue().getId().toString())) {
                String key = it.getKey();
                storage.remove(key);
                return;
            }
        }
    }

    @Override
    public List<LinkInfo> findAllShortLink() {
        return storage.values().stream().toList();
    }
}
