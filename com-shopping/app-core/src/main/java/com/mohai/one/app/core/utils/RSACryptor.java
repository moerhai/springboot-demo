package com.mohai.one.app.core.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RSA签名工具 非对称的
 * （Digital Signature Algorithm. based on discrete logarithms computation）算法,
 *  优点：容易实现密钥管理,便进行数字签名,算法复杂,加/解速度慢,采用非对称加密
 *
 * @Auther: moerhai@qq.com
 * @Date: 2020/11/27 00:34
 */
public class RSACryptor {

    private static final Logger LOG = LoggerFactory.getLogger(RSACryptor.class);

    //秘钥默认长度
    public static final int DEFAULT_KEY_SIZE = 2048;
    // 非对称加密密钥算法
    private static final String KEY_ALGORITHM = "RSA";
    // 签名算法：MD2withRSA,SHA1WithRSA,SHA256withRSA,SHA384withRSA,SHA512withRSA
    public static final String SIGN_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY ="publicKey";
    private static final String PRIVATE_KEY ="privateKey";
    private static final String SEED = "pwd123";

    /**
     * 锁
     */
    protected final Lock lock = new ReentrantLock();
    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     *
     * @throws NoSuchAlgorithmException
     */
    public RSACryptor() throws NoSuchAlgorithmException {
        Map<String,String> map = generateKey();
        this.setPublicKey(map.get(PUBLIC_KEY));
        this.setPrivateKey(map.get(PRIVATE_KEY));
    }

    /**
     * 构造方法
     * @param privateStr
     * @param publicStr
     */
    public RSACryptor(String privateStr,String publicStr) {
        if(privateStr != null){
            this.setPrivateKey(privateStr);
        }
        if(publicStr != null){
            this.setPublicKey(publicStr);
        }
    }

    public static Map<String,String> generateKey() throws NoSuchAlgorithmException {
        Map<String,String> keyMap = new HashMap<>();
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        // RSA算法要求有一个可信任的随机数源
        SecureRandom secureRandom  = new SecureRandom(SEED.getBytes());
        // 初始加密，做好用2048位，不容易破解
        keygen.initialize(DEFAULT_KEY_SIZE, secureRandom);
        // 生成密钥对
        KeyPair keyPair  = keygen.generateKeyPair();
        // 获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey)keyPair .getPrivate();
        String privateKeyStr = toHexString(privateKey.getEncoded());
        // 获取公钥
        RSAPublicKey publicKey = (RSAPublicKey)keyPair .getPublic();
        String publicKeyStr = toHexString(publicKey.getEncoded());
        // 放入map
        keyMap.put(PUBLIC_KEY, publicKeyStr);
        keyMap.put(PRIVATE_KEY, privateKeyStr);
        return keyMap;
    }

    public static RSAPublicKey getPublicKey(String publicKeyStr) {
        try {
            byte[] publicKeyBytes = toBytes(publicKeyStr);
            //创建x509证书封装类
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            //指定RSA
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            //生成公钥
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            return (RSAPublicKey) publicKey;
        } catch (Exception e) {
            LOG.error("get publicKey failed,cause:{}",e);
            throw new RuntimeException("get publicKey failed", e);
        }
    }

    /**
     * 私钥
     * @param privateKeyStr
     * @return
     */
    public static RSAPrivateKey getPrivateKey(String privateKeyStr) {
        try {
            byte[] privateKeyBytes = toBytes(privateKeyStr);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            return (RSAPrivateKey)privateKey;
        } catch (Exception e) {
            LOG.error("get privateKey failed,cause:{}",e);
            throw new RuntimeException("get privateKey failed", e);
        }
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = getPublicKey(publicKey);
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = getPrivateKey(privateKey);
    }

    /**
     * 加密
     *
     * @param keyType
     * @param cipherText
     * @return
     */
    public String encrypt(KeyType keyType, String cipherText) {
        try {
            lock.lock();
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            if (keyType == KeyType.PRIVATE_KEY) {
                cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            } else if (keyType == KeyType.PUBLIC_KEY) {
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            }
            byte[] cipherBytes = toBytes(cipherText);
            byte[] plainBytes = cipher.doFinal(cipherBytes);
            String plainText = toHexString(plainBytes);
            return plainText;
        } catch (Exception e) {
            LOG.error("encrypt failed,cause:{}",e);
            throw new RuntimeException("encrypt failed", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 解密
     *
     * @param keyType
     * @param cipherText
     * @return
     */
    public String decrypt(KeyType keyType, String cipherText) {
        try {
            lock.lock();
            Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
            if (keyType == KeyType.PRIVATE_KEY) {
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
            } else if (keyType == KeyType.PUBLIC_KEY) {
                cipher.init(Cipher.DECRYPT_MODE, publicKey);
            }
            byte[] cipherBytes = toBytes(cipherText);
            byte[] plainBytes = cipher.doFinal(cipherBytes);
            String plainText = toHexString(plainBytes);
            return plainText;
        } catch (Exception e) {
            LOG.error("decrypt failed,cause:{}",e);
            throw new RuntimeException("decrypt failed", e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 初始化RSA公钥私钥
     */
    public static KeyPair initKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGenerator.initialize(1024);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 签名（原数据，私钥 2要素）
     */
    public static byte[] sign(byte[] data, PrivateKey privateKey) throws Exception {
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(keySpec);
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);// 设置要计算的数据
        return signature.sign();
    }

    /**
     * 校验签名（元数据，公钥，签名 三要素）
     */
    public static boolean valid(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        Signature signature = Signature.getInstance(SIGN_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);
        return signature.verify(sign);
    }


    public static String toHexString(byte[] b) {
        return (new BASE64Encoder()).encodeBuffer(b).replace("\n", "").replace("\r", "");
    }

    public static byte[] toBytes(String s) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(s);
    }

    /**
     * 密钥类型
     */
    public enum KeyType {
        PRIVATE_KEY,
        PUBLIC_KEY
    }


    public static void main(String[] args) throws Exception{
        Map<String,String> keyMap = generateKey();
        System.out.println("公钥："+keyMap.get(PUBLIC_KEY));
        System.out.println("私钥："+keyMap.get(PRIVATE_KEY));
//        RSAPublicKey publicKey = getPublicKey(keyMap.get(PUBLIC_KEY));
//        RSAPrivateKey privateKey = getPrivateKey(keyMap.get(PRIVATE_KEY));
        RSACryptor cryptor = new RSACryptor(keyMap.get(PUBLIC_KEY),keyMap.get(PRIVATE_KEY));
        String info ="明文123456";
        //加密
        String enStr = cryptor.encrypt(KeyType.PUBLIC_KEY,info);
        System.out.println("加密："+enStr);
        //解密
        String deStr = cryptor.decrypt(KeyType.PRIVATE_KEY,enStr);
        System.out.println("解密："+deStr);
        System.out.println("----------------------------");
        String data = "123456";
        KeyPair keyPair = initKey();
        byte[] sign = sign(data.getBytes(), keyPair.getPrivate());
        boolean isValid = valid(data.getBytes(), keyPair.getPublic().getEncoded(), sign);
        System.out.println(isValid);
    }
}