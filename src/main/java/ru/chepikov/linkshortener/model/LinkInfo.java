package ru.chepikov.linkshortener.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class LinkInfo extends AuditableEntity {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
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
