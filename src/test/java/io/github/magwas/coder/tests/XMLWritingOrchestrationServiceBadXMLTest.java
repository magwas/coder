package io.github.magwas.coder.tests;

import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.magwas.coder.PersonalityData;
import io.github.magwas.coder.ResponseInfo;
import io.github.magwas.coder.SystemWrapper;
import io.github.magwas.coder.XMLFileWriterService;
import io.github.magwas.coder.XMLWritingOrchestrationService;
import io.github.magwas.konveyor.testing.TestBase;

public class XMLWritingOrchestrationServiceBadXMLTest extends TestBase {

	@InjectMocks
	private XMLWritingOrchestrationService service;

	@Mock
	private XMLFileWriterService xmlFileWriter;

	@Mock
	private SystemWrapper systemDependency;

	@Test
	@DisplayName("Bad XML response includes exception message in error prompt")
	void testBadXMLIncludesExceptionMessage() throws Exception {
		PersonalityData personality =
				new PersonalityData("test", "", List.of(), true, true, false, true, null, 0, true, true, true, "");

		String errorMessage = "Test XML error";
		ResponseInfo response = new ResponseInfo(0, 200, "", errorMessage, null);

		doThrow(new RuntimeException(errorMessage))
				.doNothing()
				.when(xmlFileWriter)
				.apply(anyString());

		service.apply(personality, new StringBuilder(), response);

		verify(systemDependency)
				.println(argThat(msg ->
						msg.contains(errorMessage) && msg.startsWith("Your response as an AI was not a proper XML")));
	}
}
