package box.chatgpt.ohs.req.user;

import box.chatgpt.model.UserInfo;
import box.chatgpt.util.CommonUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationReq {

    @Pattern(regexp = CommonUtil.PHONE_REGEXP, message = "手机号码格式不对")
    private String phone;

    @Pattern(regexp = CommonUtil.EMAIL_REGEXP, message = "邮箱号码格式不对")
    private String email;

    @NotBlank(message = "登陆密码不能为空")
    private String password;

    @NotBlank(message = "用户昵称不能为空")
    private String name;

    public UserInfo registration() {
        return UserInfo.registration(this.name, this.email, this.phone, this.password);
    }
}
