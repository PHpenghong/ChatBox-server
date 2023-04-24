package box.chatgpt.enums;

import cn.hutool.core.util.ReUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum LoginType {

    EMAIL(1, "email"),
    PHONE(2, "phone");

    private final Integer val;

    private final String name;

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(.[a-zA-Z0-9_-]+)+$";

    private static final String PHONE_REGEX = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    /**
     * 正则匹配用户登陆类型
     *
     * @param str 手机号/邮箱号
     * @return {@link LoginType}
     */
    public static LoginType math(String str) {
        if (ReUtil.isMatch(EMAIL_REGEX, str)) {
            return LoginType.EMAIL;
        }

        if (ReUtil.isMatch(PHONE_REGEX, str)) {
            return LoginType.PHONE;
        }

        return null;
    }


    public static LoginType of(Integer val) {
        for (LoginType loginType : LoginType.values()) {
            if (Objects.equals(loginType.getVal(), val)){
                return loginType;
            }
        }

        return null;
    }
}
