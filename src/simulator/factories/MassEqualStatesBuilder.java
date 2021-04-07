package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator> {

	public MassEqualStatesBuilder() {
		super("masseq", "Comprueba igualdad de masas");
	}

	@Override
	protected StateComparator createTheInstance(JSONObject data) {
		return new MassEqualStates();
	}

	@Override
	protected JSONObject getBuilderData() {
		return null;
	}

}
