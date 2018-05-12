package com.wz.common.tools.spider;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.ByteArrayBuffer;

import com.wz.common.tools.CommonUtils;
import com.wz.common.tools.WebUtils;


@SuppressWarnings("deprecation")
public class HtmlSpider {

	/** default constructor **/
	public HtmlSpider() {
		super();
	}

	public HtmlSpider(int connectTimeout, int readTimeout) {
		this.connectTimeout = connectTimeout;
		this.readTimeout = readTimeout;
	}

	public HtmlSpider(String encoding) {
		setEncoding(encoding);
	}

	public HtmlSpider(String encoding, int connectTimeout, int readTimeout) {
		this(connectTimeout, readTimeout);
		setEncoding(encoding);
	}

	/** full constructor **/
	public HtmlSpider(String userAgent, boolean redirecting, String charSet,
			int bufferSize, int connectTimeout, int readTimeout,
			boolean supportGzip) {
		this(charSet, connectTimeout, readTimeout);
		this.userAgent = userAgent;
		this.bufferSize = bufferSize;
		this.redirecting = redirecting;
		this.supportGzip = supportGzip;
	}

	public void createConnecttion() {

		if (this.isAlive)
			throw new IllegalStateException(
					"Can't create connection after the connection instance has been established!");

		HttpParams params = new BasicHttpParams();

		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(params, this.connectTimeout);
		HttpConnectionParams.setSoTimeout(params, this.readTimeout);
		HttpConnectionParams.setSocketBufferSize(params, this.bufferSize);

		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(params, this.redirecting);
		// 设置 user agent
		HttpProtocolParams.setUserAgent(params, this.userAgent);
		// 设置 content charSet
		if (null != this.encoding)
			HttpProtocolParams.setContentCharset(params, getEncoding());

		DefaultHttpClient httpClient = new DefaultHttpClient(params);
		if (this.isSupportGzip()) {

			httpClient.addRequestInterceptor(new HttpRequestInterceptor() {

				public void process(HttpRequest request, HttpContext context)
						throws HttpException, IOException {

					if (!request.containsHeader("Accept-Encoding"))
						request.addHeader("Accept-Encoding", "gzip");

				}

			});

			httpClient.addResponseInterceptor(new HttpResponseInterceptor() {

				public void process(HttpResponse response, HttpContext context)
						throws HttpException, IOException {

					HttpEntity entity = response.getEntity();
					Header ceheader = entity.getContentEncoding();

					if (ceheader != null) {

						HeaderElement[] codecs = ceheader.getElements();

						for (int i = 0; i < codecs.length; i++) {

							if (codecs[i].getName().equalsIgnoreCase("gzip")) {
								response.setEntity(new GzipEntityWrapper(
										response.getEntity()));
								return;
							}

						}
					}
				}

			});
		}
		this.httpClient = httpClient;
		this.isAlive = true;
	}

	public String getContent(String url) throws IOException {

		HttpResponse httpResponse = getHttpResponse(url);

		StatusLine responseStatus;
		if ((responseStatus = httpResponse.getStatusLine()).getStatusCode() != HttpStatus.SC_OK)
			throw new IOException(
					"Can't get the target url's content, server returned: "
							+ responseStatus);

		if (null == this.encoding)
			try {
				setEncoding(WebUtils.getContentCharset(httpResponse));
			} catch (Exception ex) {
			}

		StringBuilder sb = new StringBuilder();
		InputStream in = httpResponse.getEntity().getContent();

		if (null == this.encoding) {

			ByteArrayBuffer buff = new ByteArrayBuffer(this.bufferSize);
			byte[] b = new byte[this.bufferSize];
			int len = 0;
			String charset = null;

			while ((len = in.read(b, 0, b.length)) != -1) {
				if (CommonUtils.isEmptyString(charset)) {
					try {
						charset = HtmlCharsetDetector.detectCodepage(b, len,
								false);
					} catch (Exception ucEx) {
						charset = HTTP.DEF_CONTENT_CHARSET.displayName();
					}
				}
				buff.append(b, 0, len);
			}
			setContentCharset(charset);
			sb.append(new String(buff.toByteArray(), getEncoding()));
		} else {
			Reader reader = new InputStreamReader(in, getEncoding());
			try {
				char[] tmp = new char[this.bufferSize / 2];
				int l;
				while ((l = reader.read(tmp, 0, tmp.length)) != -1) {
					sb.append(tmp, 0, l);
				}
			} finally {
				reader.close();
			}
		}

		return sb.toString();

	}

