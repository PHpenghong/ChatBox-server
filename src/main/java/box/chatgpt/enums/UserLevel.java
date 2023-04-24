package box.chatgpt.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum UserLevel {
    REGULAR(1, "普通会员");

    private final Integer val;

    private final String name;

    public static UserLevel of(Integer val) {
        for (UserLevel level : UserLevel.values()) {
            if (Objects.equals(level.getVal(), val)){
                return level;
            }
        }
        return null;
    }
}
