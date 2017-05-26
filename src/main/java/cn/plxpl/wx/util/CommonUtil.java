package cn.plxpl.wx.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class CommonUtil {

	/**
	 * �������utf-8���뷽ʽʱ�ַ�����ռ�ֽ���
	 * 
	 * @param content
	 * @return
	 */
	public static int getByteSize(String content) {
		int size = 0;
		if (null != content) {
			try {
				// ���ֲ���utf-8����ʱռ3���ֽ�
				size = content.getBytes("utf-8").length;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return size;
	}

	/**
	 * ��long���͵�ʱ��ת���ɱ�׼��ʽ��yyyy-MM-dd HH:mm:ss��
	 * 
	 * @param longTime
	 * @return
	 */
	public static String formatTime(long longTime) {
		DateFormat format = new SimpleDateFormat(Constants.DateFormate);
		return format.format(new Date(longTime));
	}

	/**
	 * ��΢����Ϣ�е�CreateTimeת���ɱ�׼��ʽ��ʱ�䣨yyyy-MM-dd HH:mm:ss��
	 * 
	 * @param createTime
	 *            ��Ϣ����ʱ��
	 * @return
	 */
	public static String formatTime(String createTime) {
		// ��΢�Ŵ����CreateTimeת����long���ͣ��ٳ���1000
		long msgCreateTime = Long.parseLong(createTime) * 1000L;
		DateFormat format = new SimpleDateFormat(Constants.DateFormate);
		return format.format(new Date(msgCreateTime));
	}

	/**
	 * emoji����ת��(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public static String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}

	/**
	 * ����������ַ���
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomString(int length) { // length��ʾ�����ַ����ĳ���
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String mapToXml(Map<String, String> map) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");

		for (Entry<String, String> entry : map.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			sb.append("<" + name + ">");
			sb.append("<![CDATA[" + value + "]]");
			sb.append("</" + name + ">");
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * ��չxstream��ʹ��֧��CDATA��
	 * 
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// ������xml�ڵ��ת��������CDATA���
				boolean cdata = true;

				@SuppressWarnings("unchecked")
				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	public static String objToXml(Object obj) {
		xstream.alias("xml", obj.getClass());
		return xstream.toXML(obj).replace("__", "_");
	}

	public static String urlEnodeUTF8(String str) {
		String result = str;
		try {
			result = URLEncoder.encode(str, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<String, String> parseXml(String xml) {
		Map<String, String> map = new HashMap<String, String>();
		Document document = null;
		try {
			// ��ȡ������XML�ĵ�
			// SAXReader����һ���ܵ�����һ�����ķ�ʽ����xml�ļ�������
			// SAXReader reader = new SAXReader(); //User.hbm.xml��ʾ��Ҫ������xml�ĵ�
			// Document document = reader.read(new File("User.hbm.xml"));
			// �������ͨ������xml�ַ�����
			document = DocumentHelper.parseText(xml); // ���ַ���תΪXML
			Element rootElt = document.getRootElement(); // ��ȡ���ڵ�
			List<Element> elements = rootElt.elements();
			for (Iterator<Element> it = elements.iterator(); it.hasNext();) {
				Element element = it.next();
				// System.out.println(element.getName() + " : "
				// + element.getTextTrim());
				map.put(element.getName(), element.getTextTrim());
			}
		} catch (Exception e) {

		}
		return map;
	}

	// ��ȡip��ַ
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * ��ȡmac��ַ
	 * 
	 * @param ip
	 * @return
	 */
	public static String getMACAddress(String ip) {
		String str = "";
		String macAddress = "";
		try {
			Process p = Runtime.getRuntime().exec(
					"C:\\Windows\\sysnative\\nbtstat.exe -A " + ip);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			for (int i = 1; i < 100; i++) {
				str = input.readLine();
				if (str != null) {
					if (str.indexOf("MAC Address") > 1) {
						macAddress = str.substring(
								str.indexOf("MAC Address") + 14, str.length());
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.out);
		}
		return macAddress;
	}

	public static void main(String[] args) {
		// System.out.println(getRandomString(32));
		// PrePayReq prePayReq = new PrePayReq();
		// prePayReq.setAppid(Constants.appId);
		// prePayReq.setMch_id(Constants.mch_id);
		// prePayReq.setDevice_info("WEB");
		// prePayReq.setNonce_str(WeixinUtil.getRandomString(32));
		// String outTradeNo = WeixinUtil.getOutTradeNo();
		// prePayReq.setOut_trade_no(outTradeNo);
		// prePayReq.setNotify_url(Constants.notify_url);
		// prePayReq.setTrade_type(Constants.JSAPI);
		//
		// String xml = objToXml(prePayReq);
		// System.out.println(xml);
		// String xml =
		// "<xml><appid><![CDATA[wx95040da38fc72d8e]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><device_info><![CDATA[WEB]]></device_info><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1259469201]]></mch_id><nonce_str><![CDATA[vhsz05j0640gppf3d41zlovtgm31amdf]]></nonce_str><openid><![CDATA[oChsHuEtQM6FRVRJOkQZvdN6V3T4]]></openid><out_trade_no><![CDATA[20160728233216teua7]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[A3FB8D9ECEC2315B2F80F7218F2DB83A]]></sign><time_end><![CDATA[20160728233224]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4009202001201607280005240826]]></transaction_id></xml>";
		// parseXml(xml);
		Map<String, String> map = new HashMap<String, String>();
		map.put("return_code", "SUCCESS");
		map.put("return_msg", "OK");
		String xml = mapToXml(map);
		System.out.println(xml);
	}
}
