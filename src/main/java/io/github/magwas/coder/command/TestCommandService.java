package io.github.magwas.coder.command;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.RunCommandService;

@Service
public class TestCommandService implements ProcessingStepService {

	@Autowired
	RunCommandService runCommandService;

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() < 2) return new ProcessingContextData(500, "usage: test testCommand", context.meta());
		String testCommand = args.get(1);

		try {
			String result = runCommandService.apply(testCommand);
			if (result.isEmpty()) {
				return new ProcessingContextData(200, testCommand, context.meta());
			} else {
				return new ProcessingContextData(500, result, context.meta());
			}
		} catch (Exception e) {
			String errorMsg = MessageFormat.format(
					"""
			Test Execution Error {0}
			while executing {1}
			Stack trace:
			{2}
			""",
					e.getMessage(), testCommand, ExceptionUtils.getStackTrace(e));
			return new ProcessingContextData(501, errorMsg, context.meta());
		}
	}
}
