package org.novula.statemachine;

import java.util.Optional;

public class StateMachine<ImpulseT>
{
	private State<ImpulseT> currentState;

	public StateMachine(final State<ImpulseT> initialState)
	{
		currentState = initialState;
	}

	public void actuate(final ImpulseT impulse)
	{
		final Optional<Transition<ImpulseT>> foundTransition = currentState.getTransition(impulse);
		if (foundTransition.isPresent())
		{
			final Transition<ImpulseT> transition = foundTransition.get();
			transition.getTransitionAction().perform();
			currentState = transition.getFollowingState();
		}
		else
		{
			currentState.getActionOnWastedImpulse().perform();
		}
	}

	public State<ImpulseT> getCurrentState()
	{
		return currentState;
	}
}
