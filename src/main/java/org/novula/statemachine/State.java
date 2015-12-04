package org.novula.statemachine;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class State<ImpulseT>
{
	private static final TransitionAction EMPTY_ACTION = () -> {};

	private final String label;
	private final Map<ImpulseT, Transition<ImpulseT>> transitions;

	private TransitionAction actionOnWastedImpulse = EMPTY_ACTION;

	public State(final String label)
	{
		this.label = label;
		transitions = new HashMap<>();
	}

	public State<ImpulseT> addTransition(final ImpulseT impulse, final Transition<ImpulseT> transition)
	{
		transitions.put(impulse, transition);
		return this;
	}

	public String getLabel()
	{
		return label;
	}

	public Optional<Transition<ImpulseT>> getTransition(final ImpulseT impulse)
	{
		return Optional.ofNullable(transitions.get(impulse));
	}

	public TransitionAction getActionOnWastedImpulse()
	{
		return actionOnWastedImpulse;
	}

	public void setActionOnWastedImpulse(final TransitionAction actionOnWastedImpulse)
	{
		this.actionOnWastedImpulse = actionOnWastedImpulse;
	}
}
