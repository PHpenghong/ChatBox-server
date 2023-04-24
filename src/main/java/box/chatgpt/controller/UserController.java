package box.chatgpt.controller;

import box.chatgpt.application.UserApplication;
import box.chatgpt.ohs.ResulBody;
import box.chatgpt.ohs.req.user.LoginReq;
import box.chatgpt.ohs.req.user.RegistrationReq;
import box.chatgpt.ohs.resp.user.LoginResp;
import box.chatgpt.ohs.resp.user.UserResp;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserApplication userApplication;

    @PostMapping("/registration")
    public ResulBody<LoginResp> registration(@Validated @RequestBody RegistrationReq registrationReq) {
        LoginResp resp = userApplication.registration(registrationReq);
        return ResulBody.success(resp);
    }

    @PostMapping("/login")
    public ResulBody<LoginResp> login(@Validated @RequestBody LoginReq loginReq) {
        LoginResp resp = userApplication.login(loginReq);
        return ResulBody.success(resp);
    }

    @PostMapping("/logout")
    public ResulBody<Boolean> logout(@RequestHeader(value = "token") String token) {
        return ResulBody.success(userApplication.logout(token));
    }

    @GetMapping("/renewal")
    public ResulBody<LoginResp> renewal(@RequestHeader(value = "token") String token) {
        LoginResp resp = userApplication.renewal(token);
        return ResulBody.success(resp);
    }

    @GetMapping("/info")
    public ResulBody<UserResp> userInfo(@RequestHeader(value = "token") String token) {
        UserResp resp = userApplication.info(token);
        return ResulBody.success(resp);
    }

    @PostMapping("/sign")
    public ResulBody<Boolean> sign(@RequestHeader(value = "token") String token) {
        return ResulBody.success(userApplication.sign(token));
    }
}
