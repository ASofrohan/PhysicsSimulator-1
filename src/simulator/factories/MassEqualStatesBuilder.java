package simulator.factories;

import org.json.JSONObject;

import simulator.control.MassEqualStates;
import simulator.control.StateComparator;

public class MassEqualStatesBuilder extends Builder<StateComparator> {

	public MassEqualStatesBuilder() {
		TypeTag = "masseq";			
		desc = "MassEqualStatesComparator";
	}
	
	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		return new MassEqualStates();
	}
	
	@Override
	public JSONObject createData() {
		return new JSONObject(); 
	}

}
