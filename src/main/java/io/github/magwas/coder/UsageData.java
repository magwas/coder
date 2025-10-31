package io.github.magwas.coder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record UsageData(Integer prompt_tokens, Integer completion_tokens) {}
