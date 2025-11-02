package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

@Service
public class XMLFileWriterService implements ErrorMessages, FormattingConstants {
	@Autowired
	FileWriterService fileWriterService;

	@Autowired
	FileDeletionService fileDeletionService;

	@Autowired
	DirectoryWrapper directory;

	@Autowired
	SystemWrapper system;

	public void apply(String xmlContent) throws ParserConfigurationException, IOException, SAXException {
		Path currentDir = directory.getCurrentDir();

		List<String> modifiedFiles = processModifiedFiles(xmlContent, currentDir);
		List<String> deletedFiles = processDeletedFiles(xmlContent, currentDir);

		outputResults(modifiedFiles, deletedFiles);
	}

	private List<String> processModifiedFiles(String xmlContent, Path currentDir)
			throws ParserConfigurationException, IOException, SAXException {
		List<String> modifiedFiles = new ArrayList<>();
		for (FileToWriteData file : XmlProcessingUtil.parseFilesToWrite(xmlContent)) {
			Path absolutePath = currentDir.resolve(file.fileName()).normalize();
			validatePath(absolutePath, currentDir);
			fileWriterService.apply(absolutePath.toString(), XmlUtil.unescapeXml(file.content()));
			modifiedFiles.add(file.fileName());
		}
		return modifiedFiles;
	}

	private List<String> processDeletedFiles(String xmlContent, Path currentDir)
			throws ParserConfigurationException, IOException, SAXException {
		List<String> deletedFiles = new ArrayList<>();
		for (FileToDeleteData file : XmlProcessingUtil.parseFilesToDelete(xmlContent)) {
			Path absolutePath = currentDir.resolve(file.fileName()).normalize();
			validatePath(absolutePath, currentDir);
			fileDeletionService.apply(absolutePath.toString());
			deletedFiles.add(file.fileName());
		}
		return deletedFiles;
	}

	private void outputResults(List<String> modifiedFiles, List<String> deletedFiles) {
		StringBuilder sb = new StringBuilder();
		if (!modifiedFiles.isEmpty()) {
			sb.append(MODIFIED_FILES_HEADER);
			modifiedFiles.forEach(f -> sb.append("- ").append(f).append("\n"));
		}
		if (!deletedFiles.isEmpty()) {
			sb.append(DELETED_FILES_HEADER);
			deletedFiles.forEach(f -> sb.append("- ").append(f).append("\n"));
		}
		system.println(sb.toString());
	}

	private void validatePath(Path path, Path currentDir) {
		if (!PathUtil.isPathAllowed(path, currentDir)) {
			throw new RuntimeException(PATH_TRAVERSAL_ERROR);
		}
	}
}
