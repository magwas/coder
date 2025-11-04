package io.github.magwas.coder.config;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WorkflowStepData(List<String> cmd, int maxFails, Map<Integer, String> nextSteps) {}
