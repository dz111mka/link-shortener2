package ru.chepikov.linkshortener.property;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@ConfigurationProperties(prefix = "link-shortener")
public class LinkShortenerProperty {

    int shortLinkLength;
}
