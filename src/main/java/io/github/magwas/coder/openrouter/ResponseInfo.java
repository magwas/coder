package io.github.magwas.coder.openrouter;

public record ResponseInfo(long duration, int statusCode, String reasoning, String content, UsageData usage) {}
