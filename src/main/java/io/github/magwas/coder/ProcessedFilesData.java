package io.github.magwas.coder;

import java.util.List;

public record ProcessedFilesData(List<String> modifiedFiles, List<String> deletedFiles) {}
