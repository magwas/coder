package io.github.magwas.coder.file;

public final class XmlUtil {
	private XmlUtil() {}

	public static String unescapeXml(String content) {
		return content.replace("&amp;", "&")
				.replace("&lt;", "<")
				.replace("&gt;", ">")
				.replace("&quot;", "\"")
				.replace("&apos;", "'");
	}
}
