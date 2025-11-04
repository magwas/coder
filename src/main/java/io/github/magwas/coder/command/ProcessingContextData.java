package io.github.magwas.coder.command;

import java.util.Map;

public record ProcessingContextData(int status, String content, Map<String, String> meta) {}
