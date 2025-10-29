package io.github.magwas.coder;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

@Component
public class ObjectMapperComponent {
	public ObjectMapper getObjectMapper() {
		return JsonMapper.builder().build();
	}
}
