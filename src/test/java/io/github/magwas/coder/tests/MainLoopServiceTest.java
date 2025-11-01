package io.github.magwas.coder.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import io.github.magwas.coder.MainLoopService;
import io.github.magwas.coder.OpenRouterClientService;
import io.github.magwas.coder.PersonalityData;
import io.github.magwas.coder.PersonalityService;
import io.github.magwas.coder.SystemExitSimulationException;
import io.github.magwas.coder.config.ConfigLoadService;
import io.github.magwas.coder.conversation.ConversationClearService;
import io.github.magwas.coder.conversation.ConversationHasSystemInstructionsService;
import io.github.magwas.coder.conversation.ConversationSizeService;
import io.github.magwas.konveyor.testing.TestBase;

public class MainLoopServiceTest extends TestBase implements MainLoopTestData {

	@InjectMocks
	private MainLoopService underTest;

	@Mock
	private OpenRouterClientService openRouterClientService;

	@Mock
	private ConversationClearService conversationClearService;

	@Mock
	private ConversationSizeService conversationSizeService;

	@Mock
	private ConversationHasSystemInstructionsService conversationHasSystemInstructionsService;

	@Mock
	private ConfigLoadService configLoadService;

	@Mock
	private PersonalityService personalityService;

	@BeforeEach
	public void setUp() throws Throwable {
		super.setUp();
		doNothing().when(configLoadService).apply();
		PersonalityData personality = new PersonalityData("coder", "model", "instructions.txt");
		when(personalityService.apply("coder")).thenReturn(personality);
	}

	@Test
	@DisplayName("Exit command terminates loop")
	void testExitCommand() {
		given(EXIT_COMMAND_STATE);
		assertThrows(SystemExitSimulationException.class, () -> underTest.apply());
	}

	@Test
	@DisplayName("Clear command clears conversation history")
	void testClearCommand() {
		given(CLEAR_COMMAND_STATE);
		assertThrows(SystemExitSimulationException.class, () -> underTest.apply());
		verify(conversationClearService).apply();
	}

	@Test
	@DisplayName("History command shows message count")
	void testHistoryCommand() {
		given(HISTORY_COMMAND_STATE);
		assertThrows(SystemExitSimulationException.class, () -> underTest.apply());
		verify(conversationSizeService).apply();
		verify(SystemDependencyStub.printlnMock).accept(HISTORY_MESSAGE + 5);
	}

	@Test
	@DisplayName("Instructions command shows status")
	void testInstructionsCommand() {
		given(INSTRUCTIONS_COMMAND_STATE);
		assertThrows(SystemExitSimulationException.class, () -> underTest.apply());
		verify(conversationHasSystemInstructionsService).apply();
	}

	@Test
	@DisplayName("Normal user input processes question")
	void testNormalInput() {
		given(NORMAL_INPUT_STATE);
		assertThrows(SystemExitSimulationException.class, () -> underTest.apply());
		verify(openRouterClientService).apply("coder", ANY_QUESTION);
	}

	@Test
	@DisplayName("Error during processing shows error message")
	void testProcessingError() {
		given(ERROR_STATE);
		assertThrows(SystemExitSimulationException.class, () -> underTest.apply());
		verify(openRouterClientService).apply("coder", ANY_QUESTION);
		verify(SystemDependencyStub.printlnMock).accept(ERROR_PREFIX + "Simulated error");
	}
}
