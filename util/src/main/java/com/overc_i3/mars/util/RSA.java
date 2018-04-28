package com.overc_i3.mars.util;

import android.text.TextUtils;
import android.util.Base64;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Mars on 2017-10-21.
 */

public class RSA {
    /**
     * 密钥算法
     */
    private static final String KEY_ALGORIGTHM = "RSA";
    /**
     *
     */
    private static final String CIPHER_ALGORITHM_ECB1 = "RSA/ECB/PKCS1Padding";

    /**
     * 默认key长度
     */
    private static final int DEFAULT_KEY_SIZE = 512;

    /**
     * 公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] key) throws Exception {
        return asymmetricCryptionByPublicKey(data, key, Cipher.ENCRYPT_MODE);
    }

    public static String encryptByPublicKey(String dataString, String keyString) throws Exception {
        byte[] data = StringUtils.getBytes(dataString);
        byte[] key = toBytes(keyString);
        byte[] revData = asymmetricCryptionByPublicKey(data, key, Cipher.ENCRYPT_MODE);
        return toBase64String(revData);
    }

    /**
     * 私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        return asymmetricCryptionByPrivateKey(data, key, Cipher.ENCRYPT_MODE);
    }

    public static String encryptByPrivateKey(String dataString, String keyString) throws Exception {
        byte[] data = StringUtils.getBytes(dataString);
        byte[] key = toBytes(keyString);
        byte[] revData = asymmetricCryptionByPrivateKey(data, key, Cipher.ENCRYPT_MODE);
        return toBase64String(revData);
    }

    /**
     * 公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, byte[] key) throws Exception {
        return asymmetricCryptionByPublicKey(data, key, Cipher.DECRYPT_MODE);
    }

    public static String decryptByPublicKey(String dataString, String keyString) throws Exception {
        byte[] data = toBytes(dataString);
        byte[] key = toBytes(keyString);
        byte[] revData = asymmetricCryptionByPublicKey(data, key, Cipher.DECRYPT_MODE);
        return new String(revData);
    }

    /**
     * 私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] key) throws Exception {
        return asymmetricCryptionByPrivateKey(data, key, Cipher.DECRYPT_MODE);
    }

    public static String decryptByPrivateKey(String dataString, String keyString) throws Exception {
        byte[] data = toBytes(dataString);
        byte[] key = toBytes(keyString);
        byte[] revData = asymmetricCryptionByPrivateKey(data, key, Cipher.DECRYPT_MODE);
        return new String(revData);
    }

    /**
     *
     * @param data
     * @return
     */
    public static String toBase64String(byte[] data) {
        if (data != null) {
            return Base64.encodeToString(data, Base64.NO_WRAP);
        }
        return null;
    }

    /**
     *
     * @param keyStr
     * @return
     */
    public static byte[] toBytes(String keyStr) {
        if (!TextUtils.isEmpty(keyStr)) {
            return Base64.decode(keyStr,Base64.NO_WRAP);
        }
        return null;
    }

    /**
     * 公钥加密解密
     *
     * @param data
     * @param key
     * @param mode
     * @return
     * @throws Exception
     */
    private static byte[] asymmetricCryptionByPublicKey(byte[] data, byte[] key, int mode) throws Exception {
        // 取得公钥
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORIGTHM);
        // 生成公钥
        PublicKey pubKey = factory.generatePublic(new X509EncodedKeySpec(key));
        // 加密或解密数据
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB1);
        cipher.init(mode, pubKey);

        return cipher.doFinal(data);
    }

    /**
     * 私钥加密和解密
     *
     * @param data
     * @param key
     * @param mode
     * @return
     * @throws Exception
     */
    private static byte[] asymmetricCryptionByPrivateKey(byte[] data, byte[] key, int mode) throws Exception {
        // 取得 私钥
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORIGTHM);
        PrivateKey priKey = factory.generatePrivate(new PKCS8EncodedKeySpec(key));

        // 加密或解密数据
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB1);
        cipher.init(mode, priKey);

        return cipher.doFinal(data);
    }
}
