package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import org.apache.poi.ss.formula.functions.T;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/**
 * AES加解密工具类
 */
public class AesUtil {

    /**
     * AES KEY
     */
    private static final String AES_KEY = "AES_@GZ*996#666@LJ";

    /**
     * 需要 加解密 的字段名 列表
     */
    private static final List<String> AES_CRYPT_LIST = new ArrayList<>();
    static {
        AES_CRYPT_LIST.add("idCardNumber");
        AES_CRYPT_LIST.add("userName");
        AES_CRYPT_LIST.add("firstOrgName");
        AES_CRYPT_LIST.add("orgName");
        AES_CRYPT_LIST.add("position");
    }

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


    /**
     * AES 对 实体 指定字段加密
     * @param entity 实体
     * @param columns 待加密的列
     * @return
     */
    public static T encrypt(T entity, List<String> columns){
        if (entity == null) {
            return null;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使字段可访问
                if(columns.contains(field.getName())){
                    field.set(entity,encrypt(field.get(entity).toString()));
                }
            }catch (Exception ex){

            }
        }
        return entity;
    }



    /**
     * AES 对 实体 指定字段解密
     * @param entity 实体
     * @param columns 待解密的列
     * @return
     */
    public static T decrypt(T entity, List<String> columns){
        if (entity == null) {
            return null;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使字段可访问
                if(columns.contains(field.getName())){
                    field.set(entity,decrypt(field.get(entity).toString()));
                }
            }catch (Exception ex){

            }
        }
        return entity;
    }

}
