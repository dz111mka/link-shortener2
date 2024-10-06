package ru.chepikov.linkshortener.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLinkInfoRequest {

    @NotNull(message = "Id не должен быть пустым")
    UUID id;
    @NotEmpty(message = "Ссылка не должна быть пустой")
    @Size(min = 10, max = 4096, message = "Длинна ссылки должна быть от 10 до 4096 символов")
    @Pattern(regexp = "https?://.+\\..+", message = "Ссылка должна соответствовать шаблону URL")
    String link;
    @Future(message = "Дата экспирации должна быть в будущем")
    ZonedDateTime endTime;
    @NotEmpty(message = "Описание не должно быть пустым")
    String description;
    @NotNull(message = "Поле активности не должно быть пустым")
    Boolean active;
}
