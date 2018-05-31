package com.company.base.utility.security;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * RSA算法，实现数据的加密解密。
 *
 * @author ShaoJiang
 */
public class RSAUtils {
    //RAS公钥
    public static String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC6txbYx4AJIG7GZxcft6NhLrGk\n" +
            "/u5luSwaDAO1a9rlxZ9/eJg3wcER7hWQTGDyyYRopO4haGVvIjOazYB7nzCmiYQf\n" +
            "pwvTusoK5x2pbh6eRjsedjDkv5NLHQiiWrazkI2qIvW36ZSuX0wHI0YDZykFpi2W\n" +
            "21v41CDIhliy46jrdwIDAQAB";
    //RAS私钥(原始)
//    public static String RSA_PRIVATE_KEY = "MIICXAIBAAKBgQC6txbYx4AJIG7GZxcft6NhLrGk/u5luSwaDAO1a9rlxZ9/eJg3\n" +
//            "wcER7hWQTGDyyYRopO4haGVvIjOazYB7nzCmiYQfpwvTusoK5x2pbh6eRjsedjDk\n" +
//            "v5NLHQiiWrazkI2qIvW36ZSuX0wHI0YDZykFpi2W21v41CDIhliy46jrdwIDAQAB\n" +
//            "AoGARXEOqDUaY0qSqGty6DLuKpipLQo7M834nv9U6cZhNQ+Y0FnTkvkWi0qW7oHZ\n" +
//            "lLJp8llUz0v6XajEtJA1dsSv3L1YDAEA7livmt1Bo5CJ7gTj184x1VhG8EXixCOd\n" +
//            "usVRSS/UQAVXldV0ER0BcGxHYMCcucEkeoWLK9YfRRQYKsECQQDo/m/RCC5FEIj6\n" +
//            "m++tkVsKKicXBYVU5FPQANOE1DbZnNRaYdtPwINfdevtRlhODdJgKjfT3NI9qKd5\n" +
//            "4pXP9UanAkEAzSbUpgF38aO1QOHspdZmTlfKi2lt44HGv1WNBQtnHAVfp7WJML1t\n" +
//            "WKv2Wuvi/3slEtO5zCxeoasuHy/mIciesQJBAIsLpSFjN3HCD203+E5l66XiSBfG\n" +
//            "FK734EjG5XRkzSMLI/OYaLBsnDow/o5Ip9RVDVKUf2KxasqfRutHXM6RewsCQG2K\n" +
//            "2G0zx4n9ciENAcGjc8lNU1eRmh2rBCbSVZRl/E6YB/WTF4gw/ZwlZkwyPGlSpf8h\n" +
//            "ksoUvwrwTpZUiztq1mECQDirJ5mJFTOaspqimy+kCyw0khEF3iOwE1RARS2v2+Kg\n" +
//            "usqIx+03S9wZl2YLrNOwkG3zGEstGtAmDXwFCpe2u1U=";
    //RAS私钥(SSL转换后)
    public static String RSA_PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALq3FtjHgAkgbsZn\n" +
            "Fx+3o2EusaT+7mW5LBoMA7Vr2uXFn394mDfBwRHuFZBMYPLJhGik7iFoZW8iM5rN\n" +
            "gHufMKaJhB+nC9O6ygrnHaluHp5GOx52MOS/k0sdCKJatrOQjaoi9bfplK5fTAcj\n" +
            "RgNnKQWmLZbbW/jUIMiGWLLjqOt3AgMBAAECgYBFcQ6oNRpjSpKoa3LoMu4qmKkt\n" +
            "Cjszzfie/1TpxmE1D5jQWdOS+RaLSpbugdmUsmnyWVTPS/pdqMS0kDV2xK/cvVgM\n" +
            "AQDuWK+a3UGjkInuBOPXzjHVWEbwReLEI526xVFJL9RABVeV1XQRHQFwbEdgwJy5\n" +
            "wSR6hYsr1h9FFBgqwQJBAOj+b9EILkUQiPqb762RWwoqJxcFhVTkU9AA04TUNtmc\n" +
            "1Fph20/Ag1916+1GWE4N0mAqN9Pc0j2op3nilc/1RqcCQQDNJtSmAXfxo7VA4eyl\n" +
            "1mZOV8qLaW3jgca/VY0FC2ccBV+ntYkwvW1Yq/Za6+L/eyUS07nMLF6hqy4fL+Yh\n" +
            "yJ6xAkEAiwulIWM3ccIPbTf4TmXrpeJIF8YUrvfgSMbldGTNIwsj85hosGycOjD+\n" +
            "jkin1FUNUpR/YrFqyp9G60dczpF7CwJAbYrYbTPHif1yIQ0BwaNzyU1TV5GaHasE\n" +
            "JtJVlGX8TpgH9ZMXiDD9nCVmTDI8aVKl/yGSyhS/CvBOllSLO2rWYQJAOKsnmYkV\n" +
            "M5qymqKbL6QLLDSSEQXeI7ATVEBFLa/b4qC6yojH7TdL3BmXZgus07CQbfMYSy0a\n" +
            "0CYNfAUKl7a7VQ==";

    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成密钥对
     *
     * @param filePath 生成密钥的路径
     */
    public static Map<String, String> generateKeyPair(String filePath) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            // 密钥位数
            keyPairGen.initialize(1024);
            // 密钥对
            KeyPair keyPair = keyPairGen.generateKeyPair();
            // 公钥
            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            //得到公钥字符串
            String publicKeyString = getKeyString(publicKey);
            //得到私钥字符串
            String privateKeyString = getKeyString(privateKey);
            //将密钥对写入到文件
            FileWriter pubfw = new FileWriter(filePath + "/publicKey.keystore");
            FileWriter prifw = new FileWriter(filePath + "/privateKey.keystore");
            BufferedWriter pubbw = new BufferedWriter(pubfw);
            BufferedWriter pribw = new BufferedWriter(prifw);
            pubbw.write(publicKeyString);
            pribw.write(privateKeyString);
            pubbw.flush();
            pubbw.close();
            pubfw.close();
            pribw.flush();
            pribw.close();
            prifw.close();
            //将生成的密钥对返回
            Map<String, String> map = new HashMap<String, String>();
            map.put("publicKey", publicKeyString);
            map.put("privateKey", privateKeyString);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 得到公钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PublicKey getPublicKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key, Base64.DEFAULT);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }

    /**
     * 得到私钥
     *
     * @param key 密钥字符串（经过base64编码）
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String key) throws Exception {
        byte[] keyBytes;
        keyBytes = Base64.decode(key, Base64.NO_WRAP);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }

    /**
     * 得到密钥字符串（经过base64编码）
     *
     * @return
     */
    public static String getKeyString(Key key) throws Exception {
        byte[] keyBytes = key.getEncoded();
        String s = Base64.encodeToString(keyBytes, Base64.NO_WRAP);
        return s;
    }

    /**
     * 使用公钥对明文进行加密，返回BASE64编码的字符串
     *
     * @param publicKey
     * @param plainText
     * @return
     */
    public static String encrypt(PublicKey publicKey, String plainText) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return Base64.encodeToString(enBytes, Base64.DEFAULT);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用keystore对明文进行加密
     *
     * @param publicKeystore 公钥文件路径
     * @param plainText      明文
     * @return
     */
    public static String encrypt(String publicKeystore, String plainText) {
        try {
            FileReader fr = new FileReader(publicKeystore);
            BufferedReader br = new BufferedReader(fr);
            String publicKeyString = "";
            String str;
            while ((str = br.readLine()) != null) {
                publicKeyString += str;
            }
            br.close();
            fr.close();
            cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKeyString));
            byte[] enBytes = cipher.doFinal(plainText.getBytes());
            return Base64.encodeToString(enBytes, Base64.NO_WRAP);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用私钥对明文密文进行解密
     *
     * @param privateKey
     * @param enStr
     * @return
     */
    public static String decrypt(PrivateKey privateKey, String enStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] deBytes = cipher.doFinal(Base64.decode(enStr, Base64.NO_WRAP));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用keystore对密文进行解密
     *
     * @param privateKeystore 私钥路径
     * @param enStr           密文
     * @return
     */
    public static String decrypt(String privateKeystore, String enStr) {
        try {
            FileReader fr = new FileReader(privateKeystore);
            BufferedReader br = new BufferedReader(fr);
            String privateKeyString = "";
            String str;
            while ((str = br.readLine()) != null) {
                privateKeyString += str;
            }
            br.close();
            fr.close();
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKeyString));
            byte[] deBytes = cipher.doFinal(Base64.decode(enStr, Base64.NO_WRAP));
            return new String(deBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
