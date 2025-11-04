package io.github.magwas.coder.config;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PersonalityData(
		String name, Map<String, WorkflowStepData> workflowSteps, Map<String, WorkflowStepData> initialWorkflow) {}
