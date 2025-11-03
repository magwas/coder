package io.github.magwas.coder;

import org.springframework.stereotype.Service;

@Service
public class MultilineProcessorService implements UIConstants {

	public MultilineProcessingResult apply(String line, StringBuilder buffer, PersonalityData personality) {
		if (MULTILINE_END.equals(line)) {
			return new MultilineProcessingResult(true, buffer);
		}
		if (!buffer.isEmpty()) {
			buffer.append("\n");
		}
		buffer.append(line);
		return new MultilineProcessingResult(false, buffer);
	}
}
