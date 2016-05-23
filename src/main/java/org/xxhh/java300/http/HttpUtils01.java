package org.xxhh.java300.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xxhh.io.StreamUtil;
import org.xxhh.tools.MD5Util;

/**
 * 类HttpUtil的实现描述：HttpUtil API的实现
 * 
 * @author kevin01.yang
 * @date 2015.05.15
 * @version 1.0
 */

public class HttpUtils01 {

	@Value("${cache_data}")
	private String cachedata;
	
	@Value("${nimbus_topology_dir}")
	private String nimbusTopologyDir;

	private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils01.class);
	private static final String BOUNDARY = "--------WebKitFormBoundaryE19zNvXGzXaLvS5C";
	private static final String POST = "POST";
	private static final String GET = "GET";

	private URL url;
	private URLConnection conn;
	private HttpURLConnection hConnection;

	/**
	 * 配置基本参数
	 */
	public void config(String u) throws IOException {
		url = new URL(u);
		conn = url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		conn.connect();
	}
    
	/**
     * 配置基本参数POST
     */
	public void configmore(String u) throws IOException{
		url = new URL(u);
		conn = url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("connection", "Keep-Alive");
		conn.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		conn.connect();
	}
	/**
	 * 设置上传的配置参数
	 * 
	 * @param targetURL
	 * @throws IOException
	 */
	public void configUpload(String u, InputStream inputStream)
			throws IOException {
		url = new URL(u);
		hConnection = (HttpURLConnection) url.openConnection();
		hConnection.setUseCaches(false);
		hConnection.setDoOutput(true);
		hConnection.setDoInput(true);
		hConnection.setRequestMethod(POST);
		hConnection.setRequestProperty("Connection", "Keep-Alive");
		hConnection.setRequestProperty("user-agent",
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		hConnection.setRequestProperty("Content-Type",
				"multipart/form-data;boundary=" + BOUNDARY);
		hConnection.setRequestProperty("X-Cider-Md5",
				MD5Util.getMD5(inputStream));

	}

	/**
	 * 配置Http报文的内容
	 * 
	 * @param name
	 * @param value
	 * @return
	 * @throws IOException
	 */
	public HashMap<String, String> getParas(String name, String value)
			throws IOException {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put(name, value);
		return map;
	}

	/**
	 * The URL 不包含参数且为GET请求
	 * 
	 * @param u
	 * @return
	 * @throws IOException
	 */
	public String jsonCallBackWithOutParams(String u) throws IOException {
		config(u);
		// BufferedReader in = new BufferedReader(new InputStreamReader(
		// conn.getInputStream()));
		// String line;
		// while ((line = in.readLine()) != null) {
		// result += line;
		// }//中文编码问题
		InputStream in = conn.getInputStream();
		StringBuilder result = new StringBuilder();
		byte[] bytes = new byte[4096];
		int size = 0;
		while ((size = in.read(bytes)) > 0) {
			String str = new String(bytes, 0, size, "utf8");
			result.append(str);
		}
		return result.toString();
	}

	/**
	 * 文件上传
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public boolean upload(String u, String filename, InputStream inputStream)
			throws IOException {
		String res = "";
		byte[] input = StreamUtil.input2byte(inputStream);
		configUpload(u, StreamUtil.byte2Input(input));

		HashMap<String, String> package_map = getParas("package", filename);
		HashMap<String, String> metadata_map = getParas("metadata", "{}");

		OutputStream out = new DataOutputStream(hConnection.getOutputStream());

		// add metadata
		if (metadata_map != null) {
			StringBuffer strBuf = new StringBuffer();
			Iterator<Map.Entry<String, String>> iter = metadata_map.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				String inputName = (String) entry.getKey();
				String inputValue = (String) entry.getValue();
				if (inputValue == null) {
					continue;
				}
				strBuf.append("\r\n").append("--").append(BOUNDARY)
						.append("\r\n");
				strBuf.append("Content-Disposition: form-data; name=\""
						+ inputName + "\"\r\n\r\n");
				strBuf.append(inputValue);
			}
			LOGGER.info(String.format("%s", new String(strBuf)));
			out.write(strBuf.toString().getBytes());
		}

		// add package
		if (package_map != null) {
			Iterator<Map.Entry<String, String>> iter = package_map.entrySet()
					.iterator();
			while (iter.hasNext()) {
				Map.Entry<String, String> entry = iter.next();
				String inputName = (String) entry.getKey();
				String inputValue = (String) entry.getValue();
				if (inputValue == null) {
					continue;
				}
				File file_r = new File(inputValue);
				String file_name = file_r.getName();
				String contentType = new MimetypesFileTypeMap()
						.getContentType(file_r);
				if (filename.endsWith(".txt")) {
					contentType = "text/plain";
				}
				StringBuffer strBuf = new StringBuffer();
				strBuf.append("\r\n").append("--").append(BOUNDARY)
						.append("\r\n");
				strBuf.append("Content-Disposition: form-data; name=\""
						+ inputName + "\"; filename=\"" + file_name + "\"\r\n");
				strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
				LOGGER.info(String.format("%s", new String(strBuf)));
				out.write(strBuf.toString().getBytes());

				DataInputStream in = new DataInputStream(
						StreamUtil.byte2Input(input)); // new
														// FileInputStream(file_r)
				int bytes = 0;
				byte[] bufferOut = new byte[1024];
				while ((bytes = in.read(bufferOut)) != -1) {
					out.write(bufferOut, 0, bytes);
				}
				in.close();
			}
		}
		byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
		out.write(endData);
		out.flush();
		out.close();

		// 读取返回数据
		StringBuffer strBuf1 = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				hConnection.getInputStream()));
		String line = null;
		while ((line = reader.readLine()) != null) {
			strBuf1.append(line).append("\n");
		}
		res = strBuf1.toString();
		System.out.println(res);
		reader.close();
		reader = null;

		return true;
	}
 

	/**
	 * 复制文件
	 * @param target
	 * @param input
	 * @throws IOException
	 */
	public static void copyFile(String target, InputStream input)
			throws IOException {
		// 新建文件输入流并对它进行缓冲
		BufferedInputStream inBuff = new BufferedInputStream(input);

		// 新建文件输出流并对它进行缓冲
		File file = new File(target);
		if (file.exists()) {
			file.delete();
		}
		file.createNewFile();
		FileOutputStream output = new FileOutputStream(target);
		BufferedOutputStream outBuff = new BufferedOutputStream(output);

		// 缓冲数组
		byte[] b = new byte[1024 * 5];
		int len;
		while ((len = inBuff.read(b)) != -1) {
			outBuff.write(b, 0, len);
		}
		// 刷新此缓冲的输出流
		outBuff.flush();

		// 关闭流
		inBuff.close();
		outBuff.close();
		output.close();
		input.close();
	}

	/**
	 * 下载文件
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public String download(String u) throws IOException {
		return jsonCallBackWithOutParams(u);
	}

	/**
	 * 一般接口调用  POST请求
	 * @param u
	 * @return
	 * @throws IOException
	 */
	public String common(String url,String data) throws IOException{
		if(url==null||"".equals(url)){
			return null;
		}
		configmore(url);
        OutputStreamWriter out = new OutputStreamWriter(conn.getOutputStream());     
        out.write(data);      
        out.flush();     
        out.close();     
          
        String sCurrentLine;     
        String sTotalString;     
        sCurrentLine = "";     
        sTotalString = "";     
        InputStream l_urlStream=conn.getInputStream();     
     
        BufferedReader l_reader = new BufferedReader(new InputStreamReader(l_urlStream));     
        while ((sCurrentLine = l_reader.readLine()) != null) {
            sTotalString += sCurrentLine;     
        }
        LOGGER.info("the return status is {}",sTotalString); 
        return sTotalString;
	}
	

	/**
	 * 一般接口调用  GET请求
	 * @param url
	 * @param data
	 * @return
	 */
	public String commonGet(String url){
		String result = "";
        BufferedReader in = null;
        try {
           config(url);
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
        	LOGGER.error("发送GET请求出现异常！" + e);
          
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
            	LOGGER.error(e2.getMessage(),e2);
            }
          }
          return result;
	}
	
	
	//发送get请求的函数 特点是使用了HttpClients工具
	public static String getUrl(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			// 创建httpget.  
			HttpGet httpget = new HttpGet(url);
			LOGGER.info("executing request " + httpget.getURI());
			// 执行get请求.  
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体  
				HttpEntity entity = response.getEntity();
				
				// 打印响应状态  
				LOGGER.info(""+response.getStatusLine());
				if (entity != null) {
					// 打印响应内容长度  
					LOGGER.info("Response content length: " + entity.getContentLength());
					// 打印响应内容  
					//System.out.println("Response content: " + EntityUtils.toString(entity));
					
					return EntityUtils.toString(entity);
					
				}
				
				
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (ParseException e) {
			LOGGER.error(e.getMessage(),e);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(),e);
		} finally {
			// 关闭连接,释放资源  
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(),e);
			}
		}
		return null;		
	}
	
    public static String request(String url, String params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpUriRequest request = null;
            if (null == params || "".equals(params.trim())) {//GET
                request = new HttpGet(url);
            } else {//POST
                HttpPost httppost = new HttpPost(url);
                List<NameValuePair> ps = new ArrayList<NameValuePair>();
                String[] args = params.split("&");
                for (String arg : args) {
                    String[] kv = arg.split("=");
                    if (kv.length >= 2) {
                        ps.add(new BasicNameValuePair(kv[0], kv[1]));
                    } else {
                        ps.add(new BasicNameValuePair("", kv[0]));
                    }
                }
                UrlEncodedFormEntity uefEntity = new UrlEncodedFormEntity(ps, "UTF-8");
                httppost.setEntity(uefEntity);
                request = httppost;
            }
            CloseableHttpResponse response = httpclient.execute(request);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, "UTF-8");
                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    
	public static void main(String[] args) {
		System.out.println(HttpUtils01.request("www.baidu.com", null));
	}
}
