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
		if(data.has("G"))
			c = data.getDouble("G");
		return new NewtonUniversalGravitation(c);
			
	}

	@Override
	protected JSONObject getBuilderData() {
		JSONObject JASON = new JSONObject();
		JASON.put("G", "Constante graviatacional, default: 6.67e-11 ");	
		return JASON;
	}

}
