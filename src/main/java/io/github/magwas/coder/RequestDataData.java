
package io.github.magwas.coder;

import java.util.Arrays;

public record RequestDataData(
    String model,
    RequestMessageData[] messages
) {}
