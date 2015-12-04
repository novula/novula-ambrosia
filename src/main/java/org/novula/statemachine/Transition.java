package org.novula.statemachine;

public class Transition<ImpulseT>
{
	private static final TransitionAction EMPTY_TRANSITION_ACTION = () -> {};

	private final State<ImpulseT> followingState;

	private TransitionAction transitionAction = EMPTY_TRANSITION_ACTION;

	public Transition(final State<ImpulseT> followingState)
	{
		this.followingState = followingState;
	}

	public Transition<ImpulseT> withAction(final TransitionAction transitionAction)
	{
		this.transitionAction = transitionAction;
		return this;
	}

	public State<ImpulseT> getFollowingState()
	{
		return followingState;
	}

	public TransitionAction getTransitionAction()
	{
		return transitionAction;
	}
}
