package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Vector2D;
import simulator.model.ForceLaws;
import simulator.model.MovingTowardsFixedPoint;

public class MovingTowardsFixedPointBuilder extends Builder<ForceLaws> {

	private static final double c0 = 0.0;
	private static final double c1 = 0.0;
	private static final double g = -9.81;
	
	public MovingTowardsFixedPointBuilder() {
		TypeTag = "mtfp";			
		desc = "MovingTowardsFixedPoint";
	}
	
	@Override
	protected MovingTowardsFixedPoint createTheInstance(JSONObject jo) {
		Vector2D c = new Vector2D(c0, c1);
		if(jo.has("c")) {
			JSONArray vector = jo.getJSONArray("c");
			c = new Vector2D(vector.getDouble(0), vector.getDouble(1));
		}
		double g = MovingTowardsFixedPointBuilder.g;
		if(jo.has("g")) g = jo.getDouble("g");
		return new MovingTowardsFixedPoint(c, g);
	}
	
	@Override
	public JSONObject createData() {
		JSONObject obj = new JSONObject();
		JSONArray ja = new JSONArray();
		
		ja.put(c0);
		ja.put(c1);		
		obj.put("c", ja);
		
		obj.put("g", g);	
		return obj;
	}
}
