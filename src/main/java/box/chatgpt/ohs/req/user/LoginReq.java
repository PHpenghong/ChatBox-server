package box.chatgpt.ohs.req.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReq {

    @NotBlank(message = "登陆账户不能为空")
    private String userName;

    @NotBlank(message = "登陆密码不能为空")
    private String password;
}
