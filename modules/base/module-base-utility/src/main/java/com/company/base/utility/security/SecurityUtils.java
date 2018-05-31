package com.company.base.utility.security;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;


/**
 * 安全工具类
 *
 * @author xuhj
 */
public class SecurityUtils {

    // ------------------------------- MD5 ------------------------------------------------

    /**
     * MD5加密
     */
    public static String MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16) hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }

    // --------------------------------- Base64 ----------------------------------------------

    /**
     * Base64加密
     */
    public static String encodeByBase64(String src) {
        return encodeByBase64(src.getBytes());
    }

    public static String encodeByBase64(byte[] bytes) {
        String result = Base64.encodeToString(bytes, Base64.NO_WRAP);
        return result;
    }

    /**
     * Base64解密
     */
    public static String decodeByBase64(String src) {
        return decodeByBase64(src.getBytes());
    }

    public static String decodeByBase64(byte[] bytes) {
        return new String(Base64.decode(bytes, Base64.DEFAULT));
    }

    /**
     * 通过Base64将Bitmap转换成Base64字符串
     *
     * @param bitmap
     * @return
     */
    public static String encodeBitmapByBase64(Bitmap bitmap) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);//参数100表示不压缩
        int options = 100;
        while (bos.toByteArray().length / 1024 > 100 && options > 25) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            bos.reset();//重置baos即清空baos
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, bos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 5;//每次都减少5
        }
        byte[] bytes = bos.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    /**
     * 通过Base64将File转换成Base64字符串
     *
     * @param file
     * @return
     */
    public static String encodeFileByBase64(File file) {
        byte[] buffer = null;
        try {
            FileInputStream inputFile = new FileInputStream(file);
            buffer = new byte[(int) file.length()];
            inputFile.read(buffer);
            inputFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.encodeToString(buffer, Base64.DEFAULT);
    }

    // ------------------------------ RSA -----------------------------------------------------

    /**
     * RSA加密
     *
     * @param src
     * @return
     */
    public static String encodeByRSA(String src) {
        try {
            return RSAUtils.encrypt(RSAUtils.getPublicKey(RSAUtils.RSA_PUBLIC_KEY), src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * RSA解密
     *
     * @param src
     * @return
     */
    public static String decodeByRSA(String src) {
        try {
            return RSAUtils.decrypt(RSAUtils.getPrivateKey(RSAUtils.RSA_PRIVATE_KEY), src);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // ------------------------------- AES ------------------------------------------------------

    /**
     * AES加密
     *
     * @param src
     * @return
     */
    public static String encodeByAES(String src) {
        return encodeByAES(src, AESUtils.AES_KEY);
    }

    public static String encodeByAES(String src, String key) {
        try {
            return AESUtils.encrypt(src, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * AES解密
     *
     * @param src
     * @return
     */
    public static String decodeByAES(String src) {
        return decodeByAES(src, AESUtils.AES_KEY);
    }

    public static String decodeByAES(String src, String key) {
        try {
            return AESUtils.decrypt(src, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    // ------------------------------ funtion -------------------------------------------------

    /**
     * 将二进制转换成16进制
     */
    public static final String byte2HexString(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            if ((b & 0xFF) < 0x10)
                hex.append("0");
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    /**
     * 将16进制转换为二进制
     *
     * @param hexStr
     * @return
     */
    public static byte[] hexString2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2),
                    16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

}
