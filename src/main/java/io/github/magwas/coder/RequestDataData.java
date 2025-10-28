package io.github.magwas.coder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RequestDataData(String model, RequestMessageData[] messages) {}
