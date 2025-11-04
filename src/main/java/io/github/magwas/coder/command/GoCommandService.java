package io.github.magwas.coder.command;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.*;
import io.github.magwas.coder.config.PersonalityData;
import io.github.magwas.coder.config.PersonalityService;
import io.github.magwas.coder.config.WorkflowStepData;
import io.github.magwas.coder.dependencies.SystemWrapper;

@Service
public class GoCommandService implements ProcessingStepService, ErrorMessages {

	@Autowired
	ApplicationContext applicationContext;

	@Autowired
	SystemWrapper system;

	@Autowired
	PersonalityService personality;

	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() != 1)
			return new ProcessingContextData(
					422, "usage: go (expects the current personality in meta['personality'])", context.meta());
		PersonalityData person = personality.apply(context.meta().get("personality"));
		Map<String, Integer> failCounts = new HashMap<>();
		Map<String, WorkflowStepData> stateMachine;
		if (context.meta().containsKey("workflow")
				&& "initial".equals(context.meta().get("workflow"))) stateMachine = person.initialWorkflow();
		else stateMachine = person.workflowSteps();
		String nexStep = "start";
		Map<String, String> meta = Map.of("clearInput", "true");

		while (!"stop".equals(nexStep)) {
			WorkflowStepData step = stateMachine.get(nexStep);
			String name = step.cmd().getFirst();
			if (!failCounts.containsKey(name)) failCounts.put(name, 0);
			ProcessingStepService service =
					applicationContext.getBean(name.replaceFirst("$", "CommandService"), ProcessingStepService.class);
			system.println("-------------------- executing " + name + " #" + failCounts.get(name));
			context = service.apply(context, step.cmd());
			int status = context.status();
			if (status / 100 != 2) {
				int failCount = failCounts.get(name) + 1;
				if (failCount >= step.maxFails()) {
					String failMsg = MessageFormat.format(
							"Failed in step {0} with retry count {1} exceeding:\n{2}",
							nexStep, failCount, context.content());
					return new ProcessingContextData(500, failMsg, meta);
				}
			}
			if (!step.nextSteps().containsKey(status)) {
				String failMsg = MessageFormat.format(
						"No next step defined in state machine for {0} with status {1}:\n{2}",
						nexStep, status, context.content());
				return new ProcessingContextData(501, failMsg, meta);
			}
			nexStep = step.nextSteps().get(status);
			system.println("status: " + status + " nextStep:" + nexStep);
		}
		return context;
	}
}
