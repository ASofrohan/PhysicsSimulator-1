package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NoForce;

public class NoForceBuilder extends Builder<ForceLaws> {

	public NoForceBuilder() {
		TypeTag = "nf";			
		desc = "NoForce";
	}
	
	@Override
	protected NoForce createTheInstance(JSONObject jo) {
		return new NoForce();
	}
	
	@Override
	public JSONObject createData() {
		return new JSONObject(); 
	}

}
