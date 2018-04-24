package com.hengyushop.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Base64;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class SignEncodeUtil {
	private static final String TAG = "code";

	private static final String ALGORITHM = "RSA";

	// public static void main(String[] args) {
	// // System.out.println(SignInfo("123456", ""));
	// // System.out.println(Sign2("19996123456789"));
	// }

	// 11E4679B4423892EF6D7226A6B5C21D2809A9909144621E22F54E52113FFA76F5D708D85D07A87ED78C8762907076249ABAA27D16A94D5954585EA62F32ACCC4CBDCDC7F7CDD6F691D251A2DE621EE86AF20123478CF2BB475488906B0CEB02F82077B6786B3F3A4E9023B3383141735BBCDF9B6A779C0F59B75DD945D043BDC
	// 11E4679B4423892EF6D7226A6B5C21D2809A9909144621E22F54E52113FFA76F5D708D85D07A87ED78C8762907076249ABAA27D16A94D5954585EA62F32ACCC4CBDCDC7F7CDD6F691D251A2DE621EE86AF20123478CF2BB475488906B0CEB02F82077B6786B3F3A4E9023B3383141735BBCDF9B6A779C0F59B75DD945D043BDC
	// 11E4679B4423892EF6D7226A6B5C21D2809A9909144621E22F54E52113FFA76F5D708D85D07A87ED78C8762907076249ABAA27D16A94D5954585EA62F32ACCC4CBDCDC7F7CDD6F691D251A2DE621EE86AF20123478CF2BB475488906B0CEB02F82077B6786B3F3A4E9023B3383141735BBCDF9B6A779C0F59B75DD945D043BDC

	@SuppressLint("NewApi")
	public static String Sign2(String voucher, Context context) {
		try {
			// File file = new File("D:\\rsa\\6438_MengNiu.key.p8.key");
			// //keyfile key文件的地址
			// File file = new File("D:\\rsa\\6438_MengNiu.key.p8"); //keyfile
			// key文件的地址
			System.out.println("代签名串：" + voucher);
			InputStream in = context.getResources().getAssets()
					.open("9058_yunsendianzi.key.p8");
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] tmpbuf = new byte[1024];
			int count = 0;
			while ((count = in.read(tmpbuf)) != -1) {
				bout.write(tmpbuf, 0, count);
				tmpbuf = new byte[1024];
			}
			in.close();
			// 把读的输入流转变成自己想要的privatekey
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
					bout.toByteArray());

			RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory
					.generatePrivate(privateKeySpec);
			System.out.println("私钥：" + privateKey.toString());
			// 这样就可以使用privatekey对自己的文件进行加密了
			// 进行加密
			Signature dsa = Signature.getInstance("SHA1withRSA"); // 采用SHA1withRSA加密
			dsa.initSign(privateKey);
			dsa.update(voucher.getBytes()); // voucher需要加密的String必须变成byte类型的
			byte[] sig = dsa.sign();
			System.out.println();
			// String byRSA = bytesToHexStr(sig); //加密后的byte类型变成十六进制的String

			byte[] sign = Base64.encode(sig, Base64.DEFAULT); // 原始数据

			String string = new String(sign);
			string = string.replace("\n", "");
			String encode = com.hengyushop.db.Base64.encode(sig);
			System.out.println("ump加密：" + encode);
			System.out.println("机密：" + string);
			string = URLEncoder.encode(string, "UTF-8");
			encode = URLEncoder.encode(encode, "UTF-8");
			System.out.println("ump加密utf：" + encode);
			System.out.println("机密utf：" + string);

			// byte[] decode = Base64.decode(sign, Base64.DEFAULT);
			// System.out.println("解码:"+new String(decode));
			return string;
		} catch (InvalidKeyException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		} catch (InvalidKeySpecException e) {

			e.printStackTrace();
		} catch (SignatureException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		return voucher;
	}

	private static final String dsf = "MIICXQIBAAKBgQCtvItiXsqWnJjnGqhkRD23tOrA9YaC3ynG/xPPv5EsNeHhHr4zAGfaZyFzzBc+XT24WsqUzipMBk6BGxnRXtUi9GEoe6PraQciEFXAEMa/CCRB0r7rdFT/u64uforeT1FCSA0lhypau4rxNcdwPEJqN5J+MykTjKTEPcvAsTWsFwIDAQABAoGAHScXKHGJgw5R5e2mNfTxekMEZU6NvKYfx4GD3Idjn8yG05SqC7rUsmQ9y8WCXPeeZLHvblrN5CXmxGk8wtIr50PMEY+4CZ33Nl4/PdFmOM083KM6sGMWO28kTX4KMyqmAnuAnzCRezlHGWqH5Xn60gbQHRZuKIEBiYYfBP+evkkCQQDcarTNG1PMXphzPUKvLMcrQyvBs3R/KVLQcPu4ny29kHjjZ2ckAW7/iE6ztnFZ6Jy6ox0r6Geqt6mrKTvn5crFAkEAycinUdh6a381nK+L0pjeki88qUJRsW/20j5TAbVf9RqkEJuIBrnuWOdb3VwSC7X24wKz9YIcRSQBvfHN8Af5KwJBAMo+xuMkXgG6Epw668McjSvvGGlFpnE/k5Na+D3xIOE9fQ77xDHPdu/VPJG9p8hdneHK5Wtydhy5JV++GA+yVBkCQQCx42MelGnYOt1YtKnfj0UoOtyPmxfKBZri3m7vIqblvgbFXVgeFew6FDy4eWKvUEvG9asQ1RN3ILcobPPQmDbhAkAFNR+H+HALnJ8n5SZ2jKH3ImAh0QzY4w90Jtl1DO8dy/O85n/R9A9EG4Ktn1p3kfRMTNI1C7yQs6E7zwZki";

	// private static final String
	// dsf="MIICxTCCAi6gAwIBAgIJAPY9V3nJ6xP8MA0GCSqGSIb3DQEBBQUAMEwxCzAJBgNVBAYTAkdCMRIwEAYDVQQIEwlCZXJrc2hpcmUxEDAOBgNVBAcTB05ld2J1cnkxFzAVBgNVBAoTDk15IENvbXBhbnkgTHRkMB4XDTEzMDgyOTExMTUwOVoXDTE2MDgyODExMTUwOVowTDELMAkGA1UEBhMCR0IxEjAQBgNVBAgTCUJlcmtzaGlyZTEQMA4GA1UEBxMHTmV3YnVyeTEXMBUGA1UEChMOTXkgQ29tcGFueSBMdGQwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAK28i2JeypacmOcaqGREPbe06sD1hoLfKcb/E8+/kSw14eEevjMAZ9pnIXPMFz5dPbhaypTOKkwGToEbGdFe1SL0YSh7o+tpByIQVcAQxr8IJEHSvut0VP+7ri5+it5PUUJIDSWHKlq7ivE1x3A8Qmo3kn4zKROMpMQ9y8CxNawXAgMBAAGjga4wgaswHQYDVR0OBBYEFK2XVoHT3GAQta4TcBHhx7WGRkiyMHwGA1UdIwR1MHOAFK2XVoHT3GAQta4TcBHhx7WGRkiyoVCkTjBMMQswCQYDVQQGEwJHQjESMBAGA1UECBMJQmVya3NoaXJlMRAwDgYDVQQHEwdOZXdidXJ5MRcwFQYDVQQKEw5NeSBDb21wYW55IEx0ZIIJAPY9V3nJ6xP8MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADgYEAiwqAsABkBiNL5YSReLhrKs48Hi4ahRxmmkQc5TNOP4P5OeBaxejtyMjm2qFesniC8XU1Bs5YsjNyRWbjBtGwefBuxQ77GfFCwg7Kz3805MvWQ+eyRTRvIRqcGb8bJDjnnyjJzfRgiL+PW+ObySm/R21+VMHp0VGNK9XKqp1TF88=";
	public static String SignInfo(String info, String pubkey) {
		String signature = null;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			EncodedKeySpec publicKeySpec = new PKCS8EncodedKeySpec(
					dsf.getBytes());
			// 得到私钥
			PrivateKey privateKey = keyFactory.generatePrivate(publicKeySpec);
			// 实例化Signature，用于产生数字签名，指定用RSA
			Signature sig = Signature.getInstance("RSA");

			// 用私钥来初始化数字签名对象
			sig.initSign(privateKey);
			// 对msgBytes实施签名
			sig.update(info.getBytes());
			// 完成签名，将结果放入字节数组signatureBytes
			byte[] sigsignatureBytes = sig.sign();
			signature = bytesToHexStr(sigsignatureBytes);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
		return signature;
	}

	public static String enCoder(String info, String pubkey) {
		String signature = null;
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(
					hexStrToBytes(pubkey));
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] cipherText = cipher.doFinal(info.getBytes("GBK"));
			signature = bytesToHexStr(cipherText);
		} catch (java.lang.Exception e) {
		}
		return signature;
	}

	/**
	 * md5加密文件
	 * 
	 * @param fileName
	 * @return
	 */
	public static String md5sum(String fileName) {
		InputStream fis;
		byte[] buffer = new byte[1024];
		int numRead = 0;
		MessageDigest md5;
		try {
			fis = new FileInputStream(fileName);
			md5 = MessageDigest.getInstance("MD5");
			while ((numRead = fis.read(buffer)) > 0) {
				md5.update(buffer, 0, numRead);
			}
			fis.close();
			return bytesToHexStr(md5.digest());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Transform the specified byte into a Hex String form.
	 */
	public static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);
		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}
		return s.toString();
	}

	/**
	 * MD5加密字符串。32位
	 * 
	 * @param inStr
	 * @return
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");

			char[] charArray = str.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++)
				byteArray[i] = (byte) charArray[i];

			byte[] md5Bytes = md5.digest(byteArray);
			return bytesToHexStr(md5Bytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transform the specified Hex String into a byte array.
	 */
	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes;
		bytes = new byte[s.length() / 2];
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}
		return bytes;
	}

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static byte[] getBytesFromFile(File file) {
		byte[] ret = null;
		try {
			if (file == null) {
				// log.error("helper:the file is null!");
				return null;
			}
			FileInputStream in = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
			byte[] b = new byte[4096];
			int n;
			while ((n = in.read(b)) != -1) {
				out.write(b, 0, n);
			}
			in.close();
			out.close();
			ret = out.toByteArray();
		} catch (IOException e) {
			// log.error("helper:get bytes from file process error!");
			e.printStackTrace();
		}
		return ret;
	}

}
