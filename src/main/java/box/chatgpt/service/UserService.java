package box.chatgpt.service;

import box.chatgpt.enums.LoginType;
import box.chatgpt.exception.BusinessException;
import box.chatgpt.model.LoginInfo;
import box.chatgpt.model.SignRecord;
import box.chatgpt.model.UserInfo;
import box.chatgpt.repository.UserRepository;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.crypto.digest.DigestUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    /**
     * 用户注册
     *
     * @param userInfo 用户信息
     * @return 登陆令牌 token
     */
    public LoginInfo registration(UserInfo userInfo) {
        LoginType loginType = LoginType.math(userInfo.getPhone());
        loginType = (null != loginType) ? loginType : LoginType.math(userInfo.getEmail());
        if (null == loginType) {
            throw new BusinessException("登陆用户信息（手机号/邮箱）不能全部为空～");
        }

        if (!repository.registration(userInfo)) {
            throw new BusinessException("该用户信息已被注册～");
        }

        LoginInfo loginInfo = LoginInfo.login(loginType, userInfo.getId());
        return repository.login(loginInfo);
    }

    /**
     * 用户登陆
     *
     * @param userName 登陆信息（手机号或邮箱）
     * @param password 登陆密码
     * @return token
     */
    public LoginInfo login(String userName, String password) {
        LoginType loginType = LoginType.math(userName);
        if (null == loginType) {
            throw new BusinessException("用户名或密码错误");
        }

        String ciphertext = DigestUtil.digester("sm3").digestHex(password);
        UserInfo userInfo = LoginType.PHONE.equals(loginType) ?
                repository.find(userName, null, ciphertext) :
                repository.find(null, userName, ciphertext);
        if (null == userInfo) {
            throw new BusinessException("用户名或密码错误");
        }

        LoginInfo loginInfo = LoginInfo.login(loginType, userInfo.getId());
        return repository.login(loginInfo);
    }

    /**
     * 根据登陆令牌获取用户信息
     *
     * @param token 用户登陆令牌
     * @return UserInfo
     */
    public UserInfo info(String token) {
        LoginInfo loginInfo = LoginInfo.parse(token);
        UserInfo userInfo = repository.findById(loginInfo.getUserId());
        if (ObjUtil.isEmpty(userInfo)) {
            throw new BusinessException("用户令牌错误");
        }
        return userInfo;
    }

    /**
     * 登陆令牌续约
     *
     * @param token 登陆令牌
     * @return token
     */
    public LoginInfo renewal(String token) {
        LoginInfo loginInfo = LoginInfo.parse(token).renewal();
        return repository.login(loginInfo);
    }

    /**
     * 注销登陆
     *
     * @param token 登陆令牌
     * @return 是否注销
     */
    public boolean logout(String token) {
        return repository.login(LoginInfo.parse(token).logout()).getIsLogout();
    }

    /**
     * 用户签到
     *
     * @param token 登陆令牌
     * @return 是否签到
     */
    public boolean sign(String token) {
        LoginInfo loginInfo = LoginInfo.parse(token);
        SignRecord record = SignRecord.sign(loginInfo);
        return repository.sign(record);
    }
}