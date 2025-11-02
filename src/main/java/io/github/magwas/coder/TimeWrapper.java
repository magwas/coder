package io.github.magwas.coder;

import org.springframework.stereotype.Component;

@Component
public class TimeWrapper {
	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}
