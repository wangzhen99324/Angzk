package com.wz.common.tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.log4j.Logger;

import com.wz.common.utils.HttpClientUtils;

//HTTP 断点续传 demo（客户端测试工具：快车、迅雷）
public class HttpDownloadServlet {

	private static Logger logger = Logger.getLogger(HttpDownloadServlet.class);

	public static final int CACHE = 10 * 1024;

	/**
	 * 服务器提供 断点续传文件下载
	 * 
	 * @author Johnson.Jia
	 * @date 2015年12月25日 下午7:37:17
	 * @param request
	 * @param response
	 * @param fileName
	 */
	public static void downFile(HttpServletRequest request, HttpServletResponse response, String fileName) {
		BufferedInputStream bis = null;
		try {
			String path = new StringBuffer(WebUtils.getConfPath("android")).append("/").append(fileName).toString();
			File file = new File(path);

			verifyCheck(response, file);

			long p = 0L;
			int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
			long fileLength = file.length(), contentLength = file.length();

			// get file content
			InputStream ins = new FileInputStream(file);
			bis = new BufferedInputStream(ins);

			// tell the client to allow accept-ranges
			response.reset();
			response.setHeader("Accept-Ranges", "bytes");

			// client requests a file block download start byte
			String range = request.getHeader("Range");

			if (range != null && range.trim().length() > 0 && !"null".equals(range)) {

				response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
				String rangBytes = range.replaceAll("bytes=", "");

				if (rangBytes.endsWith("-")) { // bytes=270000-
					rangeSwitch = 1;
					p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
					contentLength = fileLength - p; // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
				} else { // bytes=270000-320000
					String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
					String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
					if (!temp2.contains("-")) {
						rangeSwitch = 2;
						p = StringUtils.isBlank(temp1) ? 0l : Long.parseLong(temp1);
						long toLength = StringUtils.isBlank(temp2) ? 0l : Long.parseLong(temp2);
						contentLength = toLength - p + 1; // 客户端请求的是
															// 270000-320000
															// 之间的字节
					}
				}
			}
			if (rangeSwitch > 0) {
				logger.info("【====文件续传下载====】----->" + fileName + " - " + range);
			} else {
				logger.info("【====文件初始下载====】----->" + fileName + " - " + range);
			}

			// 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
			// Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
			response.setHeader("Content-Length", new Long(contentLength).toString());

			// 断点开始 响应的格式是: Content-Range: bytes [文件块的开始字节]-[文件的总大小 -
			// 1]/[文件的总大小]
			if (rangeSwitch == 1) {
				String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
						.append(new Long(fileLength - 1).toString()).append("/").append(new Long(fileLength).toString()).toString();
				response.setHeader("Content-Range", contentRange);
				bis.skip(p);
			} else if (rangeSwitch == 2) {
				String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
				response.setHeader("Content-Range", contentRange);
				bis.skip(p);
			} else {
				String contentRange = new StringBuffer("bytes ").append("0-").append(fileLength - 1).append("/").append(fileLength).toString();
				response.setHeader("Content-Range", contentRange);
			}

			response.setContentType(setContentType(fileName));
			response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

			OutputStream out = response.getOutputStream();
			int n = 0;
			long readLength = 0;
			int bsize = 1024;
			byte[] bytes = new byte[bsize];
			if (rangeSwitch == 2) {
				// 针对 bytes=27000-39000 的请求，从27000开始写数据
				while (readLength <= contentLength - bsize) {
					n = bis.read(bytes);
					readLength += n;
					out.write(bytes, 0, n);
				}
				if (readLength <= contentLength) {
					n = bis.read(bytes, 0, (int) (contentLength - readLength));
					out.write(bytes, 0, n);
				}
			} else {
				while ((n = bis.read(bytes)) != -1) {
					out.write(bytes, 0, n);
				}
			}
			out.flush();
			out.close();
			bis.close();
			logger.info("【====文件下载完成====】----->" + fileName);
		} catch (IOException ie) {
			/**
			 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
			 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。 尤其是对于迅雷这种吸血的客户端软件，
			 * 明明已经有一个线程在读取 bytes=1275856879-1275877358，
			 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
			 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
			 * 所以，我们忽略这种异常
			 */
			logger.info("#提醒# 向客户端传输时出现IO异常，但此异常是允许的的，有可能客户端取消了下载，导致此异常，不用关心！");
		} catch (Exception e) {
			logger.error("【====文件下载异常====】", e);
		}
	}

