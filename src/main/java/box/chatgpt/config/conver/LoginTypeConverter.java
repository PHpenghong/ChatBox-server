package box.chatgpt.config.conver;

import box.chatgpt.enums.LoginType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class LoginTypeConverter implements AttributeConverter<LoginType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(LoginType loginType) {
        if (null != loginType) {
            return loginType.getVal();
        }
        return null;
    }

    @Override
    public LoginType convertToEntityAttribute(Integer loginType) {
        if (null != loginType) {
            return LoginType.of(loginType);
        }
        return null;
    }
}
