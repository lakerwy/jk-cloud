package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;


/**
 * AES加解密工具类
 */
public class AesUtil {

    /**
     * AES KEY
     */
    private static final String AES_KEY = "AES_@GZ*996#666@LJ";

    /**
     * AES 加密
     * @param content 待加密内容
     * @return 加密后密文
     */
    public static String encrypt(String content) {
        if (ObjectUtil.isEmpty(content)) {
            return content;
        }
        try{
            // 生成16字节密钥
            byte[] keyBytes = SecureUtil.generateKey(AES_KEY, 16).getEncoded();
            // 创建AES对象，也可以直接使用密钥字符串
            AES aes = SecureUtil.aes(keyBytes);
            // 加密
            String encryptResult = aes.encryptHex(content);
            return encryptResult;
        }catch (Exception ex){
            return content;
        }
    }

    /**
     * AES 解密
     * @param encryptStr 待解密Hex串
     * @return 解密后明文
     */
    public static String decrypt(String encryptStr){
        if(ObjectUtil.isEmpty(encryptStr)){
            return encryptStr;
        }
        try {
            // 生成16字节密钥
            byte[] keyBytes = SecureUtil.generateKey(AES_KEY, 16).getEncoded();
            // 创建AES对象，也可以直接使用密钥字符串
            AES aes = SecureUtil.aes(keyBytes);
            // 解密
            String decryptResult = aes.decryptStr(encryptStr);
            return decryptResult;
        }catch (Exception ex){
            return encryptStr;
        }
    }


}
