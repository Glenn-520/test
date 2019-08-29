package com.seatig.utils;


import javax.crypto.*;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.apache.commons.codec.binary.Base64;

public class DESUtils {
    private static Key key;
    private static String KEY_STR = "aksudhiuwer";

    static {
        try {
            KeyGenerator generator = KeyGenerator.getInstance("DES");
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(secureRandom);
            key = generator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    /**
     * 对字符串进行加密，返回BASE64的加密字符串 <功能详细描述>
     *
     * @param s
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getDecryptString(String s) {
        try {
            Base64 base64 = new Base64();
            byte[] decode = base64.decode(s.getBytes("UTF-8"));
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptStr = cipher.doFinal(decode);
            return new String(decryptStr, "UTF-8");
        } catch (Exception e) {
            return e.toString();
        }


    }

    public static String getEncryptString(String s) {
        Base64 base64 = new Base64();
        try {
            byte[] bytes = s.getBytes();
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStr = cipher.doFinal(bytes);
            return base64.encodeAsString(encryptStr);
        } catch (Exception e) {
            return e.toString();
        }
    }


}
