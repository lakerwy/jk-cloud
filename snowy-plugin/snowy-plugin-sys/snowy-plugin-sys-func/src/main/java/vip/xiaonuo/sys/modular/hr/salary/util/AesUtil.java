package vip.xiaonuo.sys.modular.hr.salary.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


/**
 * AES加解密工具类
 */
public class AesUtil {

    /**
     * AES KEY
     */
    private static final String AES_KEY = "@GZ*9@LJjianke66";

    /**
     * 【工资模块】需要 加解密 的字段名 列表
     */
    private static final List<String> AES_CRYPT_LIST = new ArrayList<>();
    static {
        AES_CRYPT_LIST.add("idCardNumber");
        AES_CRYPT_LIST.add("userName");
        AES_CRYPT_LIST.add("firstOrgName");
        AES_CRYPT_LIST.add("orgName");
        AES_CRYPT_LIST.add("position");
        AES_CRYPT_LIST.add("userId");
        AES_CRYPT_LIST.add("personnelId");
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
            byte[] keyBytes = AES_KEY.getBytes(StandardCharsets.UTF_8);
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
//            byte[] keyBytes = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(), 16).getEncoded();
            byte[] keyBytes = AES_KEY.getBytes(StandardCharsets.UTF_8);
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
    public static <T> T encrypt(T entity, List<String> columns){
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
    public static <T> T decrypt(T entity, List<String> columns){
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






    /**
     * 对 指定集合 特定字段 解密 【工资模块：解密字段已固定】
     * @param entityList 待解密实体集合
     * @return
     */
    public static <T> List<T> decryptGZList(List<T> entityList){
        if(CollUtil.isEmpty(entityList)){
            return entityList;
        }
        List<T> newEntityList = new ArrayList<>();
        entityList.forEach(item->{
            newEntityList.add(decryptGZ(item));
        });
        return newEntityList;
    }


    /**
     * AES 对 实体 指定字段解密 【工资模块：解密字段已固定】
     * @param entity 实体
     * @return
     */
    public static <T> T decryptGZ(T entity){
        if (entity == null) {
            return null;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使字段可访问
                if(AES_CRYPT_LIST.contains(field.getName())){
                    field.set(entity,decrypt(field.get(entity).toString()));
                }
            }catch (Exception ex){
            }
        }
        return entity;
    }



    /**
     * 对 指定集合 特定字段 加密 【工资模块：加密字段已固定】
     * @param entityList 待加密实体集合
     * @return
     */
    public static <T> List<T> encryptGZList(List<T> entityList){
        if(CollUtil.isEmpty(entityList)){
            return entityList;
        }
        List<T> newEntityList = new ArrayList<>();
        entityList.forEach(item->{
            newEntityList.add(encryptGZ(item));
        });
        return newEntityList;
    }


    /**
     * AES 对 实体 指定字段加密 【工资模块：加密字段已固定】
     * @param entity 实体
     * @return
     */
    public static <T> T encryptGZ(T entity){
        if (entity == null) {
            return null;
        }
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true); // 使字段可访问
                if(AES_CRYPT_LIST.contains(field.getName())){
                    field.set(entity,encrypt(field.get(entity).toString()));
                }
            }catch (Exception ex){
            }
        }
        return entity;
    }

}
