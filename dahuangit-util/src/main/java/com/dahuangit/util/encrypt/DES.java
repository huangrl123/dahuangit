package com.dahuangit.util.encrypt;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
 

/**
 * DES加密解密类
 *
 */
public class DES {
	private static String cipherName = "DES/CBC/PKCS5Padding";
	private Cipher ecipher;
    private Cipher dcipher;
    
    /**
     * 生成密匙
     * @return 密匙
     * @throws NoSuchAlgorithmException 
     */
    public static String markDEScode() throws NoSuchAlgorithmException {
    	String keyStr=null;
		SecretKey key1 = KeyGenerator.getInstance("DES").generateKey();
		byte[] keycode = key1.getEncoded();
		keyStr =EncodeUtils.base64Encode(keycode);
		//keyStr = new sun.misc.BASE64Encoder().encode(keycode);
    	return keyStr;
    }
    
    /**
     * 依密匙构造DES
     * @param keyStr
     * @throws NoSuchAlgorithmException 
     * @throws IOException 
     * @throws InvalidKeyException 
     * @throws InvalidKeySpecException 
     * @throws NoSuchPaddingException 
     * @throws InvalidAlgorithmParameterException 
     * @throws Base64DecodingException 
     */
    public DES(String keyBASE64Str) throws NoSuchAlgorithmException, InvalidKeyException, IOException, InvalidKeySpecException, NoSuchPaddingException, InvalidAlgorithmParameterException{
    	//DES算法要求有一个可信任的随机数源 , 已由IV 取代
        //DESKeySpec desKeySpec = new DESKeySpec(new sun.misc.BASE64Decoder().decodeBuffer(keyBASE64Str));
    	DESKeySpec desKeySpec = new DESKeySpec(EncodeUtils.base64Decode(keyBASE64Str));
    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    	SecretKey key = keyFactory.generateSecret(desKeySpec);
    	IvParameterSpec iv = new IvParameterSpec(EncodeUtils.base64Decode(keyBASE64Str));
    	//IvParameterSpec iv = new IvParameterSpec(new sun.misc.BASE64Decoder().decodeBuffer(keyBASE64Str));
        ecipher = Cipher.getInstance(cipherName);
        dcipher = Cipher.getInstance(cipherName);
        ecipher.init(Cipher.ENCRYPT_MODE, key, iv);
        dcipher.init(Cipher.DECRYPT_MODE, key, iv);
            
   
    }
    
    /**
     * 依密匙/加密的向量 构造DES
     * @param DES_KEY 加密的私钥
     * @param DES_IV  加密的向量
     */
    public DES(String DES_KEY, String DES_IV) throws Exception{
    	DESKeySpec desKeySpec = new DESKeySpec(DES_KEY.getBytes());
    	SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey key = keyFactory.generateSecret(desKeySpec);
        //DES算法 指定 加密的向量 
        IvParameterSpec iv = new IvParameterSpec(DES_IV.getBytes());
        ecipher = Cipher.getInstance(cipherName);
        dcipher = Cipher.getInstance(cipherName);
        ecipher.init(Cipher.ENCRYPT_MODE, key, iv);
        dcipher.init(Cipher.DECRYPT_MODE, key, iv);
     
    }
    
    /**
     * 加密
     * @param data
     * @return
     * @throws Exception
     */
    public byte[] encrypt(byte[] data) throws Exception {
        byte[] raw = ecipher.doFinal(data);
        return raw;
    }
    
    /**
     * 串加密
     * @param str
     * @return 加密后
     * @throws UnsupportedEncodingException 
     * @throws BadPaddingException 
     * @throws IllegalBlockSizeException 
     */
    public String encrypt(String str) throws UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException {
        byte[] utf8 = str.getBytes("UTF-8");
        byte[] enc = ecipher.doFinal(utf8);
        String strBase =EncodeUtils.base64Encode(enc);
        //return new sun.misc.BASE64Encoder().encode(enc);
        return strBase;
    }
    
