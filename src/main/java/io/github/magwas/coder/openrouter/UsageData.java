package io.github.magwas.coder.openrouter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsageData(Integer prompt_tokens, Integer completion_tokens) {}
