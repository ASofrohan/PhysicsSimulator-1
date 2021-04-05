package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	public MovingTowardsFixedPointBuilder() {
		TypeTag = "mtfp";			
		desc = "MovingTowardsFixedPoint";
	}
	
	@Override
	protected MovingTowardsFixedPoint createTheInstance(JSONObject jo) {
		JSONArray vector = jo.getJSONArray("c");
		Vector2D c = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		double g = jo.getDouble("g");
		return new MovingTowardsFixedPoint(c, g);
	}
	
	@Override
	public JSONObject createData() {
		JSONObject obj = new JSONObject();
		JSONArray ja = new JSONArray();
		
		ja.put(0.0);
		ja.put(0.0);		
		obj.put("c", ja);
		
		obj.put("g", 9.81);	
		return obj;
	}
}
