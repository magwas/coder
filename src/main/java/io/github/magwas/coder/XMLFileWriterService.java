package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class XMLFileWriterService {
	@Autowired
	private FileWriterService fileWriterService;

	@Autowired
	private FileDeletionService fileDeletionService;

	@Autowired
	private DirectoryComponent directoryComponent;

	public Void apply(String xmlContent) throws ParserConfigurationException, IOException, SAXException {
		Path currentDir = directoryComponent.getCurrentDir();

		for (FileToWriteData file : XmlProcessingUtil.parseFilesToWrite(xmlContent)) {
			Path absolutePath = currentDir.resolve(file.fileName()).normalize();
			validatePath(absolutePath, currentDir);
			fileWriterService.apply(absolutePath.toString(), XmlUtil.unescapeXml(file.content()));
		}

		for (FileToDeleteData file : XmlProcessingUtil.parseFilesToDelete(xmlContent)) {
			Path absolutePath = currentDir.resolve(file.fileName()).normalize();
			validatePath(absolutePath, currentDir);
			fileDeletionService.apply(absolutePath.toString());
		}

		return null;
	}

	private void validatePath(Path path, Path currentDir) {
		if (!PathUtil.isPathAllowed(path, currentDir)) {
			throw new RuntimeException(ErrorMessages.PATH_TRAVERSAL_ERROR);
		}
	}
}
