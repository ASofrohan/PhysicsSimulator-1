package simulator.factories;

import org.json.JSONObject;
import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	
	private static final double eps = 0.1;

	public EpsilonEqualStatesBuilder() {
		TypeTag = "epseq";			
		desc = "EspilonEqualStatesComparator";
	}
	
	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		double eps = EpsilonEqualStatesBuilder.eps;
		if(jo.has("eps")) eps = jo.getDouble("eps");
		return new EpsilonEqualStates(eps);
	}
	
	@Override
	public JSONObject createData() {
		JSONObject obj = new JSONObject();
		obj.put("eps", eps);
		return obj; 
	}
}
