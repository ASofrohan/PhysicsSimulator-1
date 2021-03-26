package simulator.factories;


import org.json.JSONObject;
import simulator.model.NewtonUniversalGravitation;

public class NewtonUniversalGravitationBuilder extends Builder<NewtonUniversalGravitation> {

	public NewtonUniversalGravitationBuilder() {
		TypeTag = "nlug";			
		desc = "NewtonUniversalGravitation";
	}
	
	@Override
	protected NewtonUniversalGravitation createTheInstance(JSONObject jo) {
		double g = jo.getDouble("G");
		return new NewtonUniversalGravitation(g);
	}
	
	@Override
	public JSONObject createData() {
		JSONObject obj = new JSONObject();
		obj.put("G", 6.67e-11);
		return obj;
	}
}
