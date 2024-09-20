package ru.chepikov.linkshortener.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chepikov.linkshortener.model.LinkInfo;
import ru.chepikov.linkshortener.service.LinkInfoService;

@Slf4j
@RestController
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LinkShortenerController {

    LinkInfoService linkInfoService;

    @GetMapping("/short-link/{shortLink}")
    public ResponseEntity<String> getByShortLink(@PathVariable String shortLink) {
        log.info("Поступил запрос на открытие длинной ссылки по короткой {}", shortLink);
        LinkInfo linkInfo = linkInfoService.getByShortLink(shortLink);

        return ResponseEntity.status(HttpStatus.MOVED_TEMPORARILY)
                .header(HttpHeaders.LOCATION, linkInfo.getLink())
                .build();
    }
}
