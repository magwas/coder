package io.github.magwas.coder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConfigLoadService {
	@Autowired
	private ConfigState configState;

	@Autowired
	private ObjectMapperComponent objectMapperComponent;

	public Void apply() throws IOException {
		Path configFile = Path.of(System.getProperty("user.home"), ".coder", "coder.conf");
		if (Files.exists(configFile)) {
			String content = Files.readString(configFile);
			ObjectMapper mapper = objectMapperComponent.getObjectMapper();
			configState.configData = mapper.readValue(content, ConfigData.class);
		} else {
			throw new IOException("Config file not found: " + configFile);
		}
		return null;
	}
}
