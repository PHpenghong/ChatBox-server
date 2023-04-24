package box.chatgpt.application;

import box.chatgpt.model.LoginInfo;
import box.chatgpt.model.UserInfo;
import box.chatgpt.ohs.req.user.LoginReq;
import box.chatgpt.ohs.req.user.RegistrationReq;
import box.chatgpt.ohs.resp.user.LoginResp;
import box.chatgpt.ohs.resp.user.UserResp;
import box.chatgpt.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserApplication {

    private final UserService userService;

    /**
     * 用户注册
     *
     * @param req 注册信息请求
     * @return {@link LoginResp}
     */
    public LoginResp registration(RegistrationReq req) {
        LoginInfo loginInfo = userService.registration(req.registration());
        return LoginResp.of(loginInfo);
    }

    public LoginResp login(LoginReq req) {
        LoginInfo loginInfo = userService.login(req.getUserName(), req.getPassword());
        return LoginResp.of(loginInfo);
    }

    public UserResp info(String token) {
        UserInfo info = userService.info(token);
        return UserResp.of(info);
    }

    public LoginResp renewal(String token) {
        LoginInfo loginInfo = userService.renewal(token);
        return LoginResp.of(loginInfo);
    }

    public boolean logout(String token) {
        return userService.logout(token);
    }

    public boolean sign(String token) {
        return userService.sign(token);
    }
}