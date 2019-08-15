package com.github.ruifengho.simplesecurity.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Date;

public class DesUtil {
    // 定义加密方式
    private final static String DES = "DES";
    private final static String UTF8 = "UTF-8";
    static SecretKeyFactory keyFactory = null;

    static {
        try {
            keyFactory = SecretKeyFactory.getInstance("DES");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        long begin = new Date().getTime();
        String data = "aaades加密测试";
        // 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
        String key = "qazwsxed";
        System.err.println(encrypt(data, key));
        System.err.println(decrypt(encrypt(data, key), key));
        long end = new Date().getTime();
        System.out.println(end - begin);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) throws Exception {
        // 使用指定的编码获取要加密的内容，一般秘钥都是字母或数字不用指定编码，但指定也可以
        byte[] bt = encrypt(data.getBytes(UTF8), key.getBytes());
        // 第一个使用了sun.misc.BASE64Encoder;进行了编码，但网上说使用org.apache.commons.codec.binary.Base64比较好所以拿来试试
        String strs = Base64.encodeBase64String(bt);
        return strs;
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static String decrypt(String data, String key) throws IOException,
            Exception {
        if (data == null)
            return null;
        // 使用org.apache.commons.codec.binary.Base64解码
        byte[] buf = Base64.decodeBase64(data);
        byte[] bt = decrypt(buf, key.getBytes());
        return new String(bt, UTF8);
    }

    /**
     * Description 根据键值进行加密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象，也就是创建秘钥的秘钥内容
        DESKeySpec dks = new DESKeySpec(key);
        // 密钥工厂用来将密钥（类型 Key 的不透明加密密钥）转换为密钥规范（底层密钥材料的透明表示形式），反之亦然。秘密密钥工厂只对秘密（对称）密钥进行操作。
        // 这里改为使用单例模式
        //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        //根据提供的密钥规范（密钥材料）生成 SecretKey(秘钥) 对象。
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher对象实际完成加密操作,此类为加密和解密提供密码功能
        Cipher cipher = Cipher.getInstance(DES);
        // 用密钥和随机源初始化此 Cipher。ENCRYPT_MODE用于将 Cipher 初始化为加密模式的常量。
        cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
        //正式执行加密操作
        return cipher.doFinal(data);
    }

    /**
     * Description 根据键值进行解密
     *
     * @param data
     * @param key  加密键byte数组
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();
        // 从原始密钥数据创建DESKeySpec对象，也就是创建秘钥的秘钥内容
        DESKeySpec dks = new DESKeySpec(key);
        // 密钥工厂用来将密钥（类型 Key 的不透明加密密钥）转换为密钥规范（底层密钥材料的透明表示形式），反之亦然。秘密密钥工厂只对秘密（对称）密钥进行操作。
        // 这里改为使用单例模式
        //SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        //根据提供的密钥规范（密钥材料）生成 SecretKey(秘钥)对象。
        SecretKey securekey = keyFactory.generateSecret(dks);
        // Cipher类为加密和解密提供密码功能
        Cipher cipher = Cipher.getInstance(DES);
        // DECRYPT_MODE用于将 Cipher 初始化为解密模式的常量。
        cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
        // 正式进行解密操作
        return cipher.doFinal(data);
    }
}
