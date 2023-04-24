package box.chatgpt.ohs.resp.user;

import box.chatgpt.model.LoginInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneOffset;

@Getter
@Setter
public class LoginResp {

    private String token;

    private Long expireTime;

    public static LoginResp of(LoginInfo loginInfo) {
        LoginResp resp = new LoginResp();
        resp.setToken(loginInfo.token());
        resp.setExpireTime(loginInfo.getExpiredTime().toEpochSecond(ZoneOffset.of("+8")));
        return resp;
    }
}
