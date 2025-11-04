package io.github.magwas.coder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.command.*;
import io.github.magwas.coder.config.ConfigLoadService;
import io.github.magwas.coder.config.ConfigState;
import io.github.magwas.coder.dependencies.LineReaderWrapper;
import io.github.magwas.coder.dependencies.SystemWrapper;

@Service
public class MainLoopService implements UIConstants {

	@Autowired
	GoCommandService goCommand;

	@Autowired
	SystemWrapper systemDependency;

	@Autowired
	ClearCommandService clearCommand;

	@Autowired
	LineReaderWrapper lineReaderDependency;

	@Autowired
	ConfigLoadService configLoadService;

	@Autowired
	ExitCommandService exitCommand;

	@Autowired
	ConfigState config;

	@Autowired
	PersonalityCommandService personalityCommand;

	public void apply() throws IOException {
		configLoadService.apply();
		String personalityName =
				config.configData.defaultPersonality().isEmpty() ? "coder" : config.configData.defaultPersonality();
		goCommand.apply(
				new ProcessingContextData(200, "", Map.of("workflow", "initial", "personality", personalityName)),
				List.of("go"));
		boolean shouldExit = false;
		StringBuilder input = new StringBuilder();
		while (!shouldExit) {
			String line = lineReaderDependency.reader.readLine(INPUT_PROMPT);
			if (line == null) break;
			List<String> args = List.of(line.split(" "));
			ProcessingContextData ctxIn =
					new ProcessingContextData(200, input.toString(), Map.of("personality", personalityName));
			ProcessingContextData contextData;
			switch (args.getFirst()) {
				case CommandConstants.CMD_EXIT -> contextData = exitCommand.apply(ctxIn, args);
				case CommandConstants.CMD_CLEAR -> contextData = clearCommand.apply(ctxIn, args);
				case CommandConstants.CMD_PERSONALITY -> contextData = personalityCommand.apply(ctxIn, args);
				case MULTILINE_END -> contextData = goCommand.apply(ctxIn, args);
				default -> {
					if (!input.isEmpty()) {
						input.append("\n");
					}
					input.append(line);
					contextData = new ProcessingContextData(200, "", Map.of());
				}
			}
			if (contextData.meta().containsKey("exit")) shouldExit = true;
			if (contextData.meta().containsKey("clearInput")) input.setLength(0);
			if (contextData.status() / 100 != 2) systemDependency.println("EXIT STATUS: " + contextData.status());
			if (!contextData.content().isEmpty()) systemDependency.println(contextData.content());
			if (contextData.meta().containsKey("newPersonality"))
				personalityName = contextData.meta().get("newPersonality");
		}
		systemDependency.println(GOODBYE_MESSAGE);
		systemDependency.exit(0);
	}
}
