package org.novula.statemachine;

import org.mockito.InOrder;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

public class StateMachineTest
{
	@Test
	public void simpleTransition()
	{
		// given
		final State<Integer> stateA = new State<Integer>("A")
				.addTransition(1, new Transition<>(new State<>("B")));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(1);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("B");
	}

	@Test
	public void simpleTransitionWithAction()
	{
		// given
		final TransitionAction transitionAction = mock(TransitionAction.class);
		final State<Integer> stateA = new State<Integer>("A")
				.addTransition(1, new Transition<Integer>(new State<>("B")).withAction(transitionAction));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(1);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("B");

		verify(transitionAction).perform();
	}

	@Test
	public void simpleSequenceOfTransitions()
	{
		// given
		final State<Integer> stateA = new State<>("A");
		final State<Integer> stateB = new State<>("B");
		final State<Integer> stateC = new State<>("C");
		final State<Integer> stateD = new State<>("D");

		stateA.addTransition(1, new Transition<>(stateB));
		stateB.addTransition(3, new Transition<>(stateC));
		stateC.addTransition(14, new Transition<>(stateD));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(1);
		stateMachine.actuate(3);
		stateMachine.actuate(14);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("D");
	}

	@Test
	public void simpleSequenceOfTransitionsWithActions()
	{
		// given
		final State<Integer> stateA = new State<>("A");
		final State<Integer> stateB = new State<>("B");
		final State<Integer> stateC = new State<>("C");
		final State<Integer> stateD = new State<>("D");

		final TransitionAction transitionActionAB = mock(TransitionAction.class);
		final TransitionAction transitionActionCD = mock(TransitionAction.class);

		stateA.addTransition(1, new Transition<>(stateB).withAction(transitionActionAB));
		stateB.addTransition(3, new Transition<>(stateC));
		stateC.addTransition(14, new Transition<>(stateD).withAction(transitionActionCD));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(1);
		stateMachine.actuate(3);
		stateMachine.actuate(14);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("D");

		final InOrder inOrderInvocation = Mockito.inOrder(transitionActionAB, transitionActionCD);
		inOrderInvocation.verify(transitionActionAB).perform();
		inOrderInvocation.verify(transitionActionCD).perform();
	}

	@Test
	public void simpleCircleOfTransitionsWithActions()
	{
		// given
		final State<Integer> stateA = new State<>("A");
		final State<Integer> stateB = new State<>("B");
		final State<Integer> stateC = new State<>("C");

		final TransitionAction transitionActionAB = mock(TransitionAction.class);
		final TransitionAction transitionActionCA = mock(TransitionAction.class);

		stateA.addTransition(1, new Transition<>(stateB).withAction(transitionActionAB));
		stateB.addTransition(3, new Transition<>(stateC));
		stateC.addTransition(14, new Transition<>(stateA).withAction(transitionActionCA));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(1);
		stateMachine.actuate(3);
		stateMachine.actuate(14);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("A");

		final InOrder inOrderInvocation = Mockito.inOrder(transitionActionAB, transitionActionCA);
		inOrderInvocation.verify(transitionActionAB).perform();
		inOrderInvocation.verify(transitionActionCA).perform();
	}

	@Test
	public void multiplePossibleTransitions()
	{
		// given
		final State<Integer> stateA = new State<>("A");
		final State<Integer> stateB = new State<>("B");
		final State<Integer> stateC = new State<>("C");
		final State<Integer> stateD = new State<>("D");

		stateA
				.addTransition(1, new Transition<>(stateB))
				.addTransition(3, new Transition<>(stateC))
				.addTransition(14, new Transition<>(stateD));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(14);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("D");
	}

	@Test
	public void multiplePossibleTransitionsWithActions()
	{
		// given
		final State<Integer> stateA = new State<>("A");
		final State<Integer> stateB = new State<>("B");
		final State<Integer> stateC = new State<>("C");
		final State<Integer> stateD = new State<>("D");

		final TransitionAction transitionActionAB = mock(TransitionAction.class);
		final TransitionAction transitionActionAC = mock(TransitionAction.class);
		final TransitionAction transitionActionAD = mock(TransitionAction.class);
		stateA
				.addTransition(1, new Transition<>(stateB).withAction(transitionActionAB))
				.addTransition(3, new Transition<>(stateC).withAction(transitionActionAC))
				.addTransition(14, new Transition<>(stateD).withAction(transitionActionAD));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(14);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("D");

		verify(transitionActionAD).perform();
		verifyZeroInteractions(transitionActionAB, transitionActionAC);
	}

	@Test
	public void machineWithOneStateOnly()
	{
		// given
		final State<Integer> stateA = new State<>("A");

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(1);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("A");
	}

	@Test
	public void multiplePossibleTransitionsWithActionsButNoExpectedSignal()
	{
		// given
		final State<Integer> stateA = new State<>("A");
		final State<Integer> stateB = new State<>("B");
		final State<Integer> stateC = new State<>("C");

		final TransitionAction transitionActionAB = mock(TransitionAction.class);
		final TransitionAction transitionActionAC = mock(TransitionAction.class);
		stateA
				.addTransition(1, new Transition<>(stateB).withAction(transitionActionAB))
				.addTransition(3, new Transition<>(stateC).withAction(transitionActionAC));

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(2);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("A");

		verifyZeroInteractions(transitionActionAB, transitionActionAC);
	}

	@Test
	public void multiplePossibleTransitionsWithActionsButNoExpectedSignalAndDefaultAction()
	{
		// given
		final State<Integer> stateA = new State<>("A");
		final State<Integer> stateB = new State<>("B");
		final State<Integer> stateC = new State<>("C");

		final TransitionAction transitionActionAB = mock(TransitionAction.class);
		final TransitionAction transitionActionAC = mock(TransitionAction.class);
		stateA
				.addTransition(1, new Transition<>(stateB).withAction(transitionActionAB))
				.addTransition(3, new Transition<>(stateC).withAction(transitionActionAC));

		final TransitionAction actionOnWastedImpulse = mock(TransitionAction.class);
		stateA.setActionOnWastedImpulse(actionOnWastedImpulse);

		final StateMachine<Integer> stateMachine = new StateMachine<>(stateA);

		// when
		stateMachine.actuate(2);

		// then
		final State<Integer> currentState = stateMachine.getCurrentState();
		assertThat(currentState.getLabel()).isEqualTo("A");

		verify(actionOnWastedImpulse).perform();
		verifyZeroInteractions(transitionActionAB, transitionActionAC);
	}
}
