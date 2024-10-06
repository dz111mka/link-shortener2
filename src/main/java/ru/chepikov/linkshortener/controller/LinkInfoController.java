package ru.chepikov.linkshortener.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.chepikov.linkshortener.dto.CreateShortLinkRequest;
import ru.chepikov.linkshortener.dto.FilterLinkInfoRequest;
import ru.chepikov.linkshortener.dto.LinkInfoResponse;
import ru.chepikov.linkshortener.dto.UpdateLinkInfoRequest;
import ru.chepikov.linkshortener.dto.common.CommonRequest;
import ru.chepikov.linkshortener.dto.common.CommonResponse;
import ru.chepikov.linkshortener.service.LinkInfoService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/link-infos")
@RequiredArgsConstructor
public class LinkInfoController {

    LinkInfoService service;

    @PostMapping("/")
    public CommonResponse<LinkInfoResponse> postCreateShortLink(
            @RequestBody @Valid CommonRequest<CreateShortLinkRequest> request) {
        log.info("Поступил запрос на создание короткой ссылки: {}", request);

        LinkInfoResponse createShortLinkResponse = service.createLinkInfo(request.getBody());

        return CommonResponse.<LinkInfoResponse>builder()
                .body(createShortLinkResponse)
                .build();
    }

    @PostMapping("/filter")
    public CommonResponse<List<LinkInfoResponse>> filter(@RequestBody @Valid CommonRequest<FilterLinkInfoRequest> request) {
        log.info("Поступил запрос на получение всех имеющихся коротких ссылок");

        return CommonResponse.<List<LinkInfoResponse>>builder()
                .body(service.findByFilter(request.getBody()))
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShortLinkById(@PathVariable UUID id) {
        log.info("Поступил запрос на удаление короткой ссылки по id: {}", id);
        service.deleteById(id);
    }

    @PatchMapping
    public CommonResponse<LinkInfoResponse> update(
            @RequestBody @Valid CommonRequest<UpdateLinkInfoRequest> request) {
        LinkInfoResponse linkInfoResponses = service.updateById(request.getBody());

        return CommonResponse.<LinkInfoResponse>builder()
                .body(linkInfoResponses)
                .build();
    }
}
