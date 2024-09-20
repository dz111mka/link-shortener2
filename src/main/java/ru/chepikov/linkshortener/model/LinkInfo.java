package ru.chepikov.linkshortener.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class LinkInfo {

    UUID id;
    String link;
    ZonedDateTime endTime;
    String description;
    Boolean active;
    String shortLink;
    long openingCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkInfo linkInfo = (LinkInfo) o;
        return id.equals(linkInfo.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "LinkInfo{" +
                "id=" + id +
                ", link='" + link + '\'' +
                ", endTime=" + endTime +
                ", description='" + description + '\'' +
                ", active=" + active +
                ", shortLink='" + shortLink + '\'' +
                ", openingCount=" + openingCount +
                '}';
    }
}
