package simulator.factories;

import org.json.JSONObject;

import simulator.model.ForceLaws;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<ForceLaws> {
	private final double G = 6.67e-11;
	
	public NewtonUniversalGravitationBuilder() {
		super("nlug", "Fuerza universal de Newton");
	}

	@Override
	protected ForceLaws createTheInstance(JSONObject data) {
		double c = G;
		System.out.println("createtheinstancenlug"+data);
		if(data.has("G"))
			c = data.getDouble("G");
		System.out.println("createtheinstancenlug"+data);

		return new NewtonUniversalGravitation(c);
			
	}

	@Override
	protected JSONObject getBuilderData() {
		JSONObject jo = new JSONObject();
		jo.put("type", this._type);
		JSONObject jo1 = new JSONObject();
		jo1.put("G", "the gravitational constant (a number)");
		jo.put("data", jo1);
		jo.put("desc", this._desc);
		return jo;
	}

}
