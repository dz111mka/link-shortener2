package ru.chepikov.linkshortener.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import ru.chepikov.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.chepikov.linkshortener.dto.CreateShortLinkRequest;
import ru.chepikov.linkshortener.dto.FilterLinkInfoRequest;
import ru.chepikov.linkshortener.dto.LinkInfoResponse;
import ru.chepikov.linkshortener.dto.UpdateLinkInfoRequest;
import ru.chepikov.linkshortener.exception.NotFoundException;
import ru.chepikov.linkshortener.mapper.LinkInfoMapper;
import ru.chepikov.linkshortener.model.LinkInfo;
import ru.chepikov.linkshortener.property.LinkShortenerProperty;
import ru.chepikov.linkshortener.repository.LinkInfoRepository;
import ru.chepikov.linkshortener.service.LinkInfoService;

import java.util.List;
import java.util.UUID;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class LinkInfoServiceImpl implements LinkInfoService {

    LinkInfoRepository repository;

    LinkShortenerProperty property;

    LinkInfoMapper mapper;

    @LogExecutionTime
    public LinkInfoResponse createLinkInfo(CreateShortLinkRequest request) {
        LinkInfo linkInfo = mapper.fromCreateRequest(request);

        linkInfo.setShortLink(RandomStringUtils.randomAlphanumeric(property.getShortLinkLength()));
        linkInfo.setOpeningCount(0L);

        repository.save(linkInfo);

        return mapper.toResponse(linkInfo);
    }

    @LogExecutionTime
    public LinkInfo getByShortLink(String shortLink) {
        LinkInfo linkInfo = repository.findByShortLinkAndActiveTrue(shortLink)
                .orElseThrow(() -> new NotFoundException("Не удалось найти длинную ссылку по короткой " + shortLink));

        repository.incrementOpeningCountByShortLink(shortLink);
        return linkInfo;
    }

    @Override
    public LinkInfoResponse updateById(UpdateLinkInfoRequest request) {
        LinkInfo toUpdate = repository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(
                        "LinkInfo с id {%s} не был найден".formatted(request.getId())));

        if (request.getLink() != null) {
            toUpdate.setLink(request.getLink());
        }

        if (request.getActive() != null) {
            toUpdate.setActive(request.getActive());
        }

        if (request.getDescription() != null) {
            toUpdate.setDescription(request.getDescription());
        }

        if (request.getEndTime() != null) {
            toUpdate.setEndTime(request.getEndTime());
        }
        repository.save(toUpdate);

        return mapper.toResponse(toUpdate);
    }

    @LogExecutionTime
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }

    @LogExecutionTime
    public List<LinkInfoResponse> findByFilter(FilterLinkInfoRequest filterRequest) {

        return repository.findByFilter(
                        filterRequest.getLinkPart(),
                        filterRequest.getEndTimeFrom(),
                        filterRequest.getEndTimeTo(),
                        filterRequest.getDescriptionPart(),
                        filterRequest.getActive())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
