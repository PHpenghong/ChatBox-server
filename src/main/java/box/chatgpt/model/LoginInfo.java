package box.chatgpt.model;

import box.chatgpt.config.CustomPropertyConfig;
import box.chatgpt.config.conver.LocalDateTimeConverter;
import box.chatgpt.config.conver.LoginTypeConverter;
import box.chatgpt.enums.LoginType;
import box.chatgpt.exception.BusinessException;
import box.chatgpt.util.CommonUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import jakarta.persistence.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "login_info")
public class LoginInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "login_time")
    private LocalDateTime loginTime;

    @Column(name = "login_type")
    @Convert(converter = LoginTypeConverter.class)
    private LoginType loginType;

    @Column(name = "login_ip")
    private String loginIp;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "expired_time")
    private LocalDateTime expiredTime;

    @Column(name = "is_expired", columnDefinition = "TINYINT")
    private Boolean isExpired;

    @Column(name = "is_logout", columnDefinition = "TINYINT")
    private Boolean isLogout;

    @Convert(converter = LocalDateTimeConverter.class)
    @Column(name = "logout_time")
    private LocalDateTime logoutTime;

    /**
     * 用户登陆
     *
     * @param userId    用户 ID
     * @param loginType 登陆类型
     * @return 登陆信息
     */
    public static LoginInfo login(LoginType loginType, Long userId) {
        LoginInfo login = new LoginInfo();
        String ip = CommonUtil.getIpAddr();
        login.setLoginIp(ip);
        login.setUserId(userId);
        login.setLoginType(loginType);
        LocalDateTime now = LocalDateTime.now();
        login.setLoginTime(now);
        login.setExpiredTime(now.plusHours(1));
        login.setIsExpired(Boolean.FALSE);
        login.setIsLogout(Boolean.FALSE);
        return login;
    }

    /**
     * 根据密钥生成登陆令牌
     *
     * @return token
     */
    public String token() {
        JSONConfig config = JSONConfig.create().setIgnoreNullValue(true);
        String json = JSONUtil.toJsonStr(this, config);
        return CommonUtil.sm4Encrypt(json, CustomPropertyConfig.instance().getKey());
    }


    /**
     * 用户登陆令牌解析
     *
     * @param token 用户登陆令牌
     * @return 用户登陆信息
     */
    public static LoginInfo parse(String token) {
        String json = CommonUtil.sm4Decrypt(token, CustomPropertyConfig.instance().getKey());
        LoginInfo loginInfo = JSONUtil.toBean(json, LoginInfo.class);
        if (ObjUtil.isEmpty(loginInfo.getUserId())) {
            throw new BusinessException("用户令牌错误");
        }

        if (loginInfo.isExpired()) {
            throw new BusinessException("登陆已过期，请重新登陆");
        }

        if (loginInfo.getIsLogout()) {
            throw new BusinessException("登陆已注销.");
        }

        return loginInfo;
    }

    /**
     * 判断当前登陆是否过期
     *
     * @return boolean
     */
    public boolean isExpired() {
        this.setIsExpired(this.expiredTime.isBefore(LocalDateTime.now()));
        return this.isExpired;
    }

    /**
     * 登陆令牌续订
     *
     * @return 下次过期时间
     */
    public LoginInfo renewal() {
        this.setIsExpired(Boolean.FALSE);
        this.setExpiredTime(LocalDateTime.now().plusHours(1));
        return this;
    }

    /**
     * 用户注销登陆
     *
     * @return 登陆信息
     */
    public LoginInfo logout() {
        this.setIsLogout(Boolean.TRUE);
        this.setLogoutTime(LocalDateTime.now());
        return this;
    }
}