    /**
     * 流加密
     * @param in
     * @param out
     */
    public void encrypt(InputStream in, OutputStream out)  throws IOException {
        CipherInputStream cis=null;
        try {
            cis = new CipherInputStream(in, ecipher);
            int size = 0;
            byte[] buffer = new byte[1024];
    	    int r;
    	    while ((r = cis.read(buffer)) > 0) {
    	    	out.write(buffer, 0, r);
    	    	buffer = new byte[1024];
    	    	size += r;
    	    }
    	    cis.close();
        } finally {
            try {
                if(cis!=null){
                    cis.close();
                }
            } catch (IOException ioe) {
                // ignore
            }   
        }
    }

    /**
     * 解密
     * @param raw
     * @return
     * @throws Exception
     */
    public byte[] decrypt(byte[] raw) throws Exception {
        try {
            byte[] data = dcipher.doFinal(raw);
            return data;
        } catch (Exception e) {
            throw new Exception("Do decrypt occurs Exception.[" + e.getMessage() + "]");
        }
    }
    
    /**
     * 串解密
     * @param str
     * @return 解密后
     */
    public String decrypt(String str) throws Exception{
        //byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
        byte[] dec = EncodeUtils.base64Decode(str);
        byte[] utf8 = dcipher.doFinal(dec);
        return new String(utf8, "UTF-8");
    }
    
    /**
     * 流解密
     * @param in
     * @param out
     */
    public void decrypt(InputStream in, OutputStream out) throws IOException{
        CipherOutputStream cos=null;
         try{   
             cos = new CipherOutputStream(out, dcipher);
            byte[] buffer = new byte[1024];
    	    int r;
    	    int size = 0 ;
    	    while ((r = in.read(buffer)) >= 0) {
    	        cos.write(buffer, 0, r);
    	        buffer = new byte[1024];
    	        size += r;
    	    }
    	    cos.close();
        } finally {
            try {
                if(cos!=null){
                    cos.close();
                }
            } catch (IOException ioe) {
                // ignore
            }   
        }
    }

    public static void main(String[] args) {
    	String scode_2 = "JmGKE3NiI+k=";
		try {
			//String code=DES.markDEScode();
			//System.out.println("==============="+code);
			
			DES des = new DES(scode_2);
			
			String testData="测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据"+
							"测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据测试数据";
			System.out.println("加密前数据大小:"+testData.getBytes().length);				
			String enc=des.encrypt(testData);
			System.out.println("加密后数据大小: "+enc.getBytes().length);
			
			//String zStr=ZipUtils.gzip(testData);
			//System.out.println("加密并压缩后数据大小: "+zStr.getBytes().length+", "+zStr);
			
			
			DES des2 = new DES(scode_2);
			String dec=des2.decrypt(enc);
		 
			//dec=ZipUtils.gunzip(dec);
			System.out.println("解密后数据大小:"+dec.getBytes().length+", "+dec);	
			
			//System.out.println("解密"+dec);
			
/*			String n=RC4.encrypt(scode_2, testData);
			System.out.println("加密后数据大小==: "+n.getBytes().length);
			System.out.println("加密=="+n);*/
			
/*			String e=RC4.decrypt(scode_2, n);
			System.out.println("解密后数据大小==:"+e.getBytes().length);	
			System.out.println("解密=="+e);*/
			
			/*
			FileInputStream pageHtmlInStream = new FileInputStream("E:\\temp\\login.html");
			String outHtmz = "E:\\temp\\login1.htmlz";
			FileOutputStream outputStream = new FileOutputStream(outHtmz);
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			byte[] tmpbuf = new byte[1024];
	        int count = 0;
	        while ((count = pageHtmlInStream.read(tmpbuf)) != -1) {
	            bout.write(tmpbuf, 0, count);
	            tmpbuf = new byte[1024];
	        }
	        pageHtmlInStream.close();
	        byte[] orgData = bout.toByteArray();
	        byte[] raw = des.doEncrypt(orgData);
	        outputStream.write(raw);
	        outputStream.close();
	        byte[] data = des.doDecrypt(raw);
	        System.out.println("解密后大小:"+data.length);
	        System.out.println(new String(data));
	        
	        */
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
