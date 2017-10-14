package cn.com.jicongg.config.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RSA+BASE64对称加密.
 * 
 * 2017年9月27日
 * 
 * @author jicong.
 */
public class RSAUtil {

	private static final Logger log = LoggerFactory.getLogger(RSAUtil.class);

	// 数字签名，密钥算法
	private static final String RSA_KEY_ALGORITHM = "RSA";

	// 数字签名签名/验证算法
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	// RSA密钥长度
	private static final int KEY_SIZE = 1024;// 512 -2048

	private static final String PUBLIC_KEY = "publicKey";
	private static final String PRIVATE_KEY = "privateKey";

	private static final String LAMEZHI_KEY = "LAMEZHIIHZMEAL";

	/**
	 * 初始化RSA密钥对
	 * 
	 * @return RSA密钥对
	 * @throws Exception e
	 */
	public static Map<String, String> initKey() throws Exception {
		KeyPairGenerator keygen = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
		SecureRandom secrand = new SecureRandom();
		secrand.setSeed(LAMEZHI_KEY.getBytes());
		keygen.initialize(KEY_SIZE, secrand); 
		KeyPair keys = keygen.genKeyPair();
		//加密
		String publicKey = Base64.encodeBase64String(keys.getPublic().getEncoded());
		String privateKey = Base64.encodeBase64String(keys.getPrivate().getEncoded());
		Map<String, String> keyMap = new HashMap<String, String>();
		keyMap.put(PUBLIC_KEY, publicKey);
		keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * 得到公钥
	 * 
	 * @param keyMap RSA密钥对.
	 * @return 公钥
	 * @throws Exception 抛出异常.
	 *             
	 */
	public static String getPublicKey(Map<String, String> keyMap) throws Exception {
		return keyMap.get(PUBLIC_KEY);
	}

	/**
	 * 得到私钥
	 * 
	 * @param keyMap RSA密钥对.
	 * @return 私钥
	 * @throws Exception  抛出异常.
	 *            
	 */
	public static String getPrivateKey(Map<String, String> keyMap) throws Exception {
		return keyMap.get(PRIVATE_KEY);
	}

	/**
	 * 数字签名
	 * 
	 * @param data 待签名数据.
	 * @param privateKey  私钥.
	 * @return 签名.
	 * @throws Exception 抛出异常.
	 *             
	 */
	public static String sign(byte[] data, String privateKey) throws Exception {
		// 取得私钥
		byte[] keys = Base64.decodeBase64(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keys);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		// 生成私钥
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature
		signature.initSign(priKey);
		// 更新
		signature.update(data);
		return Base64.encodeBase64String(signature.sign());
	}

	/**
	 * RSA校验数字签名.
	 * 
	 * @param data 数据.
	 * @param sign 签名.
	 * @param publicKey 公钥.
	 * @return 校验结果，成功为true，失败为false
	 * @throws Exception   抛出异常
	 *           
	 */
	public boolean verify(byte[] data, byte[] sign, String publicKey) throws Exception {
		// 转换公钥材料
		// 实例化密钥工厂
		byte[] keys = Base64.decodeBase64(publicKey);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		// 初始化公钥
		// 密钥材料转换
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keys);
		// 产生公钥
		PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
		// 实例化Signature
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		// 初始化Signature
		signature.initVerify(pubKey);
		// 更新
		signature.update(data);
		// 验证
		return signature.verify(sign);
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param publicKeys
	 *            公钥
	 * @return 密文
	 * @throws Exception
	 *             抛出异常
	 */
	private static byte[] encryptByPubKey(byte[] data, byte[] publicKeys) throws Exception {
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeys);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param publicKey
	 *            公钥
	 * @return 密文
	 * @throws Exception
	 *             抛出异常
	 */
	public static String encryptByPubKey(String data, String publicKey) throws Exception {
		// 私匙加密
		byte[] publicKeys = Base64.decodeBase64(publicKey);
		byte[] enSign = encryptByPubKey(data.getBytes(), publicKeys);
		return Base64.encodeBase64String(enSign);
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param privateKey
	 *            私钥
	 * @return 密文
	 * @throws Exception
	 *             抛出异常
	 */
	private static byte[] encryptByPriKey(byte[] data, byte[] privateKeys) throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeys);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param privateKey
	 *            私钥
	 * @return 密文
	 * @throws Exception
	 *             抛出异常
	 */
	public static String encryptByPriKey(String data, String privateKey) throws Exception {
		// 私匙加密
		byte[] privateKey_bytes = Base64.decodeBase64(privateKey);
		byte[] enSign = encryptByPriKey(data.getBytes(), privateKey_bytes);
		return Base64.encodeBase64String(enSign);
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param publicKey
	 *            公钥
	 * @return 明文
	 * @throws Exception
	 *             抛出异常
	 */
	private static byte[] decryptByPubKey(byte[] data, byte[] publicKeys) throws Exception {
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKeys);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}

	/**
	 * 公钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param publicKey
	 *            公钥
	 * @return 明文
	 * @throws Exception
	 *             抛出异常
	 */
	public static String decryptByPubKey(String data, String publicKey) throws Exception {
		// 公匙解密
		byte[] publicKey_bytes = Base64.decodeBase64(publicKey);
		byte[] design = decryptByPubKey(Base64.decodeBase64(data), publicKey_bytes);
		return new String(design);
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param privateKey
	 *            私钥
	 * @return 明文
	 * @throws Exception
	 *             抛出异常
	 */
	private static byte[] decryptByPriKey(byte[] data, byte[] privateKeys) throws Exception {
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKeys);
		KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
		PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);
	}

	/**
	 * 私钥解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param privateKey
	 *            私钥
	 * @return 明文
	 * @throws Exception
	 *             抛出异常
	 */
	public static String decryptByPriKey(String data, String privateKey) throws Exception {
		// 私匙解密
		byte[] privateKey_bytes = Base64.decodeBase64(privateKey);
		byte[] design = decryptByPriKey(Base64.decodeBase64(data), privateKey_bytes);
		return new String(design);
	}

}