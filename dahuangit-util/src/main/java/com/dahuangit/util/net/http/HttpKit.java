package com.dahuangit.util.net.http;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.Validate;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;

import com.dahuangit.util.xml.XpathUtils;

/**
 * 专门处理http请求与响应的工具
 * 
 * @author hrl
 * 
 */
public class HttpKit {
	public static final String CONTENTTYPE_JSON = "application/json;charset=UTF-8";
	public static final String CONTENTTYPE_XML = "application/xml;charset=UTF-8";
	public static final String CONTENTTYPE_HTML = "application/html;charset=UTF-8";
	public static final String CONTENTTYPE_TEXT = "application/text;charset=UTF-8";
	public static final int HTTP_STATUS_CODE_200 = 200;

	/**
	 * 测试代理服务器是否支持http get方法
	 * 
	 * @param proxyHost
	 * @param url
	 * @return
	 */
	public static boolean isProxyServerHttpGetAvailable(HostInfo proxyHost, String url) {
		Validate.notNull(url, "url不能为null");
		Validate.notNull(proxyHost, "proxyHost不能为null");

		CloseableHttpClient httpclient = null;

		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

			httpclient = httpClientBuilder.build();

			// 依次是代理地址，代理端口号，协议类型
			HttpHost proxy = new HttpHost(proxyHost.getAddr(), proxyHost.getPort());
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

			// 请求地址
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(config);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).build();
			httpGet.setConfig(requestConfig);

			HttpResponse response = httpclient.execute(httpGet);

