package ru.chepikov.linkshortener.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class CreateShortLinkRequest {

    @NotEmpty(message = "Ссылка не может быть пустой")
    @Size(min = 10, max = 2048, message = "Размер ссылки должен быть от 10 до 2048 символов")
    @Pattern(regexp = "https?://.+\\..*", message = "Ссылка должна соответствовать шаблону Url")
    String link;

    @Future(message = "Время окончания ссылки должна быть в будущем времени")
    ZonedDateTime endTime;

    @NotEmpty(message = "Описание ссылки не должно быть пустым")
    String description;

    @NotNull(message = "Поле активности не должно быть пустым")
    Boolean active;
}
