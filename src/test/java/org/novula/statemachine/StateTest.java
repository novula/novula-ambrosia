package org.novula.statemachine;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StateTest
{
	@Test
	public void stateWithoutTransitions()
	{
		// when
		final State<Character> state = new State<>("label");

		// then
		assertThat(state.getLabel()).isEqualTo("label");
		assertThat(state.getActionOnWastedImpulse()).isNotNull();
	}

	@Test
	public void stateWithTransition()
	{
		// given
		final State<Character> state = new State<>("label");

		// when
		state.addTransition('f', new Transition<>(new State<>("a state")));

		// then
		assertThat(state.getTransition('f').isPresent()).isTrue();
		assertThat(state.getTransition('f').get().getFollowingState()).isNotNull();
		assertThat(state.getTransition('f').get().getFollowingState().getLabel()).isEqualTo("a state");
	}
}
