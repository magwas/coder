package io.github.magwas.coder.command;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class ExitCommandService implements ProcessingStepService {
	public ProcessingContextData apply(ProcessingContextData contextData, List<String> args) {
		if (args.size() != 1) return new ProcessingContextData(400, "usage: exit", contextData.meta());

		return new ProcessingContextData(200, "exit command", Map.of("exit", "true"));
	}
}
