
package io.github.magwas.coder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class XMLFileWriterService {
    @Autowired 
    private FileWriterService fileWriterService;

    public Void apply(String xmlContent) {
        try {
            Path currentDir = Paths.get("").toAbsolutePath();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));

            NodeList fileNodes = doc.getElementsByTagName("file");
            for (int i = 0; i < fileNodes.getLength(); i++) {
                Element fileElement = (Element) fileNodes.item(i);
                String fileName = fileElement.getAttribute("name");
                
                Path absolutePath = currentDir.resolve(fileName).normalize();
                if (!isPathAllowed(absolutePath, currentDir)) {
                    throw new RuntimeException(ErrorMessages.PATH_TRAVERSAL_ERROR);
                }
                
                String content = fileElement.getTextContent();
                fileWriterService.apply(absolutePath.toString(), XmlUtil.unescapeXml(content));
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException(ErrorMessages.FILE_ERROR, e);
        }
    }
    
    private static boolean isPathAllowed(Path path, Path currentDir) {
        return path.toString().startsWith(currentDir.normalize().toString());
    }
}
