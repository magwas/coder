package io.github.magwas.coder.command;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.magwas.coder.UIConstants;
import io.github.magwas.coder.config.ConfigState;

@Service
public class PersonalityCommandService implements ProcessingStepService, UIConstants {

	@Autowired
	ConfigState config;

	public ProcessingContextData apply(ProcessingContextData contextData, List<String> args) {
		if (args.size() != 2) {
			return new ProcessingContextData(404, PERSONALITY_USAGE, Map.of());
		} else {
			String name = args.get(1);
			if (config.configData.personalities().stream()
					.anyMatch(p -> p.name().equals(name))) {
				return new ProcessingContextData(
						200, String.format(PERSONALITY_CHANGED, name), Map.of("newPersonality", name));
			}
			return new ProcessingContextData(404, "personality not found: " + name, Map.of());
		}
	}
}
