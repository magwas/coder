package io.github.magwas.coder.command;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ListCommandsCommandService implements ProcessingStepService {
	@Autowired
	ApplicationContext applicationContext;

	@Override
	public ProcessingContextData apply(ProcessingContextData context, List<String> args) {
		if (args.size() != 1) return new ProcessingContextData(400, "usage: listCommands", context.meta());
		List<String> beanNames = Stream.of(applicationContext.getBeanNamesForType(ProcessingStepService.class))
				.map(name -> applicationContext.getBean(name, ProcessingStepService.class))
				.map(bean -> bean.apply(context, List.of()).content())
				.toList();
		String names = String.join("\n", beanNames);
		return new ProcessingContextData(200, names, context.meta());
	}
}
