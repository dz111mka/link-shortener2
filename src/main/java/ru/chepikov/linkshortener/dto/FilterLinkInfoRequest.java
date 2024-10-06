package ru.chepikov.linkshortener.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FilterLinkInfoRequest {
    UUID id;
    String linkPart;
    ZonedDateTime endTimeFrom;
    ZonedDateTime endTimeTo;
    String descriptionPart;
    Boolean active;
}
