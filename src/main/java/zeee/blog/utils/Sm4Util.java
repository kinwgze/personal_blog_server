package zeee.blog.utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;

/**
 * sm4加密算法工具类
 *
 * @author wz297
 * @description sm4加密、解密与加密结果验证 可逆算法
 */
public class Sm4Util {
    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    private static final String ENCODING = "UTF-8";
    public static final String ALGORITHM_NAME = "SM4";
    /*加密算法/分组加密模式/分组填充方式
     PKCS5Padding-以8个字节为一组进行分组加密
     定义分组加密模式使用：PKCS5Padding*/
    private static final String ALGORITHM_NAME_ECB_PADDING = "SM4/ECB/PKCS5Padding";
    /** 128-32位16进制；256-64位16进制 */
    private static final int DEFAULT_KEY_SIZE = 128;

    /** 使用密钥 */
    private static final String SECRET_KEY = "7C663E2F4BE493F41A30C967F631D336";

    /**
     * 加密数据
     * @param data 待加密字符串
     * @return 加密后的字符串
     */
    public static String encryptDataEcb(String data) throws Exception {
        return encryptEcb(SECRET_KEY, data);
    }

    /**
     * 解密数据
     * @param data 待解密的字符串
     * @return 解密后的字符串
     */
    public static String decryptDataEcb(String data) throws Exception {
        return decryptEcb(SECRET_KEY, data);
    }

    /**
     * 校验加密前后的字符串是否为同一数据
     * @param cipherText 加密字符串
     * @param data 解密后的字符串
     */
    public static boolean verifyDataEcb(String cipherText, String data) throws Exception {
        return verifyEcb(SECRET_KEY, cipherText, data);
    }


    /**
     * 生成ECB暗号
     *
     * @param algorithmName 算法名称
     * @param mode          模式
     * @param key
     * @return
     * @throws Exception
     * @explain ECB模式（电子密码本模式：Electronic codebook）
     */
    private static Cipher generateEcbCipher(String algorithmName, int mode, byte[] key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithmName, BouncyCastleProvider.PROVIDER_NAME);
        Key sm4Key = new SecretKeySpec(key, ALGORITHM_NAME);
        cipher.init(mode, sm4Key);
        return cipher;
    }


    // 加密******************************************

    /**
     * sm4加密
     *
     * @param hexKey   16进制密钥（忽略大小写）
     * @param paramStr 待加密字符串
     * @return 返回16进制的加密字符串
     * @throws Exception
     * @explain 加密模式：ECB 密文长度不固定，会随着被加密字符串长度的变化而变化
     */
    private static String encryptEcb(String hexKey, String paramStr) throws Exception {
        String cipherText = "";
        // 16进制字符串-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // String-->byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 加密后的数组
        byte[] cipherArray = encryptEcbPadding(keyData, srcData);
        // byte[]-->hexString
        cipherText = ByteUtils.toHexString(cipherArray);
        return cipherText;
    }

    /**
     * 加密模式之Ecb
     *
     * @param key
     * @param data
     * @return
     * @throws Exception
     */
    private static byte[] encryptEcbPadding(byte[] key, byte[] data) throws Exception {
        // 声称Ecb暗号,通过第二个参数判断加密还是解密
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    // 解密****************************************

    /**
     * sm4解密
     *
     * @param hexKey     16进制密钥
     * @param cipherText 16进制的加密字符串（忽略大小写）
     * @return 解密后的字符串
     * @throws Exception
     * @explain 解密模式：采用ECB
     */
    private static String decryptEcb(String hexKey, String cipherText) throws Exception {
        // 用于接收解密后的字符串
        String decryptStr = "";
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // hexString-->byte[]
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] srcData = decryptEcbPadding(keyData, cipherData);
        // byte[]-->String
        decryptStr = new String(srcData, ENCODING);
        return decryptStr;
    }

    /**
     * 解密
     *
     * @param key
     * @param cipherText
     * @return
     * @throws Exception
     * @explain
     */
    private static byte[] decryptEcbPadding(byte[] key, byte[] cipherText) throws Exception {
        // 生成Ecb暗号,通过第二个参数判断加密还是解密
        Cipher cipher = generateEcbCipher(ALGORITHM_NAME_ECB_PADDING, Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(cipherText);
    }

    /**
     * 校验加密前后的字符串是否为同一数据
     *
     * @param hexKey     16进制密钥（忽略大小写）
     * @param cipherText 16进制加密后的字符串
     * @param paramStr   加密前的字符串
     * @return 是否为同一数据
     * @throws Exception
     * @explain
     */
    private static boolean verifyEcb(String hexKey, String cipherText, String paramStr) throws Exception {
        // 用于接收校验结果
        boolean flag = false;
        // hexString-->byte[]
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        // 将16进制字符串转换成数组
        byte[] cipherData = ByteUtils.fromHexString(cipherText);
        // 解密
        byte[] decryptData = decryptEcbPadding(keyData, cipherData);
        // 将原字符串转换成byte[]
        byte[] srcData = paramStr.getBytes(ENCODING);
        // 判断2个数组是否一致
        flag = Arrays.equals(decryptData, srcData);
        return flag;
    }


}

