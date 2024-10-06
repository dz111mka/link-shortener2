package ru.chepikov.linkshortener.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class IdRequest {

    @NotNull(message = "Id не должен быть пустым")
    UUID id;
}