	public InputStreamReader getReader(String url) throws IOException {

		HttpResponse httpResponse = getHttpResponse(url);
		BufferedHttpEntity httpEntity = new BufferedHttpEntity(
				httpResponse.getEntity());

		if (null == this.encoding)
			try {
				setEncoding(WebUtils.getContentCharset(httpResponse));
			} catch (Exception ex) {
			}

		if (null == this.encoding) {
			setContentCharset(HtmlCharsetDetector.detectCodepage(httpEntity
					.getContent()));
			return new InputStreamReader(httpEntity.getContent(), getEncoding());
		}

		return new InputStreamReader(getInputStream(url), getEncoding());

	}

	public InputStream getInputStream(String url) throws IOException {

		return getHttpResponse(url).getEntity().getContent();

	}

	public HttpResponse getHttpResponse(String url) throws IOException {

		HttpGet httpGet = new HttpGet(WebUtils.parseTargetUrl(url));

		HttpResponse httpResponse = httpClient.execute(httpGet);

		if (httpResponse.getStatusLine().getStatusCode() != HttpStatus.SC_OK)
			// 错误处理，例如可以在该请求正常结束前将其中断
			httpGet.abort();

		return httpResponse;
	}

	public void closeConnection() {

		if (!this.isAlive)
			return;

		ClientConnectionManager connection;

		if (null != this.httpClient
				&& (connection = this.httpClient.getConnectionManager()) != null)
			connection.shutdown();

		this.httpClient = null;
		this.isAlive = false;

	}

	private void setContentCharset(String charset) {

		charset = CommonUtils.isEmptyString(charset)
				|| "GB2312".equalsIgnoreCase(charset.trim()) ? "GBK" : charset;
		this.encoding = Charset.forName(charset);

	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		if (this.isAlive)
			throw new IllegalStateException(
					"Can't set userAgent param after after the connection instance has been established!");

		this.userAgent = userAgent;
	}

	public boolean isRedirecting() {
		return redirecting;
	}

	public void setRedirecting(boolean redirecting) {
		if (this.isAlive)
			throw new IllegalStateException(
					"Can't set redirecting param after after the connection instance has been established!");

		this.redirecting = redirecting;
	}

	public String getEncoding() {
		return this.encoding == null ? HTTP.DEF_CONTENT_CHARSET.displayName()
				: encoding.name();
	}

	public void setEncoding(String encoding) {
		if (this.isAlive)
			throw new IllegalStateException(
					"Can't set charSet param after after the connection instance has been established!");

		setContentCharset(encoding);
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		if (this.isAlive)
			throw new IllegalStateException(
					"Can't set bufferSize param after after the connection instance has been established!");

		this.bufferSize = bufferSize;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(int readTimeout) {
		if (this.isAlive)
			throw new IllegalStateException(
					"Can't set readTimeout param after after the connection instance has been established!");

		this.readTimeout = readTimeout;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		if (this.isAlive)
			throw new IllegalStateException(
					"Can't set connectTimeout param after after the connection instance has been established!");

		this.connectTimeout = connectTimeout;
	}

	public boolean isSupportGzip() {
		return supportGzip;
	}

	public void setSupportGzip(boolean supportGzip) {
		if (this.isAlive)
			throw new IllegalStateException(
					"Can't set supportGzip param after after the connection instance has been established!");

		this.supportGzip = supportGzip;
	}

	private HttpClient httpClient;

	private boolean isAlive = false;

	/**** default values defined ****/
	private String userAgent = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)";

	private boolean redirecting = true;

	private Charset encoding;

	private int bufferSize = 8 * 1024;

	private int readTimeout = 2 * 1000;

	private int connectTimeout = 5 * 1000;

	private boolean supportGzip = true;

	// Usage:
	public static void main(String args[]) throws IOException {

		HtmlSpider spider = null;
		String url = "http://www.bdt.cn";
		try {
			spider = new HtmlSpider();
			spider.setReadTimeout(30 * 1000);
			spider.setSupportGzip(false);
			spider.createConnecttion();
			String s = spider.getContent(url);
			System.out.println(s);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			spider.closeConnection();
		}
	}
}