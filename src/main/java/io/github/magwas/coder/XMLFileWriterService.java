package io.github.magwas.coder;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class XMLFileWriterService {
	@Autowired
	private FileWriterService fileWriterService;

	@Autowired
	private FileDeletionService fileDeletionService;

	public Void apply(String xmlContent) {
		try {
			Path currentDir = Paths.get("").toAbsolutePath();

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
		} catch (Exception e) {
			throw new RuntimeException(ErrorMessages.FILE_ERROR, e);
		}
	}

	private void validatePath(Path path, Path currentDir) {
		if (!PathUtil.isPathAllowed(path, currentDir)) {
			throw new RuntimeException(ErrorMessages.PATH_TRAVERSAL_ERROR);
		}
	}
}
