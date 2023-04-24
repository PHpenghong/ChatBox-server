package box.chatgpt.config.conver;

import box.chatgpt.enums.UserLevel;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class UserLevelConverter implements AttributeConverter<UserLevel, Integer> {
    @Override
    public Integer convertToDatabaseColumn(UserLevel userLevel) {
        if (null != userLevel) {
            return userLevel.getVal();
        }

        return null;
    }

    @Override
    public UserLevel convertToEntityAttribute(Integer userLevel) {
        if (null != userLevel) {
            return UserLevel.of(userLevel);
        }

        return null;
    }
}
