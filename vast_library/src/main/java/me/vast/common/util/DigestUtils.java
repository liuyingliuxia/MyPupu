package me.vast.common.util;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


/**
 * 数字签名、加密解密工具
 */
public class DigestUtils {

    private static byte[] doCipher(String transformation, int opmode, Key key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(opmode, key);
//        byte[] result = cipher.doFinal(data);
        return cipher.doFinal(data);
    }

    /**
     * MD5加密
     */
    public static class MD5 {

        /***
         * 以大写形式生成MD5串
         *
         * @param cleartext 明文
         * @return
         */
        public static String genUpperCase(String cleartext) {
            return gen(cleartext).toUpperCase(Locale.US);
        }

        /**
         * 生成MD5串
         *
         * @param cleartext 明文
         * @return
         */
        public static String gen(String cleartext) {
            MessageDigest md;
            try {
                md = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
            md.update(cleartext.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format(Locale.US, "%02x", b & 0xff));
            }
            return sb.toString();
        }
    }

    /**
     * 对称加密AES工具，源代码来自网络（SimpleCrypto）Usage:
     * <pre>
     * String crypto = AES.encrypt(masterpassword, cleartext)
     * ...
     * String cleartext = AES.decrypt(masterpassword, crypto)
     * </pre>
     *
     * @author ferenc.hechler
     */
    public static class AES {

        private final static String HEX = "0123456789ABCDEF";

        public static String encrypt(String seed, String cleartext) throws Exception {
            byte[] cleartextBytes = cleartext.getBytes("UTF-8");
            byte[] result = doCipher("AES", Cipher.ENCRYPT_MODE, getSecretKey(seed), cleartextBytes);
            return toHexStringFromByte(result);
        }

        public static String decrypt(String seed, String encrypted) throws Exception {
            byte[] encryptedBytes = toByteFromHexString(encrypted);
            byte[] result = doCipher("AES", Cipher.DECRYPT_MODE, getSecretKey(seed), encryptedBytes);
            return new String(result, "UTF-8");
        }

        private static Key getSecretKey(String seed) throws Exception {
            if (android.os.Build.VERSION.SDK_INT > android.os.Build.VERSION_CODES.M) {
                /********** PLEASE READ ************
                 * New versions of the Android SDK no longer support the Crypto provider.
                 * If your app was relying on setSeed() to derive keys from strings, you
                 * should switch to using SecretKeySpec to load raw key bytes directly OR
                 * use a real key derivation function (KDF). See advice here :
                 * http://android-developers.blogspot.com/2016/06/security-crypto-provider-deprecated-in.html
                 ***********************************/
//                SecretKeySpec keySpec = new SecretKeySpec(seed.getBytes(), "AES");
                return new SecretKeySpec(seed.getBytes(), "AES");
            } else {
                SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG", "Crypto");
                secureRandom.setSeed(seed.getBytes());
                KeyGenerator kgen = KeyGenerator.getInstance("AES");
                kgen.init(128, secureRandom); // 192 and 256 bits may not be available
                return kgen.generateKey();
            }
        }

        private static byte[] toByteFromHexString(String hexString) {
            int len = hexString.length() / 2;
            byte[] result = new byte[len];
            for (int i = 0; i < len; i++)
                result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
            return result;
        }

        private static String toHexStringFromByte(byte[] buf) {
            if (buf == null) return "";
            StringBuilder result = new StringBuilder(2 * buf.length);
            for (byte b : buf) {
                result.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
            }
            return result.toString();
        }

    }

    /**
     * 非对称加密RSA工具
     */
    private static class RSA {

        /**
         * 从base64格式的公钥串中获取公钥对象
         *
         * @param keyBase64String base64格式的公钥串
         * @return
         */
        public static PublicKey getPublicKey(String keyBase64String) {
            try {
                byte[] rawKey = Base64.decode(keyBase64String, Base64.DEFAULT);
                return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(rawKey));
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * 从base64格式的私钥串中获取公钥对象
         *
         * @param keyBase64String base64格式的私钥串
         * @return
         */
        public static PrivateKey getPrivateKey(String keyBase64String) {
            try {
                byte[] rawKey = Base64.decode(keyBase64String, Base64.DEFAULT);
                return KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(rawKey));
            } catch (Exception e) {
                return null;
            }
        }

        /**
         * 使用给指定RSA秘钥（公钥、或私钥）进行加密
         *
         * @param key       RSA秘钥（公钥、或私钥）
         * @param cleartext 明文
         * @return
         */
        public static String encrypt(Key key, String cleartext) {
            try {
                byte[] clearData = cleartext.getBytes("UTF-8");
                byte[] encryptedBytes = doCipher("RSA", Cipher.ENCRYPT_MODE, key, clearData);
                String encrypted = Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
                return encrypted.replaceAll("\\n", "");
            } catch (Exception e) {
                return "";
            }
        }

        /**
         * 使用给指定RSA秘钥（公钥、或私钥）进行解密
         *
         * @param key       RSA秘钥（公钥、或私钥）
         * @param encrypted 密文
         * @return
         */
        public static String decrypt(Key key, String encrypted) {
            try {
                byte[] encryptedBytes = Base64.decode(encrypted, Base64.DEFAULT);
                byte[] clearData = doCipher("RSA", Cipher.DECRYPT_MODE, key, encryptedBytes);
//                String cleartext = new String(clearData, "UTF-8");
                return new String(clearData, "UTF-8");
            } catch (Exception e) {
                return "";
            }
        }
    }


    /**
     * SHA256加密
     */
    public static class SHA256 {
        public static String shaEncrypt(String strSrc) {
            MessageDigest md;
            String strDes;
            byte[] bt = strSrc.getBytes();
            try {
                md = MessageDigest.getInstance("SHA-256");// 将此换成SHA-1、SHA-512、SHA-384等参数
                md.update(bt);
                strDes = bytes2Hex(md.digest()); // to HexString
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
            return strDes;
        }
    }

    public static class SHA1 {
        public static String shaEncrypt(String strSrc) {
            MessageDigest md;
            String strDes;
            byte[] bt = strSrc.getBytes();
            try {
                md = MessageDigest.getInstance("SHA-1");// 将此换成SHA-1、SHA-512、SHA-384等参数
                md.update(bt);
                strDes = bytes2Hex(md.digest()); // to HexString
            } catch (NoSuchAlgorithmException e) {
                return "";
            }
            return strDes;
        }
    }

    /**
     * byte数组转换为16进制字符串
     *
     * @param bts 数据源
     * @return 16进制字符串
     */
    public static String bytes2Hex(byte[] bts) {
//        String des = "";
        StringBuilder des = new StringBuilder();
        String tmp;
        int length = bts.length;
        for (int i = 0; i < length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des.append("0");
            }
            des.append(tmp);
        }
        return des.toString();
    }
}