			if (200 == response.getStatusLine().getStatusCode()) {

				httpclient.getConnectionManager().shutdown();

				return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	/**
	 * 测试代理服务器是否支持http post方法
	 * 
	 * HostInfo proxyHost
	 * 
	 * @param url
	 * @return
	 */
	public static boolean isProxyServerHttpPostAvailable(HostInfo proxyHost, String url) {
		Validate.notNull(url, "url不能为null");
		Validate.notNull(proxyHost, "proxyHost不能为null");

		CloseableHttpClient httpclient = null;

		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

			httpclient = httpClientBuilder.build();

			// 依次是代理地址，代理端口号，协议类型
			HttpHost proxy = new HttpHost(proxyHost.getAddr(), proxyHost.getPort());
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

			HttpPost httpost = new HttpPost(url);
			httpost.setConfig(config);

			RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).build();
			httpost.setConfig(requestConfig);

			HttpResponse response = httpclient.execute(httpost);

			if (200 == response.getStatusLine().getStatusCode()) {
				httpclient.getConnectionManager().shutdown();

				return true;
			}
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	/**
	 * 得到http服务端返回的字符（通过post方法）
	 * 
	 * @param host
	 * @param params
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String getHttpRequestContent(String host, Map<String, String> params, String encode)
			throws IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(host);

		if (null != params) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator it = params.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			httpost.setEntity(new UrlEncodedFormEntity(nvps, encode == null ? HTTP.UTF_8 : encode));
		}

		HttpResponse response = httpclient.execute(httpost);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(in, encode == null ? HTTP.UTF_8 : encode);
		BufferedReader read = new BufferedReader(inputStreamReader);
		String responseContent = "";
		String inputLine = "";
		while ((inputLine = read.readLine()) != null) {
			responseContent += inputLine;
		}
		inputStreamReader.close();
		read.close();
		httpclient.getConnectionManager().shutdown();
		return responseContent;
	}

	/**
	 * 得到http服务端返回的字符（通过post方法）
	 * 
	 * @param host
	 * @param params
	 *            key:参数名称 value:参数值
	 * @param multiparts
	 *            key:参数名称 value:文件
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String getHttpRequestContent(String host, Map<String, String> params, Map<String, File> multiparts,
			String encode) throws IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(host);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator it = params.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		MultipartEntity reqEntity = new MultipartEntity();
		// 设置基本信息
		for (Map.Entry<String, String> entry : params.entrySet()) {
			reqEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
		}
		// 设置设置附件
		for (Map.Entry<String, File> entry : multiparts.entrySet()) {
			ContentBody multipart = new FileBody(entry.getValue());
			reqEntity.addPart(entry.getKey(), multipart);
		}
		httpost.setEntity(reqEntity);

		HttpResponse response = httpclient.execute(httpost);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(in, encode == null ? HTTP.UTF_8 : encode);
		BufferedReader read = new BufferedReader(inputStreamReader);
		String responseContent = "";
		String inputLine = "";

		while ((inputLine = read.readLine()) != null) {
			responseContent += inputLine;
		}

		inputStreamReader.close();
		read.close();
		httpclient.getConnectionManager().shutdown();

		return responseContent;
	}

	/**
	 * 通过http协议下载文件
	 * 
	 * @param remoteFileSerialUrl
	 *            远程文件url,例如http://remoteIp:port/test.svg
	 * @param localFilePathAndName
	 *            保存到本地的文件路径,例如c:\test.svg
	 */
	public static void downloadFileByHttp(String remoteFileSerialUrl, String localFilePathAndName) {
		FileOutputStream fos = null;
		InputStream in = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(remoteFileSerialUrl);

		try {
			HttpResponse response = httpclient.execute(httpost);

			if (200 != response.getStatusLine().getStatusCode()) {
				throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
			}

			HttpEntity entity = response.getEntity();
			in = entity.getContent();

			File file = new File(localFilePathAndName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			fos = new FileOutputStream(file);
			byte[] buff = new byte[1024];
			int count;
			while ((count = in.read(buff)) != -1) {
				fos.write(buff, 0, count);
			}

			in.close();
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException("通过http协议下载文件时报错", e);
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过http协议下载文件
	 * 
	 * @param remoteFileSerialUrl
	 *            远程文件url,例如http://remoteIp:port/test.svg
	 * @param localFilePathAndName
	 *            保存到本地的文件路径,例如c:\test.svg
	 */
	public static void downloadFileByHttpGet(String remoteFileSerialUrl, String localFilePathAndName) {
		FileOutputStream fos = null;
		InputStream in = null;

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(remoteFileSerialUrl);

		try {
			HttpResponse response = httpclient.execute(httpGet);

			if (200 != response.getStatusLine().getStatusCode()) {
				throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
			}

			HttpEntity entity = response.getEntity();
			in = entity.getContent();

			File file = new File(localFilePathAndName);
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}

			fos = new FileOutputStream(file);
			byte[] buff = new byte[1024];
			int count;
			while ((count = in.read(buff)) != -1) {
				fos.write(buff, 0, count);
			}

			in.close();
			fos.close();
		} catch (Exception e) {
			throw new RuntimeException("通过http协议下载文件时报错", e);
		} finally {
			try {
				if (null != in) {
					in.close();
				}
				if (null != fos) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 通过http获取文件流
	 * 
	 * @param remoteFileSerialUrl
	 *            远程文件url,例如http://remoteIp:port/test.svg
	 */
	public static InputStream getInputStreamByHttp(String remoteFileSerialUrl) {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(remoteFileSerialUrl);

		try {
			HttpResponse response = httpclient.execute(httpost);

			if (200 != response.getStatusLine().getStatusCode()) {
				throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
			}

			HttpEntity entity = response.getEntity();

			return entity.getContent();
		} catch (Exception e) {
			throw new RuntimeException("通过http获取文件流", e);
		}
	}

	/**
	 * 得到http请求内容（通过get方法请求）
	 * 
	 * @param serialUrl
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	@SuppressWarnings("unchecked")
	public static String getHttpRequestContent(String serialUrl, String encode) throws ClientProtocolException,
			IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(serialUrl);
		HttpResponse response = httpclient.execute(httpGet);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();
		InputStreamReader in = new InputStreamReader(entity.getContent(), encode == null ? HTTP.UTF_8 : encode);
		BufferedReader reader = new BufferedReader(in);
		String line = null;
		String content = "";

		while (null != (line = reader.readLine())) {
			content += line;
		}

		in.close();
		reader.close();
		httpclient.getConnectionManager().shutdown();

		return content;
	}

	/**
	 * 进行http请求(通过post方法)
	 * 
	 * @param host
	 * @param params
	 * <br>
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static byte[] doHttpRequest(String host, Map<String, String> params) throws IOException {
		byte[] bt = null;
		Map<String, String> map = null == params ? new HashMap<String, String>() : params;
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(host);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		Iterator it = map.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, String> entry = (Entry<String, String>) it.next();
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
		HttpResponse response = httpclient.execute(httpost);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		InputStream in = response.getEntity().getContent();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		int temp;
		byte[] buf = new byte[1024];
		while ((temp = in.read(buf, 0, buf.length)) != -1) {
			bos.write(buf, 0, temp);
		}

		bt = bos.toByteArray();
		return bt;
	}

	/**
	 * 进行http请求(通过post方法)
	 * 
	 * @param host
	 * @param serialParams
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static byte[] doHttpRequest(String host, String serialParams) throws IOException {
		Map<String, String> map = new HashMap<String, String>();
		String[] arr = serialParams.split("&");

		for (String s : arr) {
			String[] kvArr = s.split("=");
			map.put(kvArr[0], kvArr[1]);
		}

		return HttpKit.doHttpRequest(host, map);
	}

	/**
	 * http请求(通过get方法)
	 * 
	 * @param serialUrl
	 * @return
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public static String doHttpRequest(String serialUrl) throws IOException {
		DefaultHttpClient httpclient = new DefaultHttpClient();

		HttpGet httpGet = new HttpGet(serialUrl);
		HttpResponse response = httpclient.execute(httpGet);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();

		String inputLine = "";
		StringBuffer responseContent = new StringBuffer();
		InputStreamReader in = new InputStreamReader(entity.getContent(), "utf-8");
		BufferedReader reader = new BufferedReader(in);
		while ((inputLine = reader.readLine()) != null) {
			responseContent.append(inputLine);
		}
		reader.close();

		httpclient.getConnectionManager().shutdown();

		return responseContent.toString();
	}

	/**
	 * 请求webservice服务
	 * 
	 * @param url
	 * 
	 *            请求url，格式为：webservice服务/方法
	 *            例如：http://127.0.0.1:8080/knet7000/services/
	 *            stopRestorationService/saveStopRestorationTask
	 * @param xmlParam
	 *            符合webservice标准的xml字符串 例如：<soapenv:Envelope
	 *            xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
	 *            xmlns:ent="http://entity.webservice.knet7000.knet.com/ent">
	 *            <soapenv:Header /> <soapenv:Body>
	 *            <ent:saveStopRestorationTask> <ent:stopRestoration
	 *            xmlns:m="http://entity.webservice.knet7000.knet.com/ent"> ...
	 *            </ent:saveStopRestorationTask> </soapenv:Body>
	 *            </soapenv:Envelope>
	 * @return
	 * @throws IOException
	 * @throws ClientProtocolException
	 */
	public static String requestWebservice(String url, String xmlParam) throws ClientProtocolException, IOException {
		Validate.notNull(url, "请求url不能为空");
		Validate.notNull(xmlParam, "符合webservice标准的xml字符串不能为空");

		String responseContent = "";

		HttpClient httpClient = new DefaultHttpClient();

		HttpPost httpPost = new HttpPost(url);

		byte[] b = xmlParam.getBytes("utf-8");
		InputStream is = new ByteArrayInputStream(b, 0, b.length);
		HttpEntity re = new InputStreamEntity(is, b.length);
		httpPost.setEntity(re);

		HttpResponse response = httpClient.execute(httpPost);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();

		String inputLine = "";
		InputStreamReader in = new InputStreamReader(entity.getContent(), "utf-8");
		BufferedReader reader = new BufferedReader(in);
		while ((inputLine = reader.readLine()) != null) {
			responseContent += inputLine;
		}
		reader.close();

		httpClient.getConnectionManager().shutdown();
		return responseContent;
	}

	/**
	 * 通过代理进行http post请求
	 * 
	 * @param host
	 *            主机地址
	 * @param proxyHost
	 *            主机要求的编码格式
	 * @param params
	 *            参数
	 * @param headers
	 *            http头信息
	 * @return
	 * @throws IOException
	 */
	public static ProxyHttpResponse doPostByProxy(String host, HostInfo proxyHost, Map<String, String> params,
			List<BasicHeader> headers) throws IOException {
		Validate.notNull(host, "主机地址不能为null");
		Validate.notNull(proxyHost, "代理服务器不能为null");

		if (proxyHost.getPort() == 0) {
			throw new RuntimeException("代理服务器端口不能为0");
		}

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

		CloseableHttpClient httpclient = httpClientBuilder.build();

		// 依次是代理地址，代理端口号，协议类型
		HttpHost proxy = new HttpHost(proxyHost.getAddr(), proxyHost.getPort());
		RequestConfig config = RequestConfig.custom().setProxy(proxy).build();

		HttpPost httpost = new HttpPost(host);
		httpost.setConfig(config);

		if (null != params) {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			Iterator it = params.entrySet().iterator();

			while (it.hasNext()) {
				Map.Entry<String, String> entry = (Entry<String, String>) it.next();
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}

			httpost.setEntity(new UrlEncodedFormEntity(nvps, proxyHost.getEncode() == null ? HTTP.UTF_8 : proxyHost
					.getEncode()));
		}

		// 设置http头
		if (null != headers) {
			httpost.setHeaders(headers.toArray(new Header[headers.size()]));
		}

		HttpResponse response = httpclient.execute(httpost);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();
		InputStream in = entity.getContent();
		InputStreamReader inputStreamReader = new InputStreamReader(in, proxyHost.getEncode() == null ? HTTP.UTF_8
				: proxyHost.getEncode());
		BufferedReader read = new BufferedReader(inputStreamReader);

		String responseContent = "";
		String line = null;
		String contentType = "text/html; charset=UTF-8";

		while ((line = read.readLine()) != null) {
			responseContent += line;

			if (line.contains("http-equiv=\"Content-Type\"")) {
				Node node = null;

				try {
					node = XpathUtils.findUnique(line, "./@content");
				} catch (DocumentException e) {
					continue;
				}

				if (null == node) {
					continue;
				}

				String text = node.getText();
				if (null == text) {
					continue;
				}

				String[] arr = text.split(";");
				if (arr.length != 2) {
					continue;
				}

				contentType = text;
			}
		}

		inputStreamReader.close();
		read.close();
		httpclient.getConnectionManager().shutdown();

		ProxyHttpResponse r = new ProxyHttpResponse();
		r.setContentType(contentType);
		r.setContent(responseContent);

		return r;
	}

	/**
	 * 通过代理进行http get请求
	 * 
	 * @param host
	 *            主机地址
	 * @param proxyHost
	 *            代理服务器
	 * @param headers
	 *            http头信息
	 * @return
	 * @throws IOException
	 */
	public static ProxyHttpResponse doGetByProxy(String host, HostInfo proxyHost, List<BasicHeader> headers)
			throws IOException {
		Validate.notNull(host, "主机地址不能为null");

		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		CloseableHttpClient httpclient = httpClientBuilder.build();

		// 请求地址
		HttpGet httpGet = new HttpGet(host);

		// 代理
		if (null != proxyHost) {
			HttpHost proxy = new HttpHost(proxyHost.getAddr(), proxyHost.getPort());
			RequestConfig config = RequestConfig.custom().setProxy(proxy).build();
			httpGet.setConfig(config);
		}

		// 设置http头
		if (null != headers) {
			httpGet.setHeaders(headers.toArray(new Header[headers.size()]));
		}

		HttpResponse response = httpclient.execute(httpGet);

		if (200 != response.getStatusLine().getStatusCode()) {
			throw new RuntimeException("http返回状态" + response.getStatusLine().getStatusCode());
		}

		HttpEntity entity = response.getEntity();
		InputStreamReader inputStreamReader = new InputStreamReader(entity.getContent(), "UTF-8");
		BufferedReader reader = new BufferedReader(inputStreamReader);

		String line = null;
		String content = "";
		String contentType = "text/html; charset=UTF-8";

		while (null != (line = reader.readLine())) {
			content += line;

			if (line.contains("http-equiv=\"Content-Type\"")) {
				Node node = null;

				try {
					node = XpathUtils.findUnique(line, "./@content");
				} catch (DocumentException e) {
					continue;
				}

				if (null == node) {
					continue;
				}

				String text = node.getText();
				if (null == text) {
					continue;
				}

				String[] arr = text.split(";");
				if (arr.length != 2) {
					continue;
				}

				contentType = text;
			}
		}

		reader.close();
		httpclient.getConnectionManager().shutdown();

		ProxyHttpResponse r = new ProxyHttpResponse();
		r.setContentType(contentType);
		r.setContent(content);

		return r;
	}

	public static String parseAndGetContentType(String html) {
		if (null == html || "".equals(html)) {
			return null;
		}

		if (html.contains("http-equiv=\"Content-Type\"")) {
			Node node = null;

			try {
				node = XpathUtils.findUnique(html, "./@content");
			} catch (DocumentException e) {
				return null;
			}

			if (null == node) {
				return null;
			}

			String text = node.getText();
			if (null == text) {
				return null;
			}

			String[] arr = text.split(";");
			if (arr.length != 2) {
				return null;
			}

			return text;
		}

		return null;
	}

	/**
	 * 根据html的输入流获取BufferedReader
	 * 
	 * @param htmlInputStream
	 * @return
	 */
	public static BufferedReader getBufferedReader(InputStream htmlInputStream) {

		if (null == htmlInputStream) {
			return null;
		}

		String line = null;
		String encode = null;
		InputStreamReader in = new InputStreamReader(htmlInputStream);
		BufferedReader reader = new BufferedReader(in);

		try {
			while (null != (line = reader.readLine())) {
				if (line.contains("http-equiv=\"Content-Type\"")) {
					Node node = null;

					try {
						node = XpathUtils.findUnique(line, "./@content");
					} catch (DocumentException e) {
						continue;
					}

					if (null == node) {
						continue;
					}

					String text = node.getText();
					if (null == text) {
						continue;
					}

					String[] arr = text.split(";");
					if (arr.length != 2) {
						continue;
					}

					if ("".equals(arr[1].trim())) {
						continue;
					}

					encode = arr[1].trim();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == encode) {
			encode = "UTF-8";
		}

		try {
			in = new InputStreamReader(htmlInputStream, encode);
			reader = new BufferedReader(in);
			return reader;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
