package io.github.magwas.coder;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public final class XmlProcessingUtil {
	private XmlProcessingUtil() {}

	public static List<FileToWriteData> parseFilesToWrite(String xmlContent) throws Exception {
		List<FileToWriteData> files = new ArrayList<>();
		Document doc = parseXml(xmlContent);
		NodeList fileNodes = doc.getElementsByTagName("file");
		for (int i = 0; i < fileNodes.getLength(); i++) {
			Element fileElement = (Element) fileNodes.item(i);
			files.add(new FileToWriteData(fileElement.getAttribute("name"), fileElement.getTextContent()));
		}
		return files;
	}

	public static List<FileToDeleteData> parseFilesToDelete(String xmlContent) throws Exception {
		List<FileToDeleteData> deletions = new ArrayList<>();
		Document doc = parseXml(xmlContent);
		NodeList deletedNodes = doc.getElementsByTagName("deleted");
		for (int i = 0; i < deletedNodes.getLength(); i++) {
			Element deletedElement = (Element) deletedNodes.item(i);
			deletions.add(new FileToDeleteData(deletedElement.getAttribute("name")));
		}
		return deletions;
	}

	private static Document parseXml(String xmlContent) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(new InputSource(new StringReader(xmlContent)));
	}
}
