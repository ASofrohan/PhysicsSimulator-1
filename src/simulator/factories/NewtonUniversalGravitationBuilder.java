package simulator.factories;


import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	
	private static final double g = 6.67e-11;
	
	public NewtonUniversalGravitationBuilder() {
		TypeTag = "nlug";			
		desc = "NewtonUniversalGravitation";
	}
	
	@Override
	protected NewtonUniversalGravitation createTheInstance(JSONObject jo) {
		double g = NewtonUniversalGravitationBuilder.g;
		if(jo.has("G")) g = jo.getDouble("G");
		return new NewtonUniversalGravitation(g);
	}
	
	@Override
	public JSONObject createData() {
		JSONObject obj = new JSONObject();
		obj.put("G", g);
		return obj;
	}
}
