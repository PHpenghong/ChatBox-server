package box.chatgpt.config.conver;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.time.LocalDate;

@Converter
public class LocalDateConverter implements AttributeConverter<LocalDate, Long> {
    @Override
    public Long convertToDatabaseColumn(LocalDate localDate) {
        if (null != localDate) {
            return localDate.toEpochDay();
        }
        return null;
    }

    @Override
    public LocalDate convertToEntityAttribute(Long epochDay) {
        if (null != epochDay) {
            return LocalDate.ofEpochDay(epochDay);
        }
        return null;
    }
}
