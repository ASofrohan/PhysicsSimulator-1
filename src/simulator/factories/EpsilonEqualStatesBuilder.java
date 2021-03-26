package simulator.factories;

import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {

	public EpsilonEqualStatesBuilder() {
		TypeTag = "masseq";			
		desc = "MassEqualStatesComparator";
	}
	
	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		double eps = jo.getDouble("eps");
		return new EpsilonEqualStates(eps);
	}
	
	@Override
	public JSONObject createData() {
		JSONObject obj = new JSONObject();
		obj.put("eps", 0.1);
		return obj; 
	}
}
