package box.chatgpt.model;

import box.chatgpt.config.conver.LocalDateTimeConverter;
import box.chatgpt.config.conver.UserLevelConverter;
import box.chatgpt.enums.UserLevel;
import cn.hutool.crypto.digest.DigestUtil;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_email")
    private String email;

    @Column(name = "login_password")
    private String ciphertext;

    @Convert(converter = UserLevelConverter.class)
    @Column(name = "membership_level")
    private UserLevel level;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "registered_time")
    private LocalDateTime registeredTime;

    /**
     * 用户信息注册
     *
     * @param name     用户名称
     * @param email    邮箱号码
     * @param phone    手机号码
     * @param password 登陆密码
     * @return {@link UserInfo}
     */
    public static UserInfo registration(String name, String email, String phone, String password) {
        UserInfo user = new UserInfo();
        user.setPhone(phone);
        user.setEmail(email);
        user.setCiphertext(DigestUtil.digester("sm3").digestHex(password));
        user.setRegisteredTime(LocalDateTime.now());
        user.setName(name);
        return user;
    }
}
