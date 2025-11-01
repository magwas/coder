package io.github.magwas.coder;

import org.springframework.stereotype.Component;

@Component
public class TimeDependency {
	public long currentTimeMillis() {
		return System.currentTimeMillis();
	}
}
