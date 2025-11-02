package io.github.magwas.coder;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RunCommandService implements ErrorMessages {
	@Autowired
	private CommandExecutionService commandExecutionService;

	public String apply(String command) {
		try {
			return commandExecutionService.apply(command);
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(String.format(COMMAND_EXECUTION_ERROR, command), e);
		}
	}
}
