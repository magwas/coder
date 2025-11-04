package io.github.magwas.coder.openrouter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenRouterResponseData(ChoiceData[] choices, UsageData usage) {}
