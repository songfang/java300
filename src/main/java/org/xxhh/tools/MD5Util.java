package org.xxhh.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
	private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
	private static final String  MD5="MD5";
 

    /**
     * 对InputStream进行MD5编码
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String getMD5(InputStream inputStream) throws IOException{
    	MessageDigest md;
		try {
			md = MessageDigest.getInstance(MD5);
			md.reset();
		} catch (NoSuchAlgorithmException e) {
			if(LOGGER.isWarnEnabled()){
				LOGGER.warn("No Such Algorithm",e);
			}
			return null;
		}
		byte[] bytes = new byte[2048];
		int numBytes;
		while ((numBytes = inputStream.read(bytes)) != -1) {
			md.update(bytes, 0, numBytes);
		}
		byte[] digest = md.digest();
		String md5 = new String(Hex.encodeHex(digest));
		return md5;

    }
    
    
    /**
     * 获取文件的MD5码
     * @param file
     * @return
     */
    public static String getFileMD5(File file){
    	try {
			return getMD5(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			if(LOGGER.isInfoEnabled()){
				LOGGER.info(String.format("{%s} can not found!", file));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
    }
}
