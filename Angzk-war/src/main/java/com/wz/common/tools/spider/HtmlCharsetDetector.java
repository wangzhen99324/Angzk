package com.wz.common.tools.spider;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Arrays;

import org.mozilla.intl.chardet.nsDetector;
import org.mozilla.intl.chardet.nsICharsetDetectionObserver;
import org.mozilla.intl.chardet.nsPSMDetector;

import com.wz.common.tools.CommonUtils;



public class HtmlCharsetDetector {

	private static boolean found = false;

	private static String result;

	private static final nsDetector det;

	static {

		det = new nsDetector(nsPSMDetector.ALL);

		det.Init(new nsICharsetDetectionObserver() {

			public void Notify(String charset) {

				found = true;
				result = charset;

			}
		});
	}

	/**
	 * 返回输入流的字符类型
	 * 
	 * @Deprecated 调用此方法将会对输入流执行read操作。如果你希望通过此方法确定字符类型后，完成对输入流的读取操作，
	 *             就必须在调用此方法前后对输入流进行mark()和reset()操作
	 * @param InputStream
	 *            in
	 * @return String charSet
	 * @throws UnsupportedCharsetException
	 *             无法确定的字符类型
	 * @throws IOException
	 */
	@Deprecated
	public static String detectCodepage(InputStream in)
			throws UnsupportedCharsetException, IOException {

		byte[] buff = new byte[1024];
		int length;

		while ((length = in.read(buff, 0, buff.length)) != -1
				&& CommonUtils
						.isEmptyString(detectCodepage(buff, length, false)))
			continue;

		if (!found)
			detectCodepage(null, -1, true);

		return result;

	}

	/**
	 * 返回字节数组buff的字符类型
	 * 
	 * @param byte[] buff
	 * @param int length 从buff中取多少个字节进行字符类型判断
	 * @param boolean compulsory 是否强制获取，若为false，则可对此方法进行循环调用，以确定最后的字符类型
	 * @return String charSet
	 * @throws UnsupportedCharsetException
	 * @throws IOException
	 */
	public static String detectCodepage(byte[] buff, int length,
			boolean compulsory) throws UnsupportedCharsetException, IOException {

		boolean done = compulsory || det.DoIt(buff, length, false);

		if (done) {
			det.DataEnd();

			if (!found)
				throw new UnsupportedCharsetException(
						"Can't determine the character type, the possible types are: "
								+ Arrays.asList(det.getProbableCharsets())
										.toString());

			return result;
		}

		return null;
	}

}
