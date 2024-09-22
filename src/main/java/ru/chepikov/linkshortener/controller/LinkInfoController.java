package ru.chepikov.linkshortener.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.chepikov.linkshortener.dto.CreateShortLinkRequest;
import ru.chepikov.linkshortener.dto.CreateShortLinkResponse;
import ru.chepikov.linkshortener.dto.common.CommonRequest;
import ru.chepikov.linkshortener.dto.common.CommonResponse;
import ru.chepikov.linkshortener.service.LinkInfoService;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1/link-infos")
@RequiredArgsConstructor
public class LinkInfoController {

    LinkInfoService service;

    @PostMapping("/")
    public CommonResponse<CreateShortLinkResponse> postCreateShortLink(
            @RequestBody @Valid CommonRequest<CreateShortLinkRequest> request) {
        log.info("Поступил запрос на создание короткой ссылки: {}", request);

        CreateShortLinkResponse createShortLinkResponse = service.createLinkInfo(request.getBody());

        return CommonResponse.<CreateShortLinkResponse>builder()
                .body(createShortLinkResponse)
                .build();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteShortLinkById(@PathVariable String id) {
        log.info("Поступил запрос на удаление короткой ссылки по id: {}", id);
        service.deleteById(id);
    }

    @GetMapping("/all")
    public CommonResponse<List<CreateShortLinkResponse>> getAll() {
        log.info("Поступил запрос на получение всех имеющихся коротких ссылок");

        return CommonResponse.<List<CreateShortLinkResponse>>builder()
                .body(service.findAll())
                .build();
    }
}
