package io.github.magwas.coder.command;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.dependencies.SystemWrapper;

@Service
public class PrintContentCommandService implements ProcessingStepService {
	@Autowired
	SystemWrapper system;

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() != 1) return new ProcessingContextData(400, "usage: printContent", context.meta());

		system.println(context.content());
		return context;
	}
}
