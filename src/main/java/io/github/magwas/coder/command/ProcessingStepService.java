package io.github.magwas.coder.command;

import java.util.List;

import io.github.magwas.konveyor.annotations.Glue;

@Glue
public interface ProcessingStepService {
	ProcessingContextData apply(ProcessingContextData context, List<String> args);
}
