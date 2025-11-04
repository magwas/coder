package io.github.magwas.coder.dependencies;

import org.springframework.stereotype.Component;

@Component
public class TimeWrapper {
	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}
