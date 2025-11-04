package io.github.magwas.coder.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.dependencies.ProcessBuilderWrapper;

@Service
public class CommandExecutionService {
	@Autowired
	private ProcessBuilderWrapper processBuilderWrapper;

	public String apply(String command) throws IOException, InterruptedException {
		Process process = processBuilderWrapper
				.getBuilder()
				.command("sh", "-c", command)
				.redirectError(ProcessBuilder.Redirect.INHERIT)
				.start();

		process.waitFor();

		try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			return reader.lines().collect(Collectors.joining("\n"));
		}
	}
}
