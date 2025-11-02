package io.github.magwas.coder;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Component
public class ObjectMapperWrapper {
	public final ObjectMapper objectMapper;

	public ObjectMapperWrapper() {
		this.objectMapper = JsonMapper.builder().build();
	}
}
