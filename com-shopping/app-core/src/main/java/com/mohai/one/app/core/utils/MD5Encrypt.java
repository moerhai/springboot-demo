package com.mohai.one.app.core.utils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Auther: moerhai@qq.com
 * @Date: 2020/12/28 01:37
 */
public class MD5Encrypt {

    private static final String ENCODE = "UTF-8";
    private static final String ENCNAME = "MD5";

    /**
     * 功能：MD5加密
     * @param strSrc 加密的源字符串
     * @return 加密串 长度32位(hex串)
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String getMessageDigest(String strSrc) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes(ENCODE);
        md = MessageDigest.getInstance(ENCNAME);
        md.update(bt);
        strDes = byteArrayToHexString(md.digest());
        return strDes;
    }

    /**
     * sha256_HMAC加密
     *
     * @param message
     *            消息
     * @param secret
     *            秘钥
     * @return 加密后字符串
     */
    private static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteArrayToHexString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    /**
     * 将加密后的字节数组转换成字符串
     *
     * @param bts 字节数组
     * @return 字符串
     */
    private static String byteArrayToHexString(byte[] bts) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; bts != null && n < bts.length; n++) {
            stmp = Integer.toHexString(bts[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString().toLowerCase();
    }

    public static final String PUBLIC_KEY = "qpda5-eu8uc-bta6c-qrdm4-775v7-nmymb-c1trv";
    public static final String PRIVATE_KEY = "*cVd)-6FYvS-97zEU-HT^SJ-9qd6&-Cz*md-;fpGt";

    public static void main(String[] args) {
        String encryptKey;
        try {
            encryptKey = MD5Encrypt.getMessageDigest(PRIVATE_KEY);
            System.out.println(" encryptKey : " + encryptKey);
            String str = sha256_HMAC("key=dhdv5-eu8uc-bta6c-qrdm4-775v7-nmymb-c1trv&nonce=170808230320", encryptKey);
            System.out.println(" getSignature : " + str);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}
