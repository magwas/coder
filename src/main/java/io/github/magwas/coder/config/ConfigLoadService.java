package io.github.magwas.coder.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.magwas.coder.ErrorMessages;
import io.github.magwas.coder.conversation.ConversationState;
import io.github.magwas.coder.dependencies.ObjectMapperWrapper;

@Service
public class ConfigLoadService {
	@Autowired
	private ConfigState configState;

	@Autowired
	ConversationState conversation;

	@Autowired
	private ObjectMapperWrapper objectMapperWrapper;

	public Void apply() throws IOException {
		conversation.history = new ArrayList<>();
		Path configFile = Path.of(System.getProperty("user.home"), ".coder", "coder.conf");
		if (Files.exists(configFile)) {
			String content = Files.readString(configFile);
			ObjectMapper mapper = objectMapperWrapper.objectMapper;
			configState.configData = mapper.readValue(content, ConfigData.class);
		} else {
			throw new IOException(String.format(ErrorMessages.CONFIG_FILE_NOT_FOUND, configFile));
		}
		return null;
	}
}
