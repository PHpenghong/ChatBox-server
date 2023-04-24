package box.chatgpt.ohs.resp.user;

import box.chatgpt.model.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResp {

    private String name;

    private String phone;

    private String email;

    public static UserResp of(UserInfo userInfo) {
        UserResp resp = new UserResp();
        resp.setName(userInfo.getName());
        resp.setPhone(userInfo.getPhone());
        resp.setEmail(userInfo.getEmail());
        return resp;
    }
}
