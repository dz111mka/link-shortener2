package ru.chepikov.linkshortener.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.chepikov.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.chepikov.linkshortener.dto.CreateShortLinkRequest;
import ru.chepikov.linkshortener.dto.CreateShortLinkResponse;
import ru.chepikov.linkshortener.exception.NotFoundException;
import ru.chepikov.linkshortener.mapper.LinkInfoMapper;
import ru.chepikov.linkshortener.model.LinkInfo;
import ru.chepikov.linkshortener.property.LinkShortenerProperty;
import ru.chepikov.linkshortener.repository.LinkInfoRepository;
import ru.chepikov.linkshortener.service.LinkInfoService;

import java.util.ArrayList;
import java.util.List;


@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    LinkInfoRepository repository;

    LinkShortenerProperty property;

    LinkInfoMapper mapper;

    @LogExecutionTime
    public CreateShortLinkResponse createLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = mapper.fromCreateRequest(request);

        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(property.getShortLinkLength()));
        linkInfo.setOpeningCount(0L);

        repository.saveShortLink(linkInfo);

        return mapper.fromLinkInfo(linkInfo);
    }

    @LogExecutionTime
    public LinkInfo getByShortLink(String shortLink) {
        return repository.findByShortLink(shortLink).orElseThrow(
                () -> new NotFoundException("Не найдена короткая ссылка")
        );
    }

    @LogExecutionTime
    public void deleteById(String id) {
        repository.deleteShortLinkById(id);
    }

    @LogExecutionTime
    public List<CreateShortLinkResponse> findAll() {
        List<LinkInfo> allShortLink = repository.findAllShortLink();
        List<CreateShortLinkResponse> result = new ArrayList<>();
        for (LinkInfo it : allShortLink) {
            result.add(mapper.fromLinkInfo(it));
        }
        return result;
    }
}
