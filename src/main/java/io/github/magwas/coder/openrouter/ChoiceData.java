package io.github.magwas.coder.openrouter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ChoiceData(MessageData message) {}
