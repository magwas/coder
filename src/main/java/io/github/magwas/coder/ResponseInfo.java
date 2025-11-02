package io.github.magwas.coder;

public record ResponseInfo(long duration, int statusCode, String reasoning, String content, UsageData usage) {}
