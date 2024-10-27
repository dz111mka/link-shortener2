package ru.chepikov.linkshortener.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.chepikov.linkshortener.beanpostprocessor.LogExecutionTime;
import ru.chepikov.linkshortener.dto.*;
import ru.chepikov.linkshortener.exception.NotFoundException;
import ru.chepikov.linkshortener.exception.NotFoundPageException;
import ru.chepikov.linkshortener.mapper.LinkInfoMapper;
import ru.chepikov.linkshortener.model.LinkInfo;
import ru.chepikov.linkshortener.property.LinkShortenerProperty;
import ru.chepikov.linkshortener.repository.LinkInfoRepository;
import ru.chepikov.linkshortener.service.LinkInfoService;

import java.time.ZonedDateTime;
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
        LinkInfo linkInfo = repository.findByShortLinkAndActiveTrueAndEndTimeIsAfter(shortLink, ZonedDateTime.now())
                .orElseThrow(() -> new NotFoundPageException("LinkInfo по короткой ссылкой " + shortLink + " не был найден"));

        repository.incrementOpeningCountByShortLink(shortLink);

        return linkInfo;
    }

    @LogExecutionTime
    public LinkInfoResponse updateById(UpdateLinkInfoRequest request) {
        LinkInfo toUpdate = repository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(
                        "LinkInfo с id %s не был найден".formatted(request.getId())));

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
        Pageable pageable = createPageable(filterRequest);

        return repository.findByFilter(
                        filterRequest.getLinkPart(),
                        filterRequest.getEndTimeFrom(),
                        filterRequest.getEndTimeTo(),
                        filterRequest.getDescriptionPart(),
                        filterRequest.getActive(),
                        pageable).stream()
                .map(mapper::toResponse)
                .toList();
    }

    private static Pageable createPageable(FilterLinkInfoRequest filterRequest) {
        PageableRequest page = filterRequest.getPage();

        List<Sort.Order> orders = page.getSorts().stream()
                .map(sort -> new Sort.Order(Sort.Direction.fromString(sort.getDirection()), sort.getField()))
                .toList();

        Sort sort = CollectionUtils.isEmpty(orders)
                ? Sort.unsorted()
                : Sort.by(orders);

        return PageRequest.of(page.getNumber() - 1, page.getSize(), sort);
    }
}
