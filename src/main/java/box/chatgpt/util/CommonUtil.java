package box.chatgpt.util;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.symmetric.SM4;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@UtilityClass
public class CommonUtil {

    public static final String EMAIL_REGEXP = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

    public static final String PHONE_REGEXP = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$";

    /**
     * 国密 SM4 算法加密
     *
     * @param plainText 纯文本信息
     * @param key       密钥
     * @return 加密后字符串
     */
    public String sm4Encrypt(String plainText, String key) {
        byte[] encrypted = sm4(key).encrypt(plainText.getBytes());
        return HexUtil.encodeHexStr(encrypted);
    }

    /**
     * 国密 SM4 算法解密
     *
     * @param secretText 密文信息
     * @param key        密钥
     * @return 解密后字符串
     */
    public String sm4Decrypt(String secretText, String key) {
        byte[] encrypted = HexUtil.decodeHex(secretText);
        byte[] decrypted = sm4(key).decrypt(encrypted);
        return new String(decrypted);
    }

    /**
     * 根据字符串生成 SM4 算法对象
     *
     * @param input 字符串 key
     * @return {@link SM4}
     */
    private SM4 sm4(String input) {
        return SmUtil.sm4(Arrays.copyOf(SecureUtil.md5(input).getBytes(), 16));
    }

    /**
     * 获取请求 ip 地址
     *
     * @return ip address
     */
    public String getIpAddr() {
        HttpServletRequest request = ((ServletRequestAttributes)
                (RequestContextHolder.currentRequestAttributes())).getRequest();
        return request.getRemoteAddr();
    }

}