	/**
	 * 效验文件
	 * 
	 * @author Johnson.Jia
	 * @date 2015年12月25日 下午6:44:42
	 * @param response
	 * @param downloadFile
	 * @throws IOException
	 */
	private static void verifyCheck(HttpServletResponse response, File downloadFile) throws IOException {
		response.setCharacterEncoding("UTF-8");
		if (downloadFile.exists()) {
			if (downloadFile.isFile()) {
				if (downloadFile.length() > 0) {
				} else {
					logger.info("请求下载的文件是一个空文件");
					response.getWriter().write("请求下载的文件是一个空文件");
					return;
				}
				if (!downloadFile.canRead()) {
					logger.info("请求下载的文件不是一个可读的文件");
					response.getWriter().write("请求下载的文件不是一个可读的文件");
					return;
				} else {
				}
			} else {
				logger.info("请求下载的文件是一个文件夹");
				response.getWriter().write("请求下载的文件是一个文件夹");
				return;
			}
		} else {
			logger.info("请求下载的文件不存在！");
			response.getWriter().write("请求下载的文件不存在！");
			return;
		}
	}

	/**
	 * 页面输出的文档MIME类型
	 * 
	 * @author Johnson.Jia
	 * @date 2015年12月25日 下午7:03:31
	 * @param returnFileName
	 * @return
	 */
	public static String setContentType(String returnFileName) {
		String contentType = "application/octet-stream";
		if (returnFileName.lastIndexOf(".") < 0)
			return contentType;
		returnFileName = returnFileName.toLowerCase();
		returnFileName = returnFileName.substring(returnFileName.lastIndexOf(".") + 1);

		if (returnFileName.equals("html") || returnFileName.equals("htm") || returnFileName.equals("shtml")) {
			contentType = "text/html";
		} else if (returnFileName.equals("apk")) {
			contentType = "application/vnd.android.package-archive";
		} else if (returnFileName.equals("sis")) {
			contentType = "application/vnd.symbian.install";
		} else if (returnFileName.equals("sisx")) {
			contentType = "application/vnd.symbian.install";
		} else if (returnFileName.equals("exe")) {
			contentType = "application/x-msdownload";
		} else if (returnFileName.equals("msi")) {
			contentType = "application/x-msdownload";
		} else if (returnFileName.equals("css")) {
			contentType = "text/css";
		} else if (returnFileName.equals("xml")) {
			contentType = "text/xml";
		} else if (returnFileName.equals("gif")) {
			contentType = "image/gif";
		} else if (returnFileName.equals("jpeg") || returnFileName.equals("jpg")) {
			contentType = "image/jpeg";
		} else if (returnFileName.equals("js")) {
			contentType = "application/x-javascript";
		} else if (returnFileName.equals("atom")) {
			contentType = "application/atom+xml";
		} else if (returnFileName.equals("rss")) {
			contentType = "application/rss+xml";
		} else if (returnFileName.equals("mml")) {
			contentType = "text/mathml";
		} else if (returnFileName.equals("txt")) {
			contentType = "text/plain";
		} else if (returnFileName.equals("jad")) {
			contentType = "text/vnd.sun.j2me.app-descriptor";
		} else if (returnFileName.equals("wml")) {
			contentType = "text/vnd.wap.wml";
		} else if (returnFileName.equals("htc")) {
			contentType = "text/x-component";
		} else if (returnFileName.equals("png")) {
			contentType = "image/png";
		} else if (returnFileName.equals("tif") || returnFileName.equals("tiff")) {
			contentType = "image/tiff";
		} else if (returnFileName.equals("wbmp")) {
			contentType = "image/vnd.wap.wbmp";
		} else if (returnFileName.equals("ico")) {
			contentType = "image/x-icon";
		} else if (returnFileName.equals("jng")) {
			contentType = "image/x-jng";
		} else if (returnFileName.equals("bmp")) {
			contentType = "image/x-ms-bmp";
		} else if (returnFileName.equals("svg")) {
			contentType = "image/svg+xml";
		} else if (returnFileName.equals("jar") || returnFileName.equals("var") || returnFileName.equals("ear")) {
			contentType = "application/java-archive";
		} else if (returnFileName.equals("doc")) {
			contentType = "application/msword";
		} else if (returnFileName.equals("pdf")) {
			contentType = "application/pdf";
		} else if (returnFileName.equals("rtf")) {
			contentType = "application/rtf";
		} else if (returnFileName.equals("xls")) {
			contentType = "application/vnd.ms-excel";
		} else if (returnFileName.equals("ppt")) {
			contentType = "application/vnd.ms-powerpoint";
		} else if (returnFileName.equals("7z")) {
			contentType = "application/x-7z-compressed";
		} else if (returnFileName.equals("rar")) {
			contentType = "application/x-rar-compressed";
		} else if (returnFileName.equals("swf")) {
			contentType = "application/x-shockwave-flash";
		} else if (returnFileName.equals("rpm")) {
			contentType = "application/x-redhat-package-manager";
		} else if (returnFileName.equals("der") || returnFileName.equals("pem") || returnFileName.equals("crt")) {
			contentType = "application/x-x509-ca-cert";
		} else if (returnFileName.equals("xhtml")) {
			contentType = "application/xhtml+xml";
		} else if (returnFileName.equals("zip")) {
			contentType = "application/zip";
		} else if (returnFileName.equals("mid") || returnFileName.equals("midi") || returnFileName.equals("kar")) {
			contentType = "audio/midi";
		} else if (returnFileName.equals("mp3")) {
			contentType = "audio/mpeg";
		} else if (returnFileName.equals("ogg")) {
			contentType = "audio/ogg";
		} else if (returnFileName.equals("m4a")) {
			contentType = "audio/x-m4a";
		} else if (returnFileName.equals("ra")) {
			contentType = "audio/x-realaudio";
		} else if (returnFileName.equals("3gpp") || returnFileName.equals("3gp")) {
			contentType = "video/3gpp";
		} else if (returnFileName.equals("mp4")) {
			contentType = "video/mp4";
		} else if (returnFileName.equals("mpeg") || returnFileName.equals("mpg")) {
			contentType = "video/mpeg";
		} else if (returnFileName.equals("mov")) {
			contentType = "video/quicktime";
		} else if (returnFileName.equals("flv")) {
			contentType = "video/x-flv";
		} else if (returnFileName.equals("m4v")) {
			contentType = "video/x-m4v";
		} else if (returnFileName.equals("mng")) {
			contentType = "video/x-mng";
		} else if (returnFileName.equals("asx") || returnFileName.equals("asf")) {
			contentType = "video/x-ms-asf";
		} else if (returnFileName.equals("wmv")) {
			contentType = "video/x-ms-wmv";
		} else if (returnFileName.equals("avi")) {
			contentType = "video/x-msvideo";
		}
		return contentType;
	}

