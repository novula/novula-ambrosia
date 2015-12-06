package org.novula.statemachine;

public interface StateMachineFactory<ImpulseT>
{
	StateMachine<ImpulseT> create();
}
