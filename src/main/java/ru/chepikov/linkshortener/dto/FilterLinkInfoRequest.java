package ru.chepikov.linkshortener.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class FilterLinkInfoRequest {

    String linkPart;
    ZonedDateTime endTimeFrom;
    ZonedDateTime endTimeTo;
    String descriptionPart;
    Boolean active;
    PageableRequest page;
}