	/**
	 * 下载 文件
	 * 
	 * @author Johnson.Jia
	 * @date 2017年2月28日 上午10:28:02
	 * @param url
	 *            下载路径
	 * @param filePath
	 *            保存路径
	 * @param fileName
	 *            保存文件名
	 * @return
	 */
	public static boolean download(String url, String filePath, String fileName) {
		InputStream is = null;
		FileOutputStream fileout = null;
		HttpGet httpget = null;
		try {
			if (StringUtils.isEmpty(filePath) || StringUtils.isEmpty(fileName)) {
				return false;
			}
			HttpClient client = HttpClientUtils.getHttpClient();
			httpget = new HttpGet(url);
			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			File file = new File(filePath + "/" + fileName);
			File dir = file.getParentFile();
			if ((!dir.exists()) || !(dir.isDirectory())) {
				dir.mkdirs();
			}
			fileout = new FileOutputStream(file);
			/**
			 * 根据实际运行效果 设置缓冲区大小
			 */
			byte[] buffer = new byte[CACHE];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			fileout.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Exception e) {
			}
			try {
				fileout.close();
			} catch (IOException e) {
			}
			if (httpget != null)
				httpget.releaseConnection();
		}
		return true;
	}

	public static void main(String[] args) {
		System.out.println(download("http://android.image.yaocaimaimai.com/Navicat_Premium_11.0.8_XiaZaiBa.exe", "G:/", "123.exe"));
	}

}