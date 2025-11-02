package io.github.magwas.coder;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record OpenRouterRequestData(String model, List<RequestMessageData> messages, ReasoningData reasoning) {}
