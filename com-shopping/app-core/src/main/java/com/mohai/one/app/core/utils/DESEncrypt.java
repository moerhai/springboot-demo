package com.mohai.one.app.core.utils;

import org.springframework.util.DigestUtils;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * 加密解密工具 对称的
 * <p>
 * DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
 * <p>
 * 如果一定要用 NoPadding 的话，那么必须保证原文字节是 8 的倍数，否则的话需要使用其他的填充模式。
 * 在 Sun JCE 中默认的填充模式除了 NoPadding 之外，还有： PKCS5Padding 和 ISO10126Padding
 * <p>
 * PKCS5Padding 的填充规则是：填充至符合块大小的整数倍，填充值为填充数量数
 * <p>
 * ISO10126Padding 的填充规则是：填充至符合块大小的整数倍，填充值最后一个字节为填充的数量数，其他字节随机处理
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/26 13:00
 */
public class DESEncrypt {

    //加密填充方式。 DES是加解密算法；CBC是加解密模式；PKCS5Padding是否需要填充。
    private static final String DES_CBC_PKCS5Padding = "DES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "DES";

    private static Cipher cipher;

    static {
        try {
            cipher = Cipher.getInstance(DES_CBC_PKCS5Padding);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对称加密
     */
    public static String desEncrypt(String source,byte[] keyBytes) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        //创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        //然后用它把DESKeySpec转换成SecretKey
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(desKeySpec.getKey()));
        return byte2hex(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8))).toUpperCase();
    }

    /**
     * 对称解密
     */
    public static String desDecrypt(String source,byte[] keyBytes) throws Exception {
        byte[] src = hex2byte(source);
        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(keyBytes));
        byte[] retByte = cipher.doFinal(src);
        return new String(retByte);
    }


    private static String byte2hex(byte[] bArray) {
        if (bArray == null || bArray.length <= 0) {
            return null;
        }
        String stmp;
        StringBuilder out = new StringBuilder(bArray.length);
        for (byte b : bArray) {
            stmp = Integer.toHexString(b & 0xFF);
            if (stmp.length() < 2)
                out.append(0);
            out.append(stmp.toUpperCase());
        }
        return out.toString();
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    private static byte[] hex2byte(String hex) {
        if (hex == null || hex.equals("")) {
            return null;
        }
        hex = hex.toUpperCase();
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] cas = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(cas[pos]) << 4 | toByte(cas[pos + 1]));
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        String str = "测试DES加密";
        str = DigestUtils.md5DigestAsHex(str.getBytes());
        System.out.println("加密："+str);
        System.out.println(desEncrypt(str,"123456".getBytes(StandardCharsets.UTF_8)));
        System.out.println(desDecrypt(str,"123456".getBytes(StandardCharsets.UTF_8)));
    }

}