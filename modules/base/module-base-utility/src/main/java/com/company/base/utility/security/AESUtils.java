package com.company.base.utility.security;

import java.security.Provider;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密工具
 * <p>
 * 此工具类中参数只针对得威电商后台
 * <p>
 * 规则：
 * 加密：AES -- Base64 -- replaceAll
 * 解密：replaceAll -- Bae64 -- AES
 *
 * @author xuhj
 */
public class AESUtils {
    private static final String TAG = "AESUtils";

    // 偏移量
    private static final String VIPARA = "1269571569321021";
    // 字符编码
    private static final String UTF_8 = "utf-8";
    // 加密解密模式【算法/模式/补码方式】
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    // keysize
    private static final int KEYSIZE = 128;
    // AES密钥
    public static final String AES_KEY = "DWERP@#12$3458ta";

    /**
     * 初始化 AES Cipher
     *
     * @param key 生成秘钥的关键字
     * @return
     */
    private static Cipher initAESCipher(String key, boolean isEncode) throws Exception {
        Cipher cipher = null;
        // create iv
        IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
        // SecretKeySpec
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(), "AES");
        // Cipher
        cipher = Cipher.getInstance(TRANSFORMATION);
        if (isEncode) {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, zeroIv);
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keySpec, zeroIv);
        }
        return cipher;
    }

    /**
     * AES 加密
     *
     * @param content 明文
     * @param key     生成秘钥的关键字
     */

    public static String encrypt(String content, String key) {
        try {
            Cipher cipher = initAESCipher(key, true);
            /*
                加密顺序，解密顺序相反
             */
            // dofinal
            byte[] encryptedData = cipher.doFinal(content.getBytes(UTF_8));
            // base64 encode
            String baseStr = Base64.encode(encryptedData);
            // replace char
            return baseStr.replaceAll("=", "_");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 解密
     *
     * @param content 密文
     * @param key     生成秘钥的关键字
     */

    public static String decrypt(String content, String key) {
        try {
            Cipher cipher = initAESCipher(key, false);
            /*
                解密顺序，与加密顺序相反
             */
            // replace char
            content = content.replaceAll("_", "=");
            // base64 decode
            byte[] byteMi = Base64.decode(content);
            // dofinal
            byte[] decryptedData = cipher.doFinal(byteMi);
            return new String(decryptedData, UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建原始的key
     *
     * @param key 生成秘钥的关键字
     * @return
     * @throws Exception
     */
    private byte[] getRawKey(byte[] key) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        //256 bits or 128 bits,192bits
        kgen.init(KEYSIZE, getSecureRandom(key));
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
    }

    /**
     * 获取随机安全对象
     * <p>
     * 兼容Android各种版本
     *
     * @param key 生成秘钥的关键字
     * @return
     * @throws Exception
     */
    private SecureRandom getSecureRandom(byte[] key) throws Exception {
        // SHA1PRNG 强随机种子算法, 要区别4.2以上版本的调用方法
        SecureRandom sr = null;
        // 在4.2以上版本中，SecureRandom获取方式发生了改变
        int sdk_version = android.os.Build.VERSION.SDK_INT;
        if (sdk_version > 23) {  // Android  6.0 以上
            sr = SecureRandom.getInstance("SHA1PRNG", new CryptoProvider());
        } else if (sdk_version >= 17) {
            sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
        } else {
            sr = SecureRandom.getInstance("SHA1PRNG");
        }
        sr.setSeed(key);
        return sr;
    }

    /**
     * 字节数组转化为大写16进制字符串
     *
     * @param b
     * @return
     */
    private String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }

            sb.append(s.toUpperCase());
        }

        return sb.toString();
    }

    /**
     * 16进制字符串转字节数组
     *
     * @param s
     * @return
     */
    private byte[] str2ByteArray(String s) {
        int byteArrayLength = s.length() / 2;
        byte[] b = new byte[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16)
                    .intValue();
            b[i] = b0;
        }

        return b;
    }

    /**
     * 针对Android6.0以上
     */
    public static class CryptoProvider extends Provider {
        /**
         * Creates a Provider and puts parameters
         */
        public CryptoProvider() {
            super("Crypto", 1.0, "HARMONY (SHA1 digest; SecureRandom; SHA1withDSA signature)");
            put("SecureRandom.SHA1PRNG",
                    "org.apache.harmony.security.provider.crypto.SHA1PRNG_SecureRandomImpl");
            put("SecureRandom.SHA1PRNG ImplementedIn", "Software");
        }
    }
}
