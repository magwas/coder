package io.github.magwas.coder.command;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.*;
import io.github.magwas.coder.dependencies.DirectoryWrapper;
import io.github.magwas.coder.dependencies.SystemWrapper;
import io.github.magwas.coder.file.*;

@Service
public class WriteFilesCommandService implements ProcessingStepService, FormattingConstants {
	@Autowired
	FileWriterService fileWriterService;

	@Autowired
	FileDeletionService fileDeletionService;

	@Autowired
	DirectoryWrapper directory;

	@Autowired
	SystemWrapper system;

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() != 1) return new ProcessingContextData(400, "usage: writeFiles", context.meta());

		try {
			List<String> modifiedFiles = processModifiedFiles(context.content());
			List<String> deletedFiles = processDeletedFiles(context.content());
			String output = outputResults(modifiedFiles, deletedFiles);
			return new ProcessingContextData(200, output, context.meta());
		} catch (Exception e) {
			system.println("XML processing error: " + e.getMessage());
			return new ProcessingContextData(500, "XML processing error: " + e.getMessage(), context.meta());
		}
	}

	private List<String> processModifiedFiles(String xmlContent) throws Exception {
		List<String> modifiedFiles = new ArrayList<>();
		for (FileToWriteData file : XmlProcessingUtil.parseFilesToWrite(xmlContent)) {
			Path absolutePath =
					directory.getCurrentDir().resolve(file.fileName()).normalize();
			validatePath(absolutePath, directory.getCurrentDir());
			fileWriterService.apply(absolutePath.toString(), XmlUtil.unescapeXml(file.content()));
			modifiedFiles.add(file.fileName());
		}
		return modifiedFiles;
	}

	private List<String> processDeletedFiles(String xmlContent) throws Exception {
		List<String> deletedFiles = new ArrayList<>();
		for (FileToDeleteData file : XmlProcessingUtil.parseFilesToDelete(xmlContent)) {
			Path absolutePath =
					directory.getCurrentDir().resolve(file.fileName()).normalize();
			validatePath(absolutePath, directory.getCurrentDir());
			fileDeletionService.apply(absolutePath.toString());
			deletedFiles.add(file.fileName());
		}
		return deletedFiles;
	}

	private String outputResults(List<String> modifiedFiles, List<String> deletedFiles) {
		StringBuilder sb = new StringBuilder();
		if (!modifiedFiles.isEmpty()) {
			sb.append(MODIFIED_FILES_HEADER);
			modifiedFiles.forEach(f -> sb.append("- ").append(f).append("\n"));
		}
		if (!deletedFiles.isEmpty()) {
			sb.append(DELETED_FILES_HEADER);
			deletedFiles.forEach(f -> sb.append("- ").append(f).append("\n"));
		}
		return sb.toString();
	}

	private void validatePath(Path path, Path currentDir) {
		if (PathUtil.isPathDisAllowed(path, currentDir)) {
			throw new RuntimeException(ErrorMessages.PATH_TRAVERSAL_ERROR);
		}
	}
}
