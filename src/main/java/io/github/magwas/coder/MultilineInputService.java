package io.github.magwas.coder;

import org.springframework.stereotype.Service;

@Service
public class MultilineInputService implements UIConstants {
	public MultilineInputProcessingResultData apply(String line, StringBuilder input) {
		if (MULTILINE_END.equals(line)) {
			return new MultilineInputProcessingResultData(true, input);
		}
		input.append(line).append("\n");
		return new MultilineInputProcessingResultData(false, input);
	}
}
