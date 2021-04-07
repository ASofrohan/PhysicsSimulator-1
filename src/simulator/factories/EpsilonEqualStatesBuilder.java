package simulator.factories;

import org.json.JSONObject;

import simulator.control.EpsilonEqualStates;
import simulator.control.StateComparator;

public class EpsilonEqualStatesBuilder extends Builder<StateComparator> {
	
	private final double m = 0.0;
	
	public EpsilonEqualStatesBuilder() {
		super("epseq", "Comparador modulo epsilon");
	}

	@Override
	protected StateComparator createTheInstance(JSONObject jo) {
		double eps = this.m;
		if(jo.has("eps")) {
			eps = jo.getDouble("eps");
			if(eps <= 0) {
				throw new IllegalArgumentException("Epsilon menor o igual que cero");
			}
		}
		return new EpsilonEqualStates(eps);
	}

	@Override
	protected JSONObject getBuilderData() {
		JSONObject jason = new JSONObject();
		jason.put("eps", "valor del modulo epsilon");
		return jason;
	}

}
