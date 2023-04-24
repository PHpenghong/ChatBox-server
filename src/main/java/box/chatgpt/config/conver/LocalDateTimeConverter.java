package box.chatgpt.config.conver;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Converter
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Long> {
    @Override
    public Long convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (null != localDateTime){
            return localDateTime.toEpochSecond(ZoneOffset.ofHours(8));
        }
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Long timestamp) {
        if (timestamp != null){
            return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.ofHours(8));
        }
        return null;
    }
}
