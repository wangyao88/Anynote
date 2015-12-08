package util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * * ��ȡ��������IP��ַ ˼���Ƿ�����վhttp://checkip.dyndns.org/���õ����ص��ı��������������������IP��ַ
 * 
 * @author Administrator
 * 
 */
public class ExternalIpAddressFetcher {
	// ����IP�ṩ�ߵ���ַ 
	private String externalIpProviderUrl;

	// ��������IP��ַ 
	private String myExternalIpAddress;

	public ExternalIpAddressFetcher(String externalIpProviderUrl) {
		this.externalIpProviderUrl = externalIpProviderUrl;

		String returnedhtml = fetchExternalIpProviderHTML(externalIpProviderUrl);

		parse(returnedhtml);
	}

	/**
	 * * �������ṩ�ߴ���ð�������������ַ���ַ��� ��http://checkip.dyndns.org���ص��ַ�������
	 * <html><head><title>Current IP Check</title></head><body>Current IP
	 * Address: 123.147.226.222</body></html>
	 * 
	 * @param externalIpProviderUrl
	 * @return String ����IP
	 */
	private String fetchExternalIpProviderHTML(String externalIpProviderUrl) {
		// ������ 
		InputStream in = null;

		// �������ṩ�ߵ�Http����
		HttpURLConnection httpConn = null;

		try {
			// ������
			URL url = new URL(externalIpProviderUrl);
			httpConn = (HttpURLConnection) url.openConnection();

			// �������� 
			HttpURLConnection.setFollowRedirects(true);
			httpConn.setRequestMethod("GET");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows 2000)");

			// ��ȡ���ӵ������� 
			in = httpConn.getInputStream();
			byte[] bytes = new byte[1024];// �˴�С�ɸ���ʵ���������

			// ��ȡ�������� 
			int offset = 0;
			int numRead = 0;
			while (offset < bytes.length
					&& (numRead = in.read(bytes, offset, bytes.length - offset)) >= 0) {
				offset += numRead;
			}

			// ���ֽ�ת��ΪΪUTF-8���ַ��� 
			String receivedString=new String(bytes,"UTF-8");

			// ���� 
			return receivedString;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
				httpConn.disconnect();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		// �����쳣�򷵻ؿ� 
		return null;
	}

	/**
	 * * ʹ��������ʽ�������ص�HTML�ı�,�õ�����������ַ
	 * 
	 * @param html
	 */
	private void parse(String html) {
		Pattern pattern = Pattern.compile("(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(html);
		while (matcher.find()) {
			myExternalIpAddress = matcher.group(0);
		}
	}

	/**
	 * * �õ�����������ַ,�ò�����Ϊ��
	 * @return ����IP
	 */
	public String getMyExternalIpAddress() {
		return myExternalIpAddress;
	}

	public static void main(String[] args) {
		ExternalIpAddressFetcher fetcher = new ExternalIpAddressFetcher(
				"http://checkip.dyndns.org/");

		System.out.println(fetcher.getMyExternalIpAddress());
	}